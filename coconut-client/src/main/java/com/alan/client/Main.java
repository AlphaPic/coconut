package com.alan.client;

import com.alan.client.instance.IClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

/**
 * @Author alan
 * @Date 2018/11/24
 */
@Service
public class Main {
	@Autowired
	private IClient client;

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("application.xml");
		context.getBean(Main.class).client.start("192.168.0.102",8080);
	}
}
