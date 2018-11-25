package com.alan.server.instance;

/**
 * @Author alan
 * @Date 2018/11/24
 */
public interface IServer {
	/**
	 * 启动
	 * @param port
	 * @throws Exception
	 */
	void start(Integer port) throws Exception;
}
