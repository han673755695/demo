package com.example.demo.generator.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.common.ResultData;
import com.example.demo.generator.constant.Constant;
import com.example.demo.generator.service.IGeneratorService;
import com.example.demo.utils.FreemarkerTool;
import com.example.demo.utils.StringUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Controller
@RequestMapping("/generator")
public class GeneratorController {

	@Autowired
	private IGeneratorService generatorService;
	@Autowired
	private FreemarkerTool freemarkerTool;
	@Autowired
    private Configuration configuration;
	
	@RequestMapping("/genCode")
	@ResponseBody
	public ResultData genCode() {
		ResultData success = ResultData.getSuccess();
		
		List<Map<String, String>> columnsList = generatorService.selectColumnsByTableName("admin_menu");
		
		for (Map<String, String> map : columnsList) {
			String value = map.get("COLUMN_NAME");
			System.out.println(value);
		}
		
		
		try {
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("columnsList", columnsList);
			dataMap.put("prefix", "${");
			dataMap.put("suffix", "}");
			String processString = freemarkerTool.processString(Constant.GeneratorEnum.查看.getValue(), dataMap);
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("processString", processString);
			success.setMapData(resultMap);
		} catch (IOException | TemplateException e) {
			e.printStackTrace();
		}
		success.setData(columnsList);
		
		return success;
	}
	
	
	@ResponseBody
	@RequestMapping("/createFile")
	public String createFile() {
		
		try {
			List<Map<String, String>> columnsList = generatorService.selectColumnsByTableName("admin_menu");
			for (Map<String, String> map : columnsList) {
				String underlineToCamelCase = StringUtils.underlineToCamelCase(map.get("COLUMN_NAME"));
				//实体类字段名称
				map.put("entryClumu", underlineToCamelCase);
			}
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("columnsList", columnsList);
			dataMap.put("prefix", "${");
			dataMap.put("suffix", "}");
			dataMap.put("jinghao", "#{");
			String tableName = columnsList.get(0).get("TABLE_NAME");
			//数据库表名
			dataMap.put("tableName", tableName);
			//包名
			dataMap.put("package", "com.example.demo");
			//实体类名称	//先去掉数据库名前缀admin_,然后把  _  转化为驼峰命名法,再转化首字母大写
			String upperCaseFirst = StringUtils.upperCaseFirst(StringUtils.underlineToCamelCase(tableName.replace("admin_", "")));
			//实体类名称
			dataMap.put("entryName", upperCaseFirst);
			
			Template template = configuration.getTemplate(Constant.GeneratorEnum.mapperXml.getValue());
			String path = Constant.createFilePathEnum.mapper文件.getValue() + upperCaseFirst + "Mapper.xml";
			File upload = new File(path);
			if(!upload.getParentFile().exists()) {
				upload.getParentFile().mkdirs();
			}
			upload.createNewFile();
			FileWriter fileWriter = new FileWriter(upload);
			
			template.process(dataMap, fileWriter);
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
		
		return "ok";
	}
	
}
