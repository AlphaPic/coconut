package com.alan.server.service.impl;

import com.alan.server.service.IThreadService;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

/**
 * @Author alan
 * @Date 2018/11/24
 */
@Service
public class ThreadServiceImpl implements IThreadService {

	@Bean
	@Override
	public ExecutorService generateExecutor() {
		BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(100);
		ThreadFactory threadFactory   = new ThreadFactoryBuilder().setNameFormat("heartbeat_%s").setDaemon(true).build();
		return new ThreadPoolExecutor(4,8,8,TimeUnit.MINUTES,queue,threadFactory);
	}
}
