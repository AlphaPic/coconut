package com.alan.user.utils;

import java.util.UUID;

/**
 * @Author alan
 * @Date 2018/12/1
 */
public class LoginUtil {

	public static String generateRandomSession(){
		return UUID.randomUUID().toString();
	}
}
