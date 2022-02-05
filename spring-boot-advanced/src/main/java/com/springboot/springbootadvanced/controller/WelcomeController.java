package com.springboot.springbootadvanced.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.springbootadvanced.configuration.BasicConfiguration;
import com.springboot.springbootadvanced.service.WelcomeService;

@RestController
public class WelcomeController {
	
	//Auto wiring
		@Autowired
		private WelcomeService service;
		
		@Autowired
		private BasicConfiguration basicConfiguration;

		@RequestMapping("/welcome")
		public String welcome() {
			return service.retrieveWelcomeMessage();
		}
		
		@RequestMapping("/dynamic-configuration")
		public Map dynamicConfiguration() {
			Map map = new HashMap();
			map.put("message", basicConfiguration.getMessage());
			map.put("number", basicConfiguration.getNumber());
			map.put("value", basicConfiguration.isValue());
			return map;
		}

}
