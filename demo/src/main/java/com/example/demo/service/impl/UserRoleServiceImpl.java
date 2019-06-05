package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.UserRoleMapper;
import com.example.demo.domain.UserRole;
import com.example.demo.service.IUserRoleService;

@Service
public class UserRoleServiceImpl implements IUserRoleService {

	@Autowired
	private UserRoleMapper userRoleMapper;
	
	@Override
	public int deleteByPrimaryKey(String id) {
		try {
			return userRoleMapper.deleteByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	@Override
	public int insert(UserRole record) {
		try {
			return userRoleMapper.insert(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	@Override
	public int insertSelective(UserRole record) {
		try {
			return userRoleMapper.insertSelective(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	@Override
	public UserRole selectByPrimaryKey(String id) {
		try {
			return userRoleMapper.selectByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	@Override
	public int updateByPrimaryKeySelective(UserRole record) {
		try {
			return userRoleMapper.updateByPrimaryKeySelective(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	@Override
	public int updateByPrimaryKey(UserRole record) {
		try {
			return userRoleMapper.updateByPrimaryKey(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

}
