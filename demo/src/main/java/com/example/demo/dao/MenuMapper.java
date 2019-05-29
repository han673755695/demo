package com.example.demo.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.Menu;


@Repository
public interface MenuMapper {
	//根据主键id删除
    int deleteByPrimaryKey(List ids);

    //添加数据
    int insert(Menu menu);

    //动态添加数据
    int insertActive(Menu menu);

    //根据实体类查询
    List<Menu> selectListByMenu(Map<String, Object> params);
    
    //根据实体类查询
    List<Menu> selectMenuByPid(Map<String, Object> params);

    //根据主键id查询
    Menu selectByPrimaryKey(String id);

    //动态修改数据
    int updateActiveByMenu(Menu menu);

    //修改数据
    int updateByMenu(Menu menu);
    
    //查询数量
    int totalCount(Map<String,Object> params);
    
    //根据用户id获取该用户的权限
    List<Menu> selectByUserId(String userId);
    

}