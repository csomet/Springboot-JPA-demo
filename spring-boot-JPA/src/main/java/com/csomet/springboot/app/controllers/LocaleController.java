package com.csomet.springboot.app.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LocaleController {

	@GetMapping("/locale")
	public String locale(HttpServletRequest request) {
	
		//it gives the link of the last visited page with params
		String prevUrl = request.getHeader("referer");
		
		return "redirect:" + prevUrl;
	}
}
