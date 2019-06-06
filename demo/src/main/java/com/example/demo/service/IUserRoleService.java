package com.example.demo.service;

import java.util.List;

import com.example.demo.domain.Role;
import com.example.demo.domain.UserRole;

public interface IUserRoleService {
	int deleteByPrimaryKey(String id);

    int insert(UserRole record);

    int insertSelective(UserRole record);

    UserRole selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UserRole record);

    int updateByPrimaryKey(UserRole record);
    
    /**
     * 根据用户id获取用户角色列表,一个用户可能对应多个角色
     * @return
     */
    List<Role> queryRoleByUserId(String userId);
}
