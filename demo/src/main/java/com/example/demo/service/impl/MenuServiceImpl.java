package com.example.demo.service.impl;

import java.util.List;
import java.util.Map;

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
	public List<Menu> selectListByMenu(Map<String, Object> params) {
		try {
			return menuMapper.selectListByMenu(params);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("获取数据失败");
		}
	}

	@Override
	public int deleteByPrimaryKey(List ids) {
		try {

			return menuMapper.deleteByPrimaryKey(ids);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("删除数据失败");
		}
	}

	@Override
	public int insert(Menu menu) {
		try {

			return menuMapper.insert(menu);
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
			return menuMapper.selectByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("获取数据失败");
		}
	}

	@Override
	public int updateActiveByMenu(Menu menu) {
		try {

			return menuMapper.updateActiveByMenu(menu);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("修改数据失败");
		}
	}

	@Override
	public int updateByMenu(Menu menu) {
		try {

			return menuMapper.updateByMenu(menu);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("修改数据失败");
		}
	}

	@Override
	public int totalCount(Map<String, Object> params) {
		try {

			return menuMapper.totalCount(params);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("修改数据失败");
		}
	}

	@Override
	public List<Menu> selectMenuByPid(Map<String, Object> params) {
		try {

			return menuMapper.selectMenuByPid(params);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("修改数据失败");
		}
	}

	@Override
	public List<Menu> selectByUserId(String userId) {
		try {

			return menuMapper.selectByUserId(userId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("修改数据失败");
		}
	}

	@Override
	public List<Menu> selectByUserRole(String roleId) {
		try {

			return menuMapper.selectByUserRole(roleId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("修改数据失败");
		}
	}

}
