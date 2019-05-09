package com.example.demo.dao;

import org.springframework.stereotype.Repository;

import com.example.demo.domain.User;

@Repository
public interface UserDao {
	public User getUser();
}
