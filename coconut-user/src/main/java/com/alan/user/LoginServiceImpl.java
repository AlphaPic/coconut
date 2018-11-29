package com.alan.user;

import com.alan.api.user.ILoginService;

/**
 * @Author alan
 * @Date 2018/11/25
 */
public class LoginServiceImpl implements ILoginService {
	@Override
	public String login(String userName, String password) {
		return null;
	}

	@Override
	public boolean logout(String sessionId) {
		return false;
	}
}
