package com.example.demo.service;

import java.util.List;

import com.example.demo.domain.Role;
import com.example.demo.domain.RoleMenu;

public interface IRoleService {
	int deleteByPrimaryKey(String id);

	int insert(Role record);

	int insertSelective(Role record);

	Role selectByPrimaryKey(String id);
	
	List<Role> selectListByName(Role role);

	int updateByPrimaryKeySelective(Role record);

	int updateByPrimaryKey(Role record);
	//操作角色菜单表
	void deleteRoleMenu(String roleId);
	//操作角色菜单表
	int insertRoleMenu(RoleMenu roleMenu);
}
