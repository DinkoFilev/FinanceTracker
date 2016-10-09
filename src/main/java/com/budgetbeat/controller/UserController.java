package com.budgetbeat.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.budgetbeat.SpringWebConfig;
import com.budgetbeat.manager.UserManager;

@Controller

public class UserController {
	@RequestMapping(value = "/*", method = RequestMethod.GET)
	public String index(Locale locale, Model model, HttpServletRequest request) {
		
		model.addAttribute("model","login.jsp");
		return "index";
		
		
	}
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String indexPOST(Locale locale, Model model, HttpServletRequest request) {
		
		model.addAttribute("model","login.jsp");
		return "index";
		
		
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String register(Locale locale, Model model, HttpServletRequest request) {
		
		
		model.addAttribute("model","register.jsp");
		return "index";
		
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public  ModelAndView registered(Locale locale, Model model, HttpServletRequest request) {
		
		

		UserManager usermanager = (UserManager) SpringWebConfig.context.getBean("UserManager");
		
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String status = usermanager.registerValidation(firstName, lastName, email, password);
		System.out.println(status);
		if (status.equals("register")) {
			try {
				usermanager.create(firstName, lastName, email, password);
			} catch (Exception e) {
				return new ModelAndView("errorpage");
				
			}
			
			return new ModelAndView("redirect:/login");
			
		}else{
		 model.addAttribute("status",status);
		 return new ModelAndView("home");
		}
		
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Locale locale, Model model, HttpServletRequest request) {
		
		model.addAttribute("model","login.jsp");
		return "index";
		
		
		
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String signIn(Locale locale, Model model, HttpServletRequest request) {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		UserManager usermanager = (UserManager) SpringWebConfig.context.getBean("UserManager");
		String status = usermanager.loginValidation(email, password);
		System.out.println(status);
		if(status.equals("success")){
			return "home2";
		}
		
		model.addAttribute("model","login.jsp");
		model.addAttribute("status",status);
		
		return "index";
		
		
	}
	
}
