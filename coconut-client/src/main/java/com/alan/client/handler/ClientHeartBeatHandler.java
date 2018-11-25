package com.alan.client.handler;

import com.alan.common.constant.MessageType;
import com.alan.common.vo.MessageInfo;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author alan
 * @Date 2018/11/25
 */
@Service
@Slf4j
public class ClientHeartBeatHandler extends ChannelInboundHandlerAdapter {
	private Long current;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		MessageInfo messageInfo = (MessageInfo) msg;
		if (messageInfo.getMessageType() == MessageType.HEART_BEAT){
			current = System.currentTimeMillis();
			log.info("update heart beat {}.",current);
		}
		super.channelRead(ctx,msg);
	}

	public void setCurrent(Long current) {
		this.current = current;
	}
}
