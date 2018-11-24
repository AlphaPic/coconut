package com.alan.server.instance.impl;

import com.alan.common.codec.ObjectJsonCodec;
import com.alan.server.handler.ServerHeartBeatHandler;
import com.alan.server.instance.IServer;
import com.alan.server.thread.HeartBeatTask;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;

/**
 * @Author alan
 * @Date 2018/11/24
 */
@Service
public class EchoServer implements IServer {
	private Logger logger = LoggerFactory.getLogger(EchoServer.class);

	@Autowired
	private ServerHeartBeatHandler heartBeatHandler;

	@Autowired
	private ExecutorService executor;

	@Autowired
	private HeartBeatTask task;

	@Override
	public void start(Integer port) throws Exception{
		ServerBootstrap serverBootstrap = new ServerBootstrap();
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			ObjectJsonCodec codec = new ObjectJsonCodec();
			serverBootstrap
				.group(group)
				.channel(NioServerSocketChannel.class)
				.localAddress(new InetSocketAddress(port))
				.childHandler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel ch) {
						ch.pipeline().addLast(heartBeatHandler).addLast(codec);
					}
				});
			ChannelFuture f = serverBootstrap.bind().sync();
			logger.info("bind on port [ 8080 ] :)");
			executor.submit(task);
			f.channel().closeFuture().sync();
		}finally {
			group.shutdownGracefully().sync();
			executor.shutdown();
			logger.info("close client successfully :)");
		}
	}
}
