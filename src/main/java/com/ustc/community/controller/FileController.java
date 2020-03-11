package com.ustc.community.controller;

import com.ustc.community.dto.FileDTO;
import com.ustc.community.exception.CustomizeErrorCode;
import com.ustc.community.exception.CustomizeException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.UUID;

/**
 * @Author: GoodbyeLullaby
 * @Date: 2020/3/9
 */
@Controller
public class FileController {
	@RequestMapping("/file/upload")
	@ResponseBody
	public Object upload(HttpServletRequest request) {
		MultipartHttpServletRequest multipartHttpServletRequest= (MultipartHttpServletRequest) request;
		MultipartFile file = multipartHttpServletRequest.getFile("editormd-image-file");
		if(file.isEmpty()){
			throw new CustomizeException(CustomizeErrorCode.PHOTO_NOT_FOUND);
		}
		FileInputStream in=null;
		FileOutputStream out=null;
		String realPath = request.getSession().getServletContext().getRealPath("/");
		String fileName=realPath+"photos\\"+UUID.randomUUID()+file.getOriginalFilename();
		File file1 = new File(realPath + "photos\\");
		if(!file1.exists()){
			file1.mkdir();
		}
		System.out.println(fileName);
		File newFile=new File(fileName);

		try {
//			if(!newFile.exists()){
//				newFile.createNewFile();
//			}
//			in= (FileInputStream) file.getInputStream();
//			out=new FileOutputStream(newFile);
//
//			int value = in.read(); //一个字节一个字节的读取文件的内容
//			while (value != -1) {
//				out.write(value);
//				out.flush();
//				value = in.read();
//			}
//			in.close();
//			out.close();
			file.transferTo(newFile);
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			// 关闭原则，先用后关
			// 关闭输出流
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		FileDTO fileDTO = new FileDTO();

		fileDTO.setSuccess(1);
		fileDTO.setUrl(fileName);
		fileDTO.setMessage("");
		return fileDTO;
	}
}
