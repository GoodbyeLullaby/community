package com.ustc.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: GoodbyeLullaby
 * @Date: 2020/2/22
 */
@Controller
public class HelloController {
	@GetMapping("/hello")
	public String hello(@RequestParam(name="name")String name, Model model){
		model.addAttribute("name", name);
		return "hello";
	}
}
