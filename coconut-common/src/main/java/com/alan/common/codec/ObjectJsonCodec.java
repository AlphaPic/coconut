package com.alan.common.codec;

import com.alan.common.vo.MessageInfo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @Author alan
 * @Date 2018/11/24
 */
@ChannelHandler.Sharable
public class ObjectJsonCodec extends MessageToMessageCodec<ByteBuf, MessageInfo> {
	private final Logger logger = LoggerFactory.getLogger(ObjectJsonCodec.class);

	@Override
	protected void encode(ChannelHandlerContext ctx, MessageInfo msg, List<Object> out) throws Exception {
		String message = JSONObject.toJSONString(msg);
		out.add(Unpooled.copiedBuffer(message, CharsetUtil.UTF_8));
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
		String message = msg.toString(CharsetUtil.UTF_8);
		MessageInfo messageInfo = JSON.parseObject(message, MessageInfo.class);
		logger.info(messageInfo.toString());
	}
}
