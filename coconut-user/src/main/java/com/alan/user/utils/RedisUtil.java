package com.alan.user.utils;

/**
 * @Author alan
 * @Date 2018/12/1
 */
public class RedisUtil {
	private static String LOGIN_REDIS_KEY_PREFIX = "LOGIN_PREFIX_";

	public static String getLoginRedisKey(String userName){
		return new StringBuilder(20).append(LOGIN_REDIS_KEY_PREFIX).append(userName).toString();
	}

	public static String getLoginPrefix(){
		return LOGIN_REDIS_KEY_PREFIX;
	}
}
