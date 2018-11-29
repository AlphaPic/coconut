package com.alan.server.handler;

import com.alan.common.constant.MessageType;
import com.alan.common.util.MessageUtil;
import com.alan.common.vo.MessageInfo;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author alan
 * @Date 2018/11/24
 */
@Service
@ChannelHandler.Sharable
public class ServerHeartBeatHandler extends ChannelInboundHandlerAdapter {
	private Logger logger = LoggerFactory.getLogger(ServerHeartBeatHandler.class);

	private final Map<String,ChannelHandlerContext> channelMap = new ConcurrentHashMap<>(100);
	private final Map<String,Long> heartBeat                   = new ConcurrentHashMap<>(100);
	private final Long timeInterval                            = 2000L;

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		String channelId = ctx.channel().id().asLongText();
		channelMap.put(channelId, ctx);
		heartBeat.put(channelId,System.currentTimeMillis());
		super.channelActive(ctx);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx,Object msg) throws Exception{
		MessageInfo messageInfo = (MessageInfo) msg;
		if (messageInfo.getMessageType() == MessageType.HEART_BEAT && checkHeatBeat(ctx.channel().id().asLongText())){
			ctx.writeAndFlush(MessageUtil.generateHeartBeatMessage()).sync();
			//不做后续处理了
			return;
		}
		super.channelRead(ctx,msg);
	}

	public boolean checkHeatBeat(String channelId){
		if (!MapUtils.isEmpty(channelMap) && CollectionUtils.isNotEmpty(channelMap.keySet())){
			try {
				ChannelHandlerContext ctx = channelMap.get(channelId);
				if (ctx != null) {
					Long last    = heartBeat.get(channelId);
					Long current = System.currentTimeMillis();
					if (current - last > timeInterval) {
						ctx.close();
						channelMap.remove(channelId);
						heartBeat.remove(channelId);
					}else{
						//刷新值
						heartBeat.put(channelId,current);
						logger.info("{} is online :)",channelId);
						return true;
					}
				}
			}catch (Exception e){
				logger.error("error occurs while doing heart beat check.",e);
			}
		}else {
			logger.info("empty channel :)");
		}
		return false;
	}
}
