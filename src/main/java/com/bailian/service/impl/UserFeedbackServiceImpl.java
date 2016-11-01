package com.bailian.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bailian.service.DataService;
import com.bailian.service.UserFeedbackService;

@Service
public class UserFeedbackServiceImpl implements UserFeedbackService {

	@Autowired
	DataService  ds;
	@Override
	public void dislike(String mId, String gId) {
		String key = "rcmd_dislike_" + mId;
		Map<String,String> map = new HashMap<String,String>();
		String time = String.valueOf(System.currentTimeMillis());
		map.put(gId, time);
		ds.hmset(key, map);
		map = null;
		
	}

	

}
