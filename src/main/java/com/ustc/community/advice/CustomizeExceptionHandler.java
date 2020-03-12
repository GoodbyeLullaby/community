//package com.ustc.community.advice;
//
//import com.alibaba.fastjson.JSON;
//import com.ustc.community.dto.ResultDTO;
//import com.ustc.community.exception.CustomizeErrorCode;
//import com.ustc.community.exception.CustomizeException;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.PrintWriter;
//
///**
// * @Author: GoodbyeLullaby
// * @Date: 2020/3/4
// */
//@Slf4j
//@ControllerAdvice
//public class CustomizeExceptionHandler {
//	@ExceptionHandler(Exception.class)
//
//	Object handle(Throwable e, Model model, HttpServletRequest request, HttpServletResponse response) {
//		String contentType = request.getContentType();
//		if("application/json".equals(contentType)){
//			ResultDTO resultDTO=null;
//			//返回json
//			if(e instanceof CustomizeException){
//				resultDTO=ResultDTO.errorOf((CustomizeException)e);
//			}else {
//				resultDTO=ResultDTO.errorOf((CustomizeErrorCode.SYS_ERROR));
//			}
//			response.setCharacterEncoding("UTF-8");
//			response.setContentType("application/json");
//			try {
//				PrintWriter printWriter=response.getWriter();
//				printWriter.write(JSON.toJSONString(resultDTO));
//			} catch (IOException ex) {
//				ex.printStackTrace();
//			}
//			return null;
//		}else {
//			//错误页面跳转
//			if(e instanceof CustomizeException){
//				model.addAttribute("message",e.getMessage());
//			}else {
//				log.error(e.getMessage());
//				model.addAttribute("message","服务冒烟了，稍后再试试！！！");
//			}
//			return new ModelAndView("error");
//		}
//
//
//
//	}
//}
