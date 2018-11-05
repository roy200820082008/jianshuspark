package com.harleycorp.service;

import java.util.List;

public interface RedisCRUD {
	//通过key查询redis
	public String getRedis(String key);
	//通过key删除键值
	public void deleteByKey(String key);
	//add键值对
	public void addKeyAndValue(String key,String value);
	
	public List<String> getValues(String key);

}
