package com.alan.server.thread;

import com.alan.common.util.TimeUtil;
import com.alan.server.handler.ServerHeartBeatHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @Author alan
 * @Date 2018/11/24
 */
@Service
public class HeartBeatTask implements Runnable {

	@Autowired
	private ServerHeartBeatHandler handler;

	@Override
	public void run() {
		while (true) {
			if (handler != null) {
				handler.checkHeatBeat("");
			}
			TimeUtil.sleep(1,TimeUnit.SECONDS);
		}
	}
}
