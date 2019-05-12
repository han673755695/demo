package com.example.demo.dao;

import java.util.List;
import java.util.Map;

import com.example.demo.domain.User;

public interface UserMapper {
    int deleteByPrimaryKey(List ids);

    int insert(User user);

    int insertSelective(User user);

    User selectByPrimaryKey(String id);
    
    List<Map<String,Object>> selectBySelective(Map<String,Object> params);

    int updateByPrimaryKeySelective(User user);

    int updateByPrimaryKey(User user);
    
    int totalCount(Map<String,Object> params);
}