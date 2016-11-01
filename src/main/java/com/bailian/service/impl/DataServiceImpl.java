package com.bailian.service.impl;

import java.util.List;
import java.util.Map;











import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.JedisCluster;

import com.alibaba.fastjson.JSON;
import com.bailian.entity.Goods;
import com.bailian.model.ApiPromotionalGoods;
import com.bailian.model.CategoryManagement;
import com.bailian.model.CombinedHotwords;
import com.bailian.service.DataService;
@Service
public class DataServiceImpl implements DataService {

	@Autowired
	private JedisCluster jedisCluster;

	@Override
	public String get(String key) {
		 
		return jedisCluster.get(key);
	}

	@Override
	public Map<String, String> hgetAll(String key) {
		return jedisCluster.hgetAll(key);
		
	}

	@Override
	public void set(String key, List<Goods> valList,long expire) {
		final String jsonValue = JSON.toJSONString(valList, true);
		jedisCluster.set(key, jsonValue);
		jedisCluster.expire(key, (int) expire);
		
	}

	@Override
	public void setPromGoods(String key, List<ApiPromotionalGoods> valList,
			long expire) {
		final String jsonValue = JSON.toJSONString(valList, true);
		jedisCluster.set(key, jsonValue);
		jedisCluster.expire(key, (int) expire);
		
	}

	@Override
	public void set(String key, String value) {
		jedisCluster.set(key, value);
		
	}

	@Override
	public void setCombinedHotwords(String key, List<CombinedHotwords> value,
			long expire) {
		final String jsonValue = JSON.toJSONString(value, true);
		jedisCluster.set(key, jsonValue);
		jedisCluster.expire(key, (int) expire);
		
	}

	@Override
	public void setategoryManagement(String key,
			List<CategoryManagement> value, long expire) {
		final String jsonValue = JSON.toJSONString(value, true);
		jedisCluster.set(key, jsonValue);
		jedisCluster.expire(key, (int) expire);		
	}

	@Override
	public void set(String key, String value, long expire) {
		jedisCluster.setex(key, (int) expire, value);
	}

	@Override
	public void hmset(String key, Map<String, String> value) {
		jedisCluster.hmset(key, value);
	}

	

}
