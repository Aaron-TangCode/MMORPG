package com.test.testgameserverclient;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class ServerHandler extends ChannelHandlerAdapter {
	

	public void channelActive(ChannelHandlerContext ctx) throws Exception {
    	String content="我收到连接";
		Header header=new Header((byte)0, (byte)1, (byte)1, (byte)1, (byte)0, "713f17ca614361fb257dc6741332caf2",content.getBytes("UTF-8").length, 1);
		Message message=new Message(header,content);
		ctx.writeAndFlush(message);
		
	}
 
	@Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
 

	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		 Message m = (Message) msg; // (1)
		 
		/* 请求分发*/
// 	    ActionMapUtil.invote(header.getCammand(),ctx, m);
	}
    
    
}