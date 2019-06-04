package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.RoleMapper;
import com.example.demo.domain.Role;
import com.example.demo.domain.RoleMenu;
import com.example.demo.service.IRoleService;

@Service
public class RoleServiceImpl implements IRoleService {

	@Autowired
	private RoleMapper roleMapper;
	
	/**
	 * 根据主键删除数据
	 */
	@Override
	public int deleteByPrimaryKey(String id) {
		try {
			return roleMapper.deleteByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	@Override
	public int insert(Role record) {
		try {
			return roleMapper.insert(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	@Override
	public int insertSelective(Role record) {
		try {
			return roleMapper.insertSelective(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	@Override
	public Role selectByPrimaryKey(String id) {
		try {
			return roleMapper.selectByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	@Override
	public int updateByPrimaryKeySelective(Role record) {
		try {
			return roleMapper.updateByPrimaryKeySelective(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	@Override
	public int updateByPrimaryKey(Role record) {
		try {
			return roleMapper.updateByPrimaryKey(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	@Override
	public void deleteRoleMenu(String roleId) {
		try {
			roleMapper.deleteRoleMenu(roleId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	@Override
	public int insertRoleMenu(RoleMenu roleMenu) {
		try {
			return roleMapper.insertRoleMenu(roleMenu);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	@Override
	public List<Role> selectListByName(Role role) {
		try {
			return roleMapper.selectListByName(role);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

}
