package com.budgetbeat.controller;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import com.budgetbeat.manager.AccountManager;
import com.budgetbeat.manager.TagManager;
import com.budgetbeat.manager.UserManager;
import com.budgetbeat.pojo.Account;
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
		session.setAttribute("user", new User(4, "Tancho", "Mihov", "tahcho@mihov@abv.bg", "password"));

		return "home";
	}

	@RequestMapping(value = "/tags", method = RequestMethod.GET)
	public String prepareNewTag(Locale locale, Model model, HttpSession session, HttpServletResponse response) {
		response.addHeader("Cache-Control",
				"no-cache,no-store,private,must-revalidate,max-stale=0,post-check=0,pre-check=0");
		response.addHeader("Pragma", "no-cache");
		response.addDateHeader("Expires", 0);
		ApplicationContext context = new FileSystemXmlApplicationContext(
				"src\\main\\webapp\\WEB-INF\\spring\\beans.xml");

		TagManager tagManager = (TagManager) context.getBean("TagManager");

		model.addAttribute("user", (User) session.getAttribute("user"));
		List<Tag> tags = tagManager.listTgs(((User) session.getAttribute("user")).getUserID());
		model.addAttribute("tags", tags);
		model.addAttribute("tag", new Tag());
		return "tag";
	}

	@RequestMapping(value = "/tags", method = RequestMethod.POST)
	public String addReadyTags(Locale locale, @ModelAttribute Tag tag, Model model, HttpSession session,
			HttpServletResponse response) {
		response.addHeader("Cache-Control",
				"no-cache,no-store,private,must-revalidate,max-stale=0,post-check=0,pre-check=0");
		response.addHeader("Pragma", "no-cache");
		response.addDateHeader("Expires", 0);
		System.out.println(tag);
		ApplicationContext context = new FileSystemXmlApplicationContext(
				"src\\main\\webapp\\WEB-INF\\spring\\beans.xml");

		TagManager tagManager = (TagManager) context.getBean("TagManager");
		Integer userId = ((User) session.getAttribute("user")).getUserID();
		tagManager.create(tag.getName(), userId, null);

		model.addAttribute("user", (User) session.getAttribute("user"));
		List<Tag> tags = tagManager.listTgs(((User) session.getAttribute("user")).getUserID());
		model.addAttribute("tags", tags);
		model.addAttribute("tag", new Tag());
		return "tag";
	}

	@RequestMapping(value = "/tags/edit/{tag_id}", method = RequestMethod.GET)
	public String editTag(Locale locale, Model model, @PathVariable("tag_id") Integer tagId) {

		ApplicationContext context = new FileSystemXmlApplicationContext(
				"src\\main\\webapp\\WEB-INF\\spring\\beans.xml");

		TagManager tagManager = (TagManager) context.getBean("TagManager");
		Tag tag = tagManager.getTag(tagId);
		model.addAttribute("tag", tag);
		return "tag_edit";
	}

	@RequestMapping(value = "/tags/edit/{tag_id}", method = RequestMethod.POST)
	public String modifyTag(Locale locale, Model model, @ModelAttribute Tag tag, HttpServletResponse response,
			HttpSession session) {

		ApplicationContext context = new FileSystemXmlApplicationContext(
				"src\\main\\webapp\\WEB-INF\\spring\\beans.xml");

		TagManager tagManager = (TagManager) context.getBean("TagManager");
		tagManager.update(tag.getTagId(), tag.getName());

		response.addHeader("Cache-Control",
				"no-cache,no-store,private,must-revalidate,max-stale=0,post-check=0,pre-check=0");
		response.addHeader("Pragma", "no-cache");
		response.addDateHeader("Expires", 0);

		model.addAttribute("user", (User) session.getAttribute("user"));
		List<Tag> tags = tagManager.listTgs(((User) session.getAttribute("user")).getUserID());
		model.addAttribute("tags", tags);
		model.addAttribute("tag", new Tag());
		return "tag";
	}

	@RequestMapping(value = "/account", method = RequestMethod.GET)
	public String prepareNewAccount(Locale locale, Model model, HttpSession session, HttpServletResponse response) {
		response.addHeader("Cache-Control",
				"no-cache,no-store,private,must-revalidate,max-stale=0,post-check=0,pre-check=0");
		response.addHeader("Pragma", "no-cache");
		response.addDateHeader("Expires", 0);
		ApplicationContext context = new FileSystemXmlApplicationContext(
				"src\\main\\webapp\\WEB-INF\\spring\\beans.xml");

		AccountManager accountManager = (AccountManager) context.getBean("AccountManager");

		model.addAttribute("user", (User) session.getAttribute("user"));
		
		List<Account> accounts = accountManager.listAccounts(((User) session.getAttribute("user")).getUserID());
		model.addAttribute("accounts", accounts);
		model.addAttribute("account", new Account());
		return "accounts";
	}
	
	
	@RequestMapping(value = "/account", method = RequestMethod.POST)
	public String addReadAccount(Locale locale, @ModelAttribute Account account, Model model, HttpSession session,
			HttpServletResponse response) {
		response.addHeader("Cache-Control",
				"no-cache,no-store,private,must-revalidate,max-stale=0,post-check=0,pre-check=0");
		response.addHeader("Pragma", "no-cache");
		response.addDateHeader("Expires", 0);
		System.out.println(account);
		ApplicationContext context = new FileSystemXmlApplicationContext(
				"src\\main\\webapp\\WEB-INF\\spring\\beans.xml");

		AccountManager accountManager = (AccountManager) context.getBean("AccountManager");
		Integer userId = ((User) session.getAttribute("user")).getUserID();
		accountManager.create(userId, account.getName(), account.getBalance(), account.getInstitution(), account.getStatus());

		model.addAttribute("user", (User) session.getAttribute("user"));
		List<Account> accounts = accountManager.listAccounts(userId);
		model.addAttribute("accounts", accounts);
		model.addAttribute("account", new Account());
		return "accounts";
	}

}
