package com.example.demo.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.example.demo.common.ResultData;
import com.example.demo.domain.UploadImg;

@Controller
@RequestMapping("/admin/upload")
public class UploadUtils {

	
	/**
	  * 上传单张或多张图片
	 * @param file
	 * @param model
	 * @param request
	 * @return
	 */
	@PostMapping("/uploadFile")
	@ResponseBody
	public ResultData uploadFile(MultipartFile[] file, Model model, HttpServletRequest request) {
		ResultData success = ResultData.getSuccess();
		try {
			if (file.length == 0) {
				throw new RuntimeException("文件不能为空");
			}

			String realPath = request.getServletContext().getRealPath("/");
			for (MultipartFile multipartFile : file) {
				String fileName = multipartFile.getOriginalFilename(); // 文件名
				String suffixName = fileName.substring(fileName.lastIndexOf(".")); // 后缀名
				fileName = UUID.randomUUID() + suffixName; // 新文件名
				String path = "/upload/" + fileName;
				File dest = new File(realPath + path);
				if (!dest.getParentFile().exists()) {
					dest.getParentFile().mkdirs();
				}
				multipartFile.transferTo(dest);
				System.out.println("path: " + path);
				success.setData(path);
			}
		} catch (Exception e) {
			success.setStatus(ResultData.ERROR);
			e.printStackTrace();
		}
		return success;
	}
	
	
}
