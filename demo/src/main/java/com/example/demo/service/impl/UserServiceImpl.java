package com.example.demo.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.UserDao;
import com.example.demo.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
    private UserDao userDao;
	
	@Override
	public List<Map<String, Object>> getUser() {
		
		try {
			List<Map<String, Object>> userList = userDao.getUserList();
			return userList;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	
}
