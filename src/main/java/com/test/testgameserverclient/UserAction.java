package com.test.testgameserverclient;

import io.netty.channel.ChannelHandlerContext;
import org.springframework.beans.factory.annotation.Autowired;

@NettyController()
public class UserAction {
	
	
//	@Autowired
//	private UserService userService;
//
//
//	@ActionMap(key=1)
//	public String login(ChannelHandlerContext ct, Message message){
//		UserModel userModel=this.userService.findByMasterUserId(1000001);
//		System.out.println(String.format("用户昵称:%s;密码%d;传人内容%s", userModel.getNickname(),userModel.getId(),message.getData()));
//		return userModel.getNickname();
//	}
}