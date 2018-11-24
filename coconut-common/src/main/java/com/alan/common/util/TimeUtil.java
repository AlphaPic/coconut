package com.alan.common.util;

import java.util.concurrent.TimeUnit;

/**
 * @author alan
 */
public class TimeUtil {

	public static void sleep(Integer seconds, TimeUnit timeUnit){
		try {
			Integer timeBase = 1;
			switch (timeUnit){
				case MILLISECONDS:
					break;
				case SECONDS:
					timeBase = 1000;
					break;
				case DAYS:
				case HOURS:
				case MINUTES:
				case NANOSECONDS:
				case MICROSECONDS:
				default:
					return;
			}
			Thread.sleep(seconds * timeBase);
		}catch (Exception e){
		}
	}
}
