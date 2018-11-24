package com.alan.server.handler;

import com.alan.common.util.MessageUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author alan
 * @Date 2018/11/24
 */
@Service
@ChannelHandler.Sharable
public class ServerHeartBeatHandler extends ChannelInboundHandlerAdapter {
	private Logger logger = LoggerFactory.getLogger(ServerHeartBeatHandler.class);

	private final Map<String,ChannelHandlerContext> channelMap = new ConcurrentHashMap<>(100);
	private final AtomicLong atomicLong = new AtomicLong(0);

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		String channelId = ctx.channel().id().asLongText();
		channelMap.put(channelId, ctx);
		super.channelActive(ctx);
	}

	public void checkHeatBeat(){
		if (!MapUtils.isEmpty(channelMap) && CollectionUtils.isNotEmpty(channelMap.keySet())){
			Set<String> channelIds = channelMap.keySet();
			channelIds.forEach(
				channelId -> {
					ChannelHandlerContext ctx = channelMap.get(channelId);
					if (ctx != null){
						try {
							ctx.channel().writeAndFlush(MessageUtil.generateHeartBeatMessage()).sync();
						} catch (Exception e) {
							logger.error("heart beat send error with channel Id {} :(",channelId,e);
							channelMap.remove(channelId);
						}
					}
				}
			);
			logger.info("heart beat for {} times.",atomicLong.getAndIncrement());
		}else {
			logger.info("empty channel :)");
		}
	}
}
