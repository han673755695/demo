package com.example.demo;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.druid.util.StringUtils;
import com.example.demo.domain.Menu;
import com.example.demo.service.IMenuService;
import com.example.demo.utils.UUIDUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {
	
	@Autowired
	private IMenuService menuService;

	@Test
	public void contextLoads() {
		
		Menu menu = new Menu();
		menu.setName("保存角色");
		menu.setUrl("/admin/menu/menuEdit/json");
		menu.setMenuType("2");
		menu.setParentId("9883a984af6242faa2d1965f932e7daa");
		menu.setStatus("1");
		menu.setSort("5");
		// 
		
		//保存
		menu.setId(UUIDUtils.getUUID());
		if (StringUtils.isEmpty(menu.getParentId())) {
			menu.setParentId("-1");
		}
		menu.setCreateDate(new Date());
		menu.setUpdateDate(new Date());
		menuService.insertActive(menu);
		System.out.println("ok");
		
	}
	
	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			
			String uuid = UUIDUtils.getUUID();
			System.out.println(uuid);
		}
	}

}
