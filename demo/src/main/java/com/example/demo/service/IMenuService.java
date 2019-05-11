package com.example.demo.service;

import java.util.List;

import com.example.demo.domain.Menu;

public interface IMenuService {

	//根据主键id删除
    int deleteByPrimaryKey(String id);

    //添加数据
    int insert(Menu menu);

    //动态添加数据
    int insertActive(Menu menu);

    //根据实体类查询
    List<Menu> selectListByMenu(Menu menu);

    //根据主键id查询
    Menu selectByPrimaryKey(String id);

    //动态修改数据
    int updateActiveByMenu(Menu menu);

    //修改数据
    int updateByMenu(Menu menu);
}
