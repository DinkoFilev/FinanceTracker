package com.budgetbeat.controller;

import java.util.Locale;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.boot.autoconfigure.web.ServerProperties.Session;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.budgetbeat.SpringWebConfig;
import com.budgetbeat.manager.UserManager;
import com.budgetbeat.pojo.Tag;
import com.budgetbeat.pojo.Transaction;
import com.budgetbeat.pojo.User;

@Controller

public class UserController {
	@RequestMapping(value = "/*", method = RequestMethod.GET)
	public String index(Locale locale, Model model, HttpServletRequest request,HttpSession session) {
		if(session.getAttribute("user") != null){
			model.addAttribute("model","dashboard.jsp");
			return "logged";
		}
		System.out.println(session.getId());
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
	public  String registered(Locale locale, Model model, HttpServletRequest request) {

		UserManager usermanager = (UserManager) SpringWebConfig.context.getBean("UserManager");
		
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String status = usermanager.registerValidation(null ,firstName, lastName, email, password);
		System.out.println(status);
		if (status.equals("register")) {
			try {
				int userID = usermanager.create(firstName, lastName, email, password);
				usermanager.insertDefaultTagsAndAccounts(userID);
			} catch (Exception e) {
				e.printStackTrace();
				return "errorpage";
				
			}
			model.addAttribute("model","login.jsp");
			return "index";
			
		}
		
		model.addAttribute("model","register.jsp");
		model.addAttribute("status",status);
		 return "index";
		
		
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Locale locale, Model model, HttpServletRequest request) {
		
		model.addAttribute("model","login.jsp");
		return "index";
		
		
		
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)

	
	public String signIn(Locale locale, Model model, HttpServletRequest request,HttpSession session) {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		UserManager usermanager = (UserManager) SpringWebConfig.context.getBean("UserManager");
		String status = usermanager.loginValidation(email, password);
		System.out.println(status);
		if(status.equals("success")){
			session.setAttribute("user", usermanager.addUserToSession(email));
			
			return "redirect:/dashboard";
		}
		System.out.println("testovo stava");
		model.addAttribute("model","login.jsp");
		model.addAttribute("status",status);
		
		return "index";
		
		
	}
	

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public String dashboard(Locale locale, Model model, HttpServletRequest request,HttpSession session) {
		if(session.getAttribute("user") == null){
			model.addAttribute("model","login.jsp");
			return "index";
		}
		model.addAttribute("pagename","Dashboard");
		model.addAttribute("model","dashboard.jsp");
		return "logged";
		
		
	}
	
	@RequestMapping(value = "/settings", method = RequestMethod.GET)
	public String settings(Locale locale, Model model, HttpServletRequest request,HttpSession session) {
		User user = (User)session.getAttribute("user");
		if(user == null){
			model.addAttribute("model","login.jsp");
			return "index";
		}
		User newUser = new User(null, user.getFirstName(), user.getLastName(), user.getEmail(), null);
		model.addAttribute("command",newUser);
		model.addAttribute("model","settings.jsp");
		return "logged";
		
		
	}
	
	@RequestMapping(value = "/editsettings", method = RequestMethod.POST)
	public String editsettings(@ModelAttribute("usera")User user,@ModelAttribute("newpassword")String newpassword,@ModelAttribute("repeatpassword")String repeatpassword, Locale locale, Model model, HttpServletRequest request,HttpSession session) {
		User sessionUser = (User) session.getAttribute("user");
		UserManager userManager = (UserManager) SpringWebConfig.context.getBean("UserManager");
		if(sessionUser == null){
			model.addAttribute("model","login.jsp");
			return "index";
		}
		System.out.println(user.toString());
		String registerValidation = userManager.registerValidation(user,user.getFirstName(), user.getLastName(), user.getEmail(), null);
		
		
		if(registerValidation != "register"){
			
			model.addAttribute("model", "settings.jsp");
			model.addAttribute("command", user);
			model.addAttribute("status", registerValidation);
			return "logged";
		}
		System.out.println(sessionUser.getPassword()+" "+ String.valueOf(userManager.MD5Convert(user.getPassword())));
		if (!sessionUser.getPassword().equals(String.valueOf(userManager.MD5Convert(user.getPassword())))) {
			
			model.addAttribute("model", "settings.jsp");
			model.addAttribute("command", user);
			model.addAttribute("status", "Wrong password");
			return "logged";
		}
		
		if (!(newpassword.equals(repeatpassword)) || newpassword.length() < 3) {
			
			model.addAttribute("model", "settings.jsp");
			model.addAttribute("command", user);
			model.addAttribute("status", "Passwords don't match or length is under 3 symbols");
			return "logged";
		}
		
		userManager.update(sessionUser ,sessionUser.getUserID(), user.getFirstName(), user.getLastName(),user.getEmail(),user.getPassword());
		String status = "You should re-login";
		if(!sessionUser.getEmail().equals(user.getEmail())){
			status = "You can re-login with your new email now";
		}
		session.invalidate();
		model.addAttribute("status",status);
		model.addAttribute("model","login.jsp");
		return "index";
	}
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(Locale locale, Model model, HttpServletRequest request,HttpSession session) {
		if(session.getAttribute("user") == null){
			model.addAttribute("model","login.jsp");
			return "index";
		}
		System.out.println(session.getId());
		session.invalidate();
		
		model.addAttribute("model","login.jsp");
		return "index";
		
		
	}
	

	
}
