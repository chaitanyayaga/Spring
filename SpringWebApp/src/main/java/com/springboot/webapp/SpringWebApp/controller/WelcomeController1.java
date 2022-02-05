package com.springboot.webapp.SpringWebApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WelcomeController1 {

	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	@ResponseBody
	public String showWelcomePage() {
		//model.put("name", "Chaitanya");
		return "welcome";
	}
	
	/*private String getLoggedinUserName() {
		Object principal = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		
		if (principal instanceof UserDetails) {
			return ((UserDetails) principal).getUsername();
		}
		
		return principal.toString();
	} */

}
