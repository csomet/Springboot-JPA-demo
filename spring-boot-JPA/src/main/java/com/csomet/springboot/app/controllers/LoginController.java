package com.csomet.springboot.app.controllers;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

	
	@GetMapping(value="/login")
	public String login(@RequestParam(value="logout", required=false) String logout,
			@RequestParam(value="error", required=false) String error, Model model, Principal principal, RedirectAttributes flash) {
		
		//we are logged in
		if (principal != null) {
			flash.addFlashAttribute("info", "You are already logged in!");
			return "redirect:/";
		}
		
		if (error != null) {
			
			model.addAttribute("error", "Login Error: Password or username incorrect!!!!");
		}
		
		if (logout != null) {
			
			model.addAttribute("info", "You have signed out successfuly");
		}
		
		return "login";
	}
}
