package com.alan.client.instance.impl;

import com.alan.client.handler.ClientHeartBeatHandler;
import com.alan.client.instance.IClient;
import com.alan.common.codec.ObjectJsonCodec;
import com.alan.common.util.MessageUtil;
import com.alan.common.util.TimeUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Author alan
 * @Date 2018/11/24
 */
@Service
public class EchoClient implements IClient {
	private Logger logger = LoggerFactory.getLogger(EchoClient.class);

	@Autowired
	private ExecutorService executorService;

	private Channel channel;
	EventLoopGroup group;

	@Override
	public void start(String host, Integer port) {
		Bootstrap bootstrap             = new Bootstrap();
		group                           = new NioEventLoopGroup();
		ObjectJsonCodec codec           = new ObjectJsonCodec();
		ClientHeartBeatHandler handler  = new ClientHeartBeatHandler();
		try {
			bootstrap.group(group)
				.channel(NioSocketChannel.class)
				.handler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						ch.pipeline().addLast(codec).addLast(handler);
					}
				});
			final ChannelFuture f = bootstrap.connect(new InetSocketAddress(host,port)).sync();
			f.addListener((ChannelFutureListener) future -> {
				if (future.isSuccess()){
					this.channel = future.channel();
					handler.setCurrent(System.currentTimeMillis());
					executorService.submit(new HeartBeatTask(this.channel));
					logger.info("Connection successfully :)");
				}else {
					logger.error("error occurs while connecting server.");
				}
			});
		}catch (Exception e){
			logger.info("error occur while connecting to remote machine.",e);
		}
	}

	class HeartBeatTask implements Runnable{
		private Channel channel;

		public HeartBeatTask(Channel channel) {
			this.channel = channel;
		}

		@Override
		public void run() {
			while(true){
				try {
					if (channel != null) {
						channel.writeAndFlush(MessageUtil.generateHeartBeatMessage()).sync();
					}
				} catch (Exception e) {
					logger.info("error occur while sending heart beat package.",e);
				}
				TimeUtil.sleep(1,TimeUnit.SECONDS);
			}
		}
	}
}
