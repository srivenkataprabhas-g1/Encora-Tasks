package com.prabhas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Mycontroller {
	
	@GetMapping("/")
	@ResponseBody
	public String display() {
		return "<marquee width=\"100%\" direction=\"left\" height=\"600px\">\r\n"
				+ "<h1>My name is Prabhas,Iam a java developer in web application.</h1>\r\n"
				+ "</marquee>";
	}
}
