package com.alan.client.service;

import java.util.concurrent.ExecutorService;

/**
 * @Author alan
 * @Date 2018/11/25
 */
public interface IThreadService {
	/**
	 * 生成执行器
	 * @return
	 */
	ExecutorService generateExecutor();
}
