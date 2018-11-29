package com.alan.user;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @Author alan
 * @Date 2018/11/25
 */
public class Main {
	public static void main(String[] args) throws IOException {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application.xml");
		context.start();
		System.in.read();
	}
}
