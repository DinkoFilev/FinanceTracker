package com.budgetbeat.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import com.budgetbeat.manager.TagManager;
import com.budgetbeat.manager.UserManager;
import com.budgetbeat.pojo.Tag;
import com.budgetbeat.pojo.User;

/**
 * Handles requests for the application home page.
 */
@Controller

public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	/**
	 * Simply selects the home view to render by returning its name.
	 */

	@RequestMapping(value = "/form", method = RequestMethod.GET)
	public String home(Locale locale, Model model, HttpServletRequest request) {
		ApplicationContext context = new FileSystemXmlApplicationContext(
				"src\\main\\webapp\\WEB-INF\\spring\\beans.xml");

		logger.info("Welcome home! The client locale is {}.", locale);

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate);
		UserManager usermanager = (UserManager) context.getBean("UserManager");

		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		usermanager.create(firstName, lastName, email, password);
		System.out.println(firstName);
		System.out.println(lastName);
		System.out.println(email);
		System.out.println(password);

		return "home2";
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home2(Locale locale, Model model, HttpSession session) {
		// ApplicationContext context = new FileSystemXmlApplicationContext
		// ("src\\main\\webapp\\WEB-INF\\spring\\root-context.xml");
		logger.info("Welcome home! The client locale is {}.", locale);

		User user = new User();
		user.setUserID(4);

		System.out.println(user);

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate);

		return "home";
	}

	// @RequestMapping(value = "/tags1", method = RequestMethod.GET)
	// public String tags(Locale locale, Model model, HttpServletRequest
	// request) {
	//// ApplicationContext context = new FileSystemXmlApplicationContext
	//// ("src\\main\\webapp\\WEB-INF\\spring\\root-context.xml");
	// logger.info("Welcome home! The client locale is {}.", locale);
	// UserManager usermanager = (UserManager) context.getBean("UserManager");
	//
	// String firstName = request.getParameter("firstName");
	// String lastName = request.getParameter("lastName");
	// String email = request.getParameter("email");
	// String password = request.getParameter("password");
	// usermanager.create(firstName, lastName, email, password);
	// System.out.println(firstName);
	// System.out.println(lastName);
	// System.out.println(email);
	// System.out.println(password);
	//
	//
	// model.addAttribute("serverTime", formattedDate );
	//
	//
	//
	// return "home";
	// }

	@RequestMapping(value = "/tags", method = RequestMethod.GET)
	public String prepareNewTag(Model model, HttpSession session) {
		System.out.println("Tags mapp");
		User user = new User();
		user.setUserID(4);
		model.addAttribute("user", "Ivan Ivanov");
		session.setAttribute("user", user);
		model.addAttribute("tag", new Tag());
		return "tag";
	}

	@RequestMapping(value = "/tags", method = RequestMethod.POST)
	public String addReadyTags(@ModelAttribute Tag tag, Model model, HttpSession session) {
		System.out.println(tag);
		ApplicationContext context = new FileSystemXmlApplicationContext(
				"src\\main\\webapp\\WEB-INF\\spring\\beans.xml");

		logger.info("Tag created {}.", tag);

		TagManager tagManager = (TagManager) context.getBean("TagManager");
		Integer userId = 4;
		//Integer userId = ((User) session.getAttribute("user")).getUserID();
		tagManager.create(tag.getName(), userId, null);
		return "tag";
	}

}
