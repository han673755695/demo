package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.UserDao;
import com.example.demo.domain.User;
import com.example.demo.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
    private UserDao userDao;
	
	@Override
	public User getUser() {
		
		try {
			User user = userDao.getUser();
			return user;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	
}
