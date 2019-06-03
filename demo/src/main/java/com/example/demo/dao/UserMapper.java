package com.example.demo.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.example.demo.domain.User;

@Repository
public interface UserMapper {
    int deleteByPrimaryKey(List ids);

    int insert(User user);

    int insertSelective(User user);

    User selectByPrimaryKey(String id);
    
    User selectUserByNameAndPwd(Map<String,Object> params);
    
    List<Map<String,Object>> selectBySelective(Map<String,Object> params);

    int updateByPrimaryKeySelective(User user);

    int updateByPrimaryKey(User user);
    
    int totalCount(Map<String,Object> params);
}