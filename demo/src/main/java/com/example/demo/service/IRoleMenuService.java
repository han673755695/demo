package com.example.demo.service;

import java.util.List;

import com.example.demo.domain.RoleMenu;

public interface IRoleMenuService {
	int deleteByPrimaryKey(String id);

    int insert(RoleMenu record);

    int insertSelective(RoleMenu record);

    RoleMenu selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(RoleMenu record);

    int updateByPrimaryKey(RoleMenu record);
    
    List<RoleMenu> queryByRoleId(String id);
}
