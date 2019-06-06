package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.RoleMenuMapper;
import com.example.demo.domain.RoleMenu;
import com.example.demo.service.IRoleMenuService;

@Service
public class RoleMenuServiceImpl implements IRoleMenuService {

	@Autowired
	private RoleMenuMapper roleMenuMapper;

	@Override
	public int deleteByPrimaryKey(String id) {

		return roleMenuMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(RoleMenu record) {
		try {
			return roleMenuMapper.insert(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	@Override
	public int insertSelective(RoleMenu record) {
		try {
			return roleMenuMapper.insertSelective(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	@Override
	public RoleMenu selectByPrimaryKey(String id) {
		try {
			return roleMenuMapper.selectByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	@Override
	public int updateByPrimaryKeySelective(RoleMenu record) {
		try {
			return roleMenuMapper.updateByPrimaryKeySelective(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	@Override
	public int updateByPrimaryKey(RoleMenu record) {
		try {
			return roleMenuMapper.updateByPrimaryKey(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	@Override
	public List<RoleMenu> queryByRoleId(String id) {
		try {
			return roleMenuMapper.queryByRoleId(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

}
