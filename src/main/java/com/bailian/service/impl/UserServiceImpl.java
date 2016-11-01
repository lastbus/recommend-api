package com.bailian.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bailian.dao.UserMapper;
import com.bailian.model.User;
import com.bailian.service.UserService;
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;
	@Override
	public User selectById(int id) {
		// TODO Auto-generated method stub
		return userMapper.selectById(id);
	}

}
