package com.example.demo.controller.admin;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.common.ResultData;
import com.example.demo.domain.User;
import com.example.demo.service.IUserService;
import com.example.demo.utils.DateUtils;
import com.example.demo.utils.RequestParamUtils;
import com.example.demo.utils.UUIDUtils;

/**
 * 会员管理控制类
 * 
 * @author my
 *
 */
@Controller
@RequestMapping("/admin/consumer")
public class ConsumerController {

	private final Logger logger = LoggerFactory.getLogger(ConsumerController.class);

	@Autowired
	private IUserService userService;

	/**
	 * 会员列表
	 * 
	 * @return
	 */
	@RequestMapping("/consumerList")
	public String consumerList(Model model, HttpServletRequest request, User user) {
		logger.info("进入用户列表页面");
		ResultData consumerListJson = consumerListJson(model, request, user);
		model.addAttribute(ResultData.DATAKEY, consumerListJson);
		return "/platform/consumer/consumerList";
	}

	/**
	 * 会员列表返回json数据
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/consumerList/json")
	public ResultData consumerListJson(Model model, HttpServletRequest request, User user) {
		ResultData success = ResultData.getSuccess();
		try {
			Map<String,Object> parameterMap = RequestParamUtils.getParameterMap(request);

			List<Map<String, Object>> userList = userService.selectBySelective(parameterMap);
			success.setData(userList);
			success.setQueryParams(parameterMap);
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
	@RequestMapping("/consumerEdit")
	public String consumerEdit(Model model, HttpServletRequest request, User user) {
		logger.info("进入编辑用户页面");
		ResultData consumerListJson = consumerEditJson(model, request, user);
		model.addAttribute(ResultData.DATAKEY, consumerListJson.getData());
		return "/platform/consumer/consumerAdd";
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
	@RequestMapping("/consumerEdit/json")
	public ResultData consumerEditJson(Model model, HttpServletRequest request, User user) {
		ResultData success = ResultData.getSuccess();
		try {
			String id = user.getId();
			if (StringUtils.isEmpty(id)) {
				success.setStatus(ResultData.ERROR);
				return success;
			}
			User result = userService.selectByPrimaryKey(id);
			success.setData(result);
		} catch (Exception e) {
			success.setStatus(ResultData.ERROR);
			success.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return success;
	}

	/**
	 * 到添加页面
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/toConsumerAdd")
	public String toConsumerAdd(Model model, HttpServletRequest request) {
		logger.info("进入添加用户页面");
		return "/platform/consumer/consumerAdd";
	}

	/**
	 * 田家庵数据
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/consumerAdd")
	public ResultData consumerAdd(Model model, HttpServletRequest request, User user) {
		ResultData success = ResultData.getSuccess();
		try {
			if (!StringUtils.isEmpty(user.getId())) {
				// 修改
				logger.info("修改用户");
				user.setUpdateDate(new Date());
				int id = userService.updateByPrimaryKeySelective(user);
				success.setData(id);
			} else {
				// 增加
				logger.info("添加用户");
				user.setId(UUIDUtils.getUUID());
				user.setCreateDate(new Date());
				user.setUpdateDate(new Date());
				int id = userService.insertSelective(user);
				success.setData(id);
			}

			logger.info(success.toString());
			logger.info("user: " + user);
		} catch (Exception e) {
			success.setStatus(ResultData.ERROR);
			success.setMessage(e.getMessage());
			e.printStackTrace();
		}

		return success;
	}

	/**
	 * 根据用户id删除用户信息
	 * 
	 * @param model
	 * @param request
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/consumerDel")
	public ResultData consumerDel(Model model, HttpServletRequest request, String ids) {
		ResultData success = ResultData.getSuccess();
		try {
			// 删除用户
			logger.info("删除用户");
			if (StringUtils.isEmpty(ids)) {
				success.setStatus(ResultData.ERROR);
				success.setMessage("id不能为空");
				return success;
			}
			logger.info(ids);
			String[] split = ids.split(",");
			List<String> asList = Arrays.asList(split);
			userService.deleteByPrimaryKey(asList);

		} catch (Exception e) {
			success.setStatus(ResultData.ERROR);
			success.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return success;
	}

}
