package com.example.demo.controller.admin;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.example.demo.common.Page;
import com.example.demo.common.ResultData;
import com.example.demo.domain.Menu;
import com.example.demo.eunm.CommonEunm;
import com.example.demo.eunm.RedisKeyEnum;
import com.example.demo.service.IMenuService;
import com.example.demo.utils.RedisUtils;
import com.example.demo.utils.RequestParamUtils;
import com.example.demo.utils.UUIDUtils;

/**
 * 菜单管理控制类
 * @author my
 *
 */
@Controller
@RequestMapping("/admin/menu")
public class MenuController {

	@Autowired
	private IMenuService menuService;
	@Autowired
	private RedisTemplate redisTemplate;
	
	private static final Logger logger = LoggerFactory.getLogger(MenuController.class);
	
	/**
	  * 菜单列表返回页面
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/menuList")
	public String menuList(Model model, HttpServletRequest request) {
		logger.info("获取菜单list");
		Menu menu = new Menu();
		ResultData menuListJson = list(model, request, menu);
		model.addAttribute(ResultData.DATAKEY, menuListJson);
		return "/platform/menu/menuList";
	}
	
	
	/**
	  * 菜单列表返回json数据
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/menuList/json")
	@ResponseBody
	public ResultData menuListJson(Model model, HttpServletRequest request) {
		ResultData success = ResultData.getSuccess();
		Page page = Page.getPage(request);
		
		try {
			logger.info("获取菜单list");
			Map<String,Object> parameterMap = RequestParamUtils.getParameterMap(request);
			String dataSelect = (String) parameterMap.get("dataSelect");
			if (!StringUtils.isEmpty(dataSelect)) {
				String[] split = dataSelect.split("~");
				parameterMap.put("start", split[0]);
				parameterMap.put("end", split[1]);
			}
			
			
			parameterMap.put("currentNum", page.getCurrent());
			parameterMap.put("pageSize", page.getPageSize());
			parameterMap.put("isPage", page.getIsPage());
			
			List<Menu> list = menuService.selectListByMenu(parameterMap);
			int totalCount = menuService.totalCount(parameterMap);
			page.setTotalCount(totalCount);
			
			success.setData(list);
			success.setPage(page);
			success.setQueryParams(parameterMap);
		} catch (Exception e) {
			success.setStatus(ResultData.ERROR);
			success.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return success;
	}
	
	
	@RequestMapping("/toSaveMenu")
	public String toSaveMenu(Model model, HttpServletRequest request, Menu menu) {
		logger.info("添加菜单页面");
		ResultData menuListJson = list(model, request, menu);
		model.addAttribute(ResultData.DATAKEY, menuListJson);
		return "/platform/menu/menuAdd";
	}
	
	
	/**
	  * 保存菜单
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/saveMenu")
	@ResponseBody
	public ResultData saveMenu(Model model, HttpServletRequest request, Menu menu) {
		ResultData success = ResultData.getSuccess();
		try {
			logger.info("保存菜单");
			String id = menu.getId();
			if (StringUtils.isEmpty(id)) {
				//保存
				menu.setId(UUIDUtils.getUUID());
				if (StringUtils.isEmpty(menu.getParentId())) {
					menu.setParentId("-1");
				}
				menu.setCreateDate(new Date());
				menu.setUpdateDate(new Date());
				logger.info(menu.toString());
				menuService.insertActive(menu);
			}else {
				//修改
				menuService.updateActiveByMenu(menu);
			}
			
			if(redisTemplate.hasKey(RedisKeyEnum.MENUKEY.getValue())) {
				redisTemplate.delete(RedisKeyEnum.MENUKEY.getValue());
			}
			if(redisTemplate.hasKey(RedisKeyEnum.MENUKEY_ALL.getValue())) {
				redisTemplate.delete(RedisKeyEnum.MENUKEY_ALL.getValue());
			}
			logger.info("更新redis中菜单信息");
		} catch (Exception e) {
			success.setStatus(ResultData.ERROR);
			success.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return success;
	}
	
	@RequestMapping("/delMenu")
	@ResponseBody
	public ResultData delMenu(Model model, HttpServletRequest request, String ids) {
		ResultData success = ResultData.getSuccess();
		try {
			//删除菜单
			logger.info("进入删除菜单");
			if (StringUtils.isEmpty(ids)) {
				success.setStatus(ResultData.ERROR);
				success.setMessage("ids不能为空");
				return success;
			}
			String[] split = ids.split(",");
			List<String> asList = Arrays.asList(split);
			menuService.deleteByPrimaryKey(asList);
			if(redisTemplate.hasKey(RedisKeyEnum.MENUKEY.getValue())) {
				redisTemplate.delete(RedisKeyEnum.MENUKEY.getValue());
			}
			if(redisTemplate.hasKey(RedisKeyEnum.MENUKEY_ALL.getValue())) {
				redisTemplate.delete(RedisKeyEnum.MENUKEY_ALL.getValue());
			}
		} catch (Exception e) {
			success.setStatus(ResultData.ERROR);
			success.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return success;
	}
	
	
	/**
	 * 跳到页面编辑
	 * 
	 * @param model
	 * @param request
	 * @param user
	 * @return
	 */
	@RequestMapping("/menuEdit")
	public String menuEdit(Model model, HttpServletRequest request, Menu menu) {
		logger.info("进入编辑用户页面");
		ResultData consumerListJson = menuEditJson(model, request, menu);
		model.addAttribute(ResultData.DATAKEY, consumerListJson.getData());
		return "/platform/menu/menuAdd";
	}

	/**
	 * 编辑返回json
	 * 
	 * @param model
	 * @param request
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/menuEdit/json")
	public ResultData menuEditJson(Model model, HttpServletRequest request, Menu menu) {
		ResultData success = ResultData.getSuccess();
		try {
			String id = menu.getId();
			if (StringUtils.isEmpty(id)) {
				success.setStatus(ResultData.ERROR);
				return success;
			}
			Menu result = menuService.selectByPrimaryKey(id);
			Map<String,Object> dataMap = new HashMap();
			if (!"-1".equals(result.getParentId())) {
				Menu parentMenu = menuService.selectByPrimaryKey(result.getParentId());
				dataMap.put("parentName", parentMenu.getName());
				success.setMapData(dataMap);
			}
			
			success.setData(result);
		} catch (Exception e) {
			success.setStatus(ResultData.ERROR);
			success.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return success;
	}
	
	/**
	   * 加载后台管理的菜单
	 * @param model
	 * @param request
	 * @param menu
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/list")
	public ResultData list(Model model, HttpServletRequest request, Menu menu) {
		ResultData success = ResultData.getSuccess();
		try {
			Map<String, Object> parameterMap = RequestParamUtils.getParameterMap(request);
			String menuType = (String) parameterMap.get("menuType");
			if (CommonEunm.menuType.菜单.getValue().equals(menuType)) {
				//菜单
				if (redisTemplate.hasKey(RedisKeyEnum.MENUKEY.getValue())) {
					List<Menu> menuList = (List<Menu>) redisTemplate.opsForValue().get(RedisKeyEnum.MENUKEY.getValue());
					if (menuList != null && menuList.size() > 0) {
						success.setData(menuList);
						logger.info("从redis中获取菜单");
						return success;
					}
				}
				logger.info("从数据库中获取菜单");
				List<Menu> menuList = menuService.selectMenuByPid(parameterMap);
				success.setData(menuList);
				redisTemplate.opsForValue().set(RedisKeyEnum.MENUKEY.getValue(), menuList);
			}else {
				//菜单和按钮全部查询
				if (redisTemplate.hasKey(RedisKeyEnum.MENUKEY_ALL.getValue())) {
					List<Menu> menuList = (List<Menu>) redisTemplate.opsForValue().get(RedisKeyEnum.MENUKEY_ALL.getValue());
					if (menuList != null && menuList.size() > 0) {
						success.setData(menuList);
						logger.info("从redis中获取全部菜单,包含按钮");
						return success;
					}
				}
				logger.info("从数据库中获取全部菜单,包含按钮");
				List<Menu> menuList = menuService.selectMenuByPid(parameterMap);
				success.setData(menuList);
				redisTemplate.opsForValue().set(RedisKeyEnum.MENUKEY_ALL.getValue(), menuList);
			}
			
		} catch (Exception e) {
			success.setStatus(ResultData.ERROR);
			success.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return success;
	}
	
	
}
