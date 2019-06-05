package com.test.testgameserverclient;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
 
	
	public static void main(String[] args) {
		  ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		  TimeServer timeServer=  ac.getBean(TimeServer.class);
		  try {
			timeServer.start(8888);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}