package com.example.demo.controller.admin;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
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
import com.example.demo.domain.Role;
import com.example.demo.domain.RoleMenu;
import com.example.demo.domain.User;
import com.example.demo.eunm.CommonEunm;
import com.example.demo.eunm.RedisKeyEnum;
import com.example.demo.service.IMenuService;
import com.example.demo.service.IRoleMenuService;
import com.example.demo.service.IRoleService;
import com.example.demo.utils.RequestParamUtils;
import com.example.demo.utils.UUIDUtils;

@Controller
@RequestMapping("/admin/role")
public class RoleController {
	
	
	private static final Logger logger = LoggerFactory.getLogger(RoleController.class);
	
	@Autowired
	private RedisTemplate redisTemplate;
	@Autowired
	private IMenuService menuService;
	@Autowired
	private IRoleService roleService;
	@Autowired
	private IRoleMenuService roleMenuService;
	
	/**
	 * 角色列表返回页面
	 * @param model
	 * @param request
	 * @param role
	 * @return
	 */
	@RequestMapping("/roleList")
	public String roleList(Model model, HttpServletRequest request, Role role) {
		ResultData roleListJson = roleListJson(model, request, role);
		model.addAttribute(ResultData.DATAKEY, roleListJson);
		return "/platform/role/roleList";
	}
	
	/**
	 * 角色列表返回json格式
	 * @param model
	 * @param request
	 * @param role
	 * @return
	 */
	@RequestMapping("/roleList/json")
	@ResponseBody
	public ResultData roleListJson(Model model, HttpServletRequest request, Role role) {
		ResultData success = ResultData.getSuccess();
		try {
			List<Role> roleList = roleService.selectListByName(role);
			for (Role role2 : roleList) {
				List<Menu> menuList = menuService.selectByUserRole(role2.getId());
				role2.setMenuList(menuList);
			}
			success.setData(roleList);
		} catch (Exception e) {
			success.setStatus(ResultData.ERROR);
			success.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return success;
	}
	
	
	/**
	 * 跳转到添加角色页面
	 * @param model
	 * @param request
	 * @param role
	 * @return
	 */
	@RequestMapping("/toSaveRole")
	public String toSave(Model model, HttpServletRequest request, Role role) {
		try {
			Map<String, Object> parameterMap = RequestParamUtils.getParameterMap(request);
			//菜单和按钮全部查询
			if (redisTemplate.hasKey(RedisKeyEnum.MENUKEY_ALL.getValue())) {
				List<Menu> menuList = (List<Menu>) redisTemplate.opsForValue().get(RedisKeyEnum.MENUKEY_ALL.getValue());
				if (menuList != null && menuList.size() > 0) {
					model.addAttribute("resultdata", menuList);
					logger.info("从redis中获取全部菜单,包含按钮");
					return "/platform/role/roleAdd";
				}
			}
			logger.info("从数据库中获取全部菜单,包含按钮");
			List<Menu> menuList = menuService.selectMenuByPid(parameterMap);
			model.addAttribute("resultdata", menuList);
			redisTemplate.opsForValue().set(RedisKeyEnum.MENUKEY_ALL.getValue(), menuList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/platform/role/roleAdd";
	}
	
	/**
	 * 添加或修改角色信息
	 * @param model
	 * @param request
	 * @param role
	 * @return
	 */
	@RequestMapping("/saveRole")
	@ResponseBody
	public ResultData saveRole(Model model, HttpServletRequest request, Role role) {
		ResultData success = ResultData.getSuccess();
		
		try {
			String id = role.getId();
			String idsStr = request.getParameter("menuIds");
			
			User user = (User) new Subject.Builder().buildSubject().getSession().getAttribute("user");
			if (StringUtils.isEmpty(id)) {
				//保存角色信息
				String roleId = UUIDUtils.getUUID();
				role.setId(roleId);
				role.setActive(CommonEunm.Active.可用.getValue());
				role.setCreateuser(user.getId());
				role.setCreatedate(new Date());
				roleService.insertSelective(role);
				//保存角色对应的菜单权限
				if (!StringUtils.isEmpty(idsStr)) {
					String[] split = idsStr.split(",");
					for (String menuId : split) {
						RoleMenu roleMenu = new RoleMenu();
						roleMenu.setId(UUIDUtils.getUUID());
						roleMenu.setActive(CommonEunm.Active.可用.getValue());
						roleMenu.setMenuid(menuId);
						roleMenu.setRoleid(roleId);
						roleMenu.setCreateuser(user.getId());
						roleMenu.setCreatedate(new Date());
						roleMenuService.insertSelective(roleMenu);
					}
				}
				
			}else {
				role.setUpdateuser(user.getId());
				role.setUpdatedate(new Date());
				roleService.updateByPrimaryKeySelective(role);
				//先清空之前的角色菜单
				roleService.deleteRoleMenu(id);
				//保存角色对应的菜单权限
				if (!StringUtils.isEmpty(idsStr)) {
					String[] split = idsStr.split(",");
					for (String menuId : split) {
						RoleMenu roleMenu = new RoleMenu();
						roleMenu.setId(UUIDUtils.getUUID());
						roleMenu.setActive(CommonEunm.Active.可用.getValue());
						roleMenu.setMenuid(menuId);
						roleMenu.setRoleid(id);
						roleMenu.setCreateuser(user.getId());
						roleMenu.setCreatedate(new Date());
						roleMenuService.insertSelective(roleMenu);
					}
				}
				
			}
		} catch (Exception e) {
			success.setStatus(ResultData.ERROR);
			success.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return success;
	}
	
	
	/**
	 * 跳到编辑页面
	 * @param model
	 * @param request
	 * @param role
	 * @return
	 */
	@RequestMapping("/toEditRole")
	public String toEditRole(Model model, HttpServletRequest request, Role role) {
		try {
			Map<String, Object> parameterMap = RequestParamUtils.getParameterMap(request);
			ResultData resultData = editRoleJson(model, request, role);
			System.out.println(resultData.getData());
			model.addAttribute("roleData", resultData.getData());
			//菜单和按钮全部查询
			if (redisTemplate.hasKey(RedisKeyEnum.MENUKEY_ALL.getValue())) {
				List<Menu> menuList = (List<Menu>) redisTemplate.opsForValue().get(RedisKeyEnum.MENUKEY_ALL.getValue());
				if (menuList != null && menuList.size() > 0) {
					model.addAttribute("resultdata", menuList);
					logger.info("从redis中获取全部菜单,包含按钮");
					return "/platform/role/roleAdd";
				}
			}
			logger.info("从数据库中获取全部菜单,包含按钮");
			List<Menu> menuList = menuService.selectMenuByPid(parameterMap);
			model.addAttribute("resultdata", menuList);
			redisTemplate.opsForValue().set(RedisKeyEnum.MENUKEY_ALL.getValue(), menuList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/platform/role/roleAdd";
	}
	
	
	/**
	  * 编辑返回json
	 * @param model
	 * @param request
	 * @param role
	 * @return
	 */
	@RequestMapping("/editRoleJson")
	public ResultData editRoleJson(Model model, HttpServletRequest request, Role role) {
		ResultData success = ResultData.getSuccess();
		try {
			role = roleService.selectByPrimaryKey(role.getId());
			List<Menu> menuList = menuService.selectByUserRole(role.getId());
			role.setMenuList(menuList);
			success.setData(role);
		} catch (Exception e) {
			success.setStatus(ResultData.ERROR);
			success.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return success;
	}
	
	
	/**
	 * 删除角色
	 * @param model
	 * @param request
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delRole")
	@ResponseBody
	public ResultData delRole(Model model, HttpServletRequest request, String id) {
		ResultData success = ResultData.getSuccess();
		try {
			//删除菜单
			logger.info("进入删除菜单");
			if (StringUtils.isEmpty(id)) {
				success.setStatus(ResultData.ERROR);
				success.setMessage("id不能为空");
				return success;
			}
			roleService.deleteByPrimaryKey(id);
		} catch (Exception e) {
			success.setStatus(ResultData.ERROR);
			success.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return success;
	}
	

}
