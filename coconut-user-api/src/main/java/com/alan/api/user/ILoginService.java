package com.alan.api.user;

/**
 * @Author alan
 * @Date 2018/11/25
 */
public interface ILoginService {
	/**
	 * 登录
	 * @param userName
	 * @param password
	 * @return
	 */
	public String login(String userName,String password);

	/**
	 * 登出
	 * @param sessionId
	 * @return
	 */
	public boolean logout(String username,String sessionId);
}
