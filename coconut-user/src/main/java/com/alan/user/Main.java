package com.alan.user;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @Author alan
 * @Date 2018/11/25
 */
public class Main {
	public static void main(String[] args) throws IOException {
//		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application.xml");
//		LoginServiceImpl service = context.getBean(LoginServiceImpl.class);
//		service.login("admin","admin");
		String str = "fan:wen:long";
		String[] values = str.split(":",4);
		for (String value : values){
			System.out.println(value);
		}
	}
}
