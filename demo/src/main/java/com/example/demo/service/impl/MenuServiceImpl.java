package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.MenuMapper;
import com.example.demo.domain.Menu;
import com.example.demo.service.IMenuService;

@Service
public class MenuServiceImpl implements IMenuService {

	@Autowired
	private MenuMapper menuMapper;

	@Override
	public List<Menu> selectListByMenu(Menu enu) {
		try {
			return menuMapper.selectListByMenu(enu);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("获取数据失败");
		}
	}

	@Override
	public int deleteByPrimaryKey(String id) {
		try {

			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("删除数据失败");
		}
	}

	@Override
	public int insert(Menu menu) {
		try {

			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("添加数据失败");
		}
	}

	@Override
	public int insertActive(Menu menu) {
		try {

			return menuMapper.insertActive(menu);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("添加数据失败");
		}
	}

	@Override
	public Menu selectByPrimaryKey(String id) {
		try {

			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("获取数据失败");
		}
	}

	@Override
	public int updateActiveByMenu(Menu menu) {
		try {

			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("修改数据失败");
		}
	}

	@Override
	public int updateByMenu(Menu menu) {
		try {

			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("修改数据失败");
		}
	}

}
