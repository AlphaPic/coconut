package com.alan.server.service;

import java.util.concurrent.ExecutorService;

/**
 * @Author alan
 * @Date 2018/11/24
 */
public interface IThreadService {
	/**
	 * 生成执行器
	 * @return
	 */
	ExecutorService generateExecutor();
}
