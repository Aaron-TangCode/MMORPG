package com.test.testgameserverclient;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import org.springframework.stereotype.Component;

@Component
public class TimeServer {
 
	private int port=88888;

 
	public void run() throws InterruptedException {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		ByteBuf heapBuffer = Unpooled.buffer(8);
		heapBuffer.writeBytes("\r".getBytes());
		try {
			ServerBootstrap b = new ServerBootstrap(); // (2)
			b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class) // (3)
					.childHandler(new ChannelInitializer<SocketChannel>() { // (4)
								@Override
								public void initChannel(SocketChannel ch) throws Exception {
									ch.pipeline().addLast("encoder", new MessageEncoder()).addLast("decoder", new MessageDecoder()).addFirst(new LineBasedFrameDecoder(65535))
											.addLast(new ServerHandler());
								}
							}).option(ChannelOption.SO_BACKLOG, 1024) // (5)
					.childOption(ChannelOption.SO_KEEPALIVE, true); // (6)
			ChannelFuture f = b.bind(port).sync(); // (7)
			f.channel().closeFuture().sync();
		} finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}
	
	public void start(int port) throws InterruptedException{
	  this.port=port;
	  this.run();
	}
}