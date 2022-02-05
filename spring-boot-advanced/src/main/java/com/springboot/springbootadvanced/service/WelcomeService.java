package com.springboot.springbootadvanced.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WelcomeService {
	
	@Value("${welcome.message}")
	private String welcomeMessage;
	
	public WelcomeService() {
		super();
	}
	
	 @PostConstruct
	    public void init() {
	        System.out.println("================== " + welcomeMessage + "================== ");
	    }
	
	public String retrieveWelcomeMessage() {
		return welcomeMessage;
	}

}
