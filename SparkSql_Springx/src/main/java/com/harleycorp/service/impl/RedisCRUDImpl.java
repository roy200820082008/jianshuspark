package com.harleycorp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.harleycorp.service.RedisCRUD;
import com.redis.JedisClient;
@Service
public class RedisCRUDImpl implements RedisCRUD {
	@Resource
	private JedisClient jedisClient;

	@Override
	public String getRedis(String key) {
		return jedisClient.get(key);
	}

	@Override
	public List<String> getValues(String key) {
		List<String> hvals = jedisClient.hvals(key);
		return hvals;
	}

	@Override
	public void deleteByKey(String key) {
		jedisClient.del(key);
	}

	@Override
	public void addKeyAndValue(String key, String value) {
		jedisClient.set(key, value);
		
	}

}
