package com.alan.user;

import com.alan.api.user.ILoginService;
import com.alan.user.dao.UserinfoMapper;
import com.alan.user.model.Userinfo;
import com.alan.user.model.UserinfoExample;
import com.alan.user.utils.LoginUtil;
import com.alan.user.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @Author alan
 * @Date 2018/11/25
 */
@Service
@Slf4j
public class LoginServiceImpl implements ILoginService {
	@Autowired
	private UserinfoMapper userinfoMapper;
	@Autowired
	private RedisTemplate<String,String> redisTemplate;

	@Override
	public String login(String userName, String password) {
		if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)){
			return null;
		}
		try {
			//
			String loginKey = RedisUtil.getLoginRedisKey(RedisUtil.getLoginRedisKey(userName));
			if (redisTemplate.hasKey(loginKey)) {
				return redisTemplate.opsForValue().get(loginKey);
			}
			//
			UserinfoExample example = new UserinfoExample();
			UserinfoExample.Criteria criteria = example.createCriteria();
			criteria.andUsernameEqualTo(userName);
			List<Userinfo> userinfos = userinfoMapper.selectByExample(example);
			if (!CollectionUtils.isEmpty(userinfos) && userinfos.size() == 1) {
				Userinfo userinfo = userinfos.get(0);
				if (password.equals(userinfo.getPassword())) {
					String sessionId = LoginUtil.generateRandomSession();
					redisTemplate.opsForValue().set(loginKey, sessionId);
					log.info("login successfully :)");
					return sessionId;
				}
			}
		}catch (Exception e){
			log.error("error occurs while query user info.",e);
		}
		return null;
	}

	@Override
	public boolean logout(String username,String sessionId) {
		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(sessionId)){
			return false;
		}
		try {
			String loginKey = RedisUtil.getLoginRedisKey(username);
			if (!redisTemplate.hasKey(loginKey)) {
				return false;
			}
			String redisSessionId = redisTemplate.opsForValue().get(loginKey);
			if (sessionId.equals(redisSessionId)) {
				redisTemplate.delete(loginKey);
				return true;
			}
		}catch (Exception e){
			log.error("error occurs while log out.",e);
		}
		return false;
	}
}
