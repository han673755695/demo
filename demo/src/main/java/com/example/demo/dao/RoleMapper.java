package com.example.demo.dao;

import org.springframework.stereotype.Repository;

import com.example.demo.domain.Role;
import com.example.demo.domain.RoleMenu;

@Repository
public interface RoleMapper {
    int deleteByPrimaryKey(String id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

	void deleteRoleMenu(String roleId);

	int insertRoleMenu(RoleMenu roleMenu);
}