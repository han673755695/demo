package com.example.demo.controller.admin;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.druid.util.StringUtils;
import com.example.demo.common.ResultData;
import com.example.demo.domain.Menu;
import com.example.demo.service.IMenuService;
import com.example.demo.utils.RequestParamUtils;


/**
 * 后台管理首页控制类
 * @author my
 *
 */
@Controller
@RequestMapping("/admin/index")
public class IndexController {

	private final Logger logger = LoggerFactory.getLogger(IndexController.class);
	
	@Autowired
	private IMenuService menuService;
	@Autowired
	RedisTemplate redisTemplate;
	
	/**
	 * 进入首页
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/toIndex")
	public String toIndex(Model model, HttpServletRequest request) {
		logger.info("进入后台管理首页");
		Map<String,Object> parameterMap = RequestParamUtils.getParameterMap(request);
		try {
			
			String menuDiv = "";
			if (redisTemplate.hasKey("menuDiv")) {
				menuDiv = (String) redisTemplate.opsForValue().get("menuDiv");
				if (!StringUtils.isEmpty(menuDiv)) {
					model.addAttribute("div", menuDiv);
					return "/platform/index";
				}
			}
			
			//一级菜单
			parameterMap.put("parentId", "-1");
			parameterMap.put("sort", "sort");
			List<Menu> firstList = menuService.selectListByMenu(parameterMap);
			//二级菜单
			parameterMap.remove("parentId");
			parameterMap.put("level", "2");
			List<Menu> secondList = menuService.selectListByMenu(parameterMap);
			//三级菜单
			parameterMap.put("level", "3");
			List<Menu> threeList = menuService.selectListByMenu(parameterMap);
			
			
			StringBuffer first = new StringBuffer();
			//一级菜单
			for (Menu menu : firstList) {
				first.append("<li>")
				.append("<a href=\"javascript:;\">")
				.append("<i class=\"iconfont left-nav-li\" lay-tips=\"").append(menu.getName()).append("\">")
				.append(menu.getIcon()).append("</i>")
				.append("<cite>").append(menu.getName()).append("</cite>")
				.append("<i class=\"iconfont nav_right\">&#xe697;</i>")
				.append("</a>")
				.append("<ul class=\"sub-menu\">");
				
				for (Menu secondMenu : secondList) {
					//二级菜单
					if (secondMenu.getParentId().equals(menu.getId())) {
						
						if ("2".equals(secondMenu.getIsParent())) {
							first.append("<li>")
							.append("<a onclick=\"xadmin.add_tab(\'")
								.append(secondMenu.getName()).append("\',\'")
								.append(request.getContextPath()).append(secondMenu.getUrl()).append("\')\">")
							.append("<i class=\"iconfont\">&#xe6a7;</i>")
							.append("<cite>").append(secondMenu.getName()).append("</cite>")
							.append("</a>")
						    .append("</li>");
						}else {
							//三级菜单
							first.append("<li>")
							.append("<a href=\"javascript:;\">")
							.append("<i class=\"iconfont\">&#xe70b;</i>")
							.append("<cite>").append(secondMenu.getName()).append("</cite>")
							.append("<i class=\"iconfont nav_right\">&#xe697;</i>")
							.append("</a>")
							.append("<ul class=\"sub-menu\">");
							
							for (Menu threeMenu : threeList) {
								first.append("<li>")
								.append("<a onclick=\"xadmin.add_tab(\'")
								.append(threeMenu.getName()).append("\',\'")
								.append(request.getContextPath()).append(threeMenu.getUrl()).append("\')\">")
								.append("<i class=\"iconfont\">&#xe6a7;</i>")
								.append("<cite>").append(threeMenu.getName()).append("</cite>")
								.append("</a>")
								.append("</li>");
							}
							first.append("</ul>")
								.append("</li>");
						}
					}
				}
			    first.append("</ul>")
			    .append("</li>");
			}
			
			model.addAttribute("div", first.toString());
			redisTemplate.opsForValue().set("menuDiv", first.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "/platform/index";
	}
	
	/**
	 * 欢迎页面
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/toWelcome")
	public String toWelcome(Model model, HttpServletRequest request) {
		logger.info("进入后台管理欢迎页面");
		return "/platform/welcome";
	}
	
}
