package com.alan.server;

import com.alan.server.instance.IServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @Author alan
 * @Date 2018/11/24
 */
@Component
public class Main {
	@Autowired
	private IServer server;

	public static void main(String[] args) throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext("application.xml");
		context.getBean(Main.class).server.start(8080);
	}
}
