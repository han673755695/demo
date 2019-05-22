package com.example.demo.controller.admin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

/**
 * 统计控制类
 * 
 * @author my
 * @param <E>
 *
 */
@Controller
@RequestMapping("/admin/statistics")
public class StatisticsController<E> {

	private static final Logger logger = LoggerFactory.getLogger(StatisticsController.class);

	/**
	 * 统计页面
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/toStatistics")
	public String statistics(Model model, HttpServletRequest request) {
		logger.info("进入后台管理统计界面");
		return "/platform/statistics/statistics";
	}

	@RequestMapping("/toUpload")
	public String toUpload(Model model, HttpServletRequest request) {
		logger.info("进入后台管理上传图片界面");
		return "/platform/statistics/fileUpload";
	}

	@PostMapping("/uploadFile")
	@ResponseBody
	public ResultData uploadFile(MultipartFile file, Model model, HttpServletRequest request) {
		ResultData success = ResultData.getSuccess();
		try {
			if (file.isEmpty()) {
				System.out.println("文件为空");
			}

			String realPath = request.getServletContext().getRealPath("/");
			System.out.println(realPath);

			String fileName = file.getOriginalFilename(); // 文件名
			String suffixName = fileName.substring(fileName.lastIndexOf(".")); // 后缀名
			String filePath = "D://temp-rainy//"; // 上传后的路径
			fileName = UUID.randomUUID() + suffixName; // 新文件名
			File dest = new File(realPath + fileName);
			if (!dest.getParentFile().exists()) {
				dest.getParentFile().mkdirs();
			}
			file.transferTo(dest);
			String filename = fileName;
			model.addAttribute("filename", filename);

		} catch (Exception e) {
			success.setStatus(ResultData.ERROR);
			e.printStackTrace();
		}
		return success;
	}

	@PostMapping("/uploadFileBase64")
	@ResponseBody
	public ResultData uploadFileBase64(String base64Data, HttpServletRequest request) {
		ResultData success = ResultData.getSuccess();
		try {

			/*
			 * ObjectMapper mapper=new ObjectMapper(); ArrayList<UploadImg>
			 * list=mapper.readValue(base64Data, new TypeReference<ArrayList<UploadImg>>() {
			 * }); System.out.println(list);
			 */

//			List<UploadImg> parseArray = JSON.parseArray(base64Data, UploadImg.class);
//			System.out.println(parseArray.toString());

			JSONArray parseArray2 = JSON.parseArray(base64Data);
			List<UploadImg> list = new ArrayList<>();
			for (Object object : parseArray2) {
				UploadImg uploadImg = JSON.parseObject(object.toString(),UploadImg.class);
				list.add(uploadImg);
			}

			String dataPrix = "";
			String data = "";
			if (base64Data == null || "".equals(base64Data)) {
				throw new Exception("上传失败，上传图片数据为空");
			} else {
				String[] d = list.get(0).getBase64().split("base64,");
				if (d != null && d.length == 2) {
					dataPrix = d[0];
					data = d[1];
				} else {
					throw new Exception("上传失败，数据不合法");
				}
			}
			String suffix = "";
			if ("data:image/jpeg;".equalsIgnoreCase(dataPrix)) {// data:image/jpeg;base64,base64编码的jpeg图片数据
				suffix = ".jpg";
			} else if ("data:image/x-icon;".equalsIgnoreCase(dataPrix)) {// data:image/x-icon;base64,base64编码的icon图片数据
				suffix = ".ico";
			} else if ("data:image/gif;".equalsIgnoreCase(dataPrix)) {// data:image/gif;base64,base64编码的gif图片数据
				suffix = ".gif";
			} else if ("data:image/png;".equalsIgnoreCase(dataPrix)) {// data:image/png;base64,base64编码的png图片数据
				suffix = ".png";
			} else {
				throw new Exception("上传图片格式不合法");
			}
			String tempFileName = UUID.randomUUID().toString() + suffix;

			// 因为BASE64Decoder的jar问题，此处使用spring框架提供的工具包
			byte[] bs = Base64Utils.decodeFromString(data);
			try {
				// 使用apache提供的工具类操作流

				System.out.println(request.getServletContext().getRealPath("/upload"));
				FileUtils.writeByteArrayToFile(
						new File(request.getServletContext().getRealPath("/upload"), tempFileName), bs);
			} catch (Exception ee) {
				throw new Exception("上传失败，写入文件失败，" + ee.getMessage());
			}

			return success;
		} catch (Exception e) {
			e.printStackTrace();
			return success;
		}
	}

}
