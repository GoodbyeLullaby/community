package com.ustc.community.dto;

import com.ustc.community.exception.CustomizeErrorCode;
import com.ustc.community.exception.CustomizeException;
import lombok.Data;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author: GoodbyeLullaby
 * @Date: 2020/3/5
 */
@Data
public class ResultDTO {
	private Integer code;
	private String message;

	public static ResultDTO errorOf(Integer code ,String message){
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setCode(code);
		resultDTO.setMessage(message);
		return resultDTO;


	}

	public static ResultDTO errorOf(CustomizeErrorCode noLogin) {
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setCode(noLogin.getCode());
		resultDTO.setMessage(noLogin.getMessage());
		return resultDTO;

	}

	public static ResultDTO okOf(){
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setCode(200);
		resultDTO.setMessage("请求成功");
		return resultDTO;
	}

	public static ResultDTO errorOf(CustomizeException e) {
		return errorOf(e.getCode(), e.getMessage());
	}
}
