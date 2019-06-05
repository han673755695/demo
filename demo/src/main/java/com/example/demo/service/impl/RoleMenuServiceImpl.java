package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.RoleMenuMapper;
import com.example.demo.domain.RoleMenu;
import com.example.demo.service.IRoleMenuService;

@Service
public class RoleMenuServiceImpl implements IRoleMenuService{

	@Autowired
	private RoleMenuMapper RoleMenuMapper;
	
	@Override
	public int deleteByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(RoleMenu record) {
		try {
			return RoleMenuMapper.insert(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	@Override
	public int insertSelective(RoleMenu record) {
		try {
			return RoleMenuMapper.insertSelective(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	@Override
	public RoleMenu selectByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateByPrimaryKeySelective(RoleMenu record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKey(RoleMenu record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<RoleMenu> queryByRoleId(String id) {
		try {
			return RoleMenuMapper.queryByRoleId(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

}
