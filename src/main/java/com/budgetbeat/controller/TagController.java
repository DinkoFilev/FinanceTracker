package com.budgetbeat.controller;

import javax.servlet.http.HttpSession;

import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.budgetbeat.manager.TagManager;
import com.budgetbeat.manager.TransactionManager;
import com.budgetbeat.pojo.Tag;
import com.budgetbeat.pojo.User;

@Controller
public class TagController {

	@Autowired
	TagManager tagManager;// will inject dao from xml file

	@Autowired
	TransactionManager transactionManager;

	@RequestMapping("/test")
	public String test() {

		return "test";
	}

	/*
	 * It displays a form to input data, here "command" is a reserved request
	 * attribute which is used to display object data into form
	 */
	@RequestMapping("/tagform")
	public String showTagForm(Model model, HttpSession session) {
		User user = ((User) session.getAttribute("user"));
		// Check user
		if (user == null) {
			model.addAttribute("model", "login.jsp");
			return "index";
		} // End
		model.addAttribute("title", "Tag manager");
		model.addAttribute("model", "tagform.jsp");

		model.addAttribute("command", new Tag());
		return "logged";
	}

	/*
	 * It saves object into database. The @ModelAttribute puts request data into
	 * model object. You need to mention RequestMethod.POST method because
	 * default request is GET
	 */
	@RequestMapping(value = "/savetag", method = RequestMethod.POST)
	public String saveTag(@ModelAttribute("tag") Tag tag, HttpSession session, Model model) {
		User user = ((User) session.getAttribute("user"));

		if (user == null) {
			model.addAttribute("model", "login.jsp");
			return "redirect:/index";
		} // End

		if (tag.getName().length() < 2 || tag.getName().length() > 44) {
			model.addAttribute("model", "tagform.jsp");
			model.addAttribute("command", tag);
			model.addAttribute("error", "Tag name length error!");
			return "logged";
		}
		
		for (Tag tagElement : user.getTags().values()) {
			System.out.println(tagElement.getName() + " < >" + tag.getName());

			if (tagElement.getName().equals(tag.getName())) {
				model.addAttribute("title", "Tag manager123");
				model.addAttribute("model", "tagform.jsp");
				model.addAttribute("command", new Tag());
				model.addAttribute("error", "This tag exist!");
				System.out.println("ERROR TAGGGGGGGGGGGGGGGGG");
				return "logged";
			}
		}

		tag.setName(Jsoup.parse(tag.getName()).text());



		tag.setUserId(user.getUserID());

		user.addTag(tagManager.create(tag));

		return "redirect:/viewtag";// will redirect to viewemp
									// request mapping
	}

	/* It provides list of employees in model object */
	@RequestMapping("/viewtag")
	public String viewTags(HttpSession session, Model model) {
		User user = ((User) session.getAttribute("user"));
		// Check user
		if (user == null) {
			model.addAttribute("model", "login.jsp");
			return "index";
		} // End

		model.addAttribute("title", "Tags Manager");
		model.addAttribute("model", "viewtag.jsp");
		// model.addAttribute("list",list);
		return "logged";
	}

	// test as post
	@RequestMapping(value = "/edittag", method = RequestMethod.POST)
	public String editTagPost(@ModelAttribute("action") String action, @ModelAttribute("tagId") Integer tagId,
			HttpSession session, Model model) {
		User user = ((User) session.getAttribute("user"));
		// Check user
		if (user == null) {
			model.addAttribute("model", "login.jsp");
			return "index";
		} // End
		if (action.equals("edit")) {
			model.addAttribute("command", user.getTag(tagId));
			model.addAttribute("model", "tageditform.jsp");
			return ("logged");
		}
		return "redirect:/viewtag";
	}

	/* It updates model object. */
	@RequestMapping(value = "/editsavetag", method = RequestMethod.POST)
	public String editSaveTag(@ModelAttribute("tag") Tag tag, HttpSession session, Model model) {
		User user = ((User) session.getAttribute("user"));

		if (user == null) {
			model.addAttribute("model", "login.jsp");
			return "redirect:/index";
		} // End
		
		if (tag.getName().length() < 2 || tag.getName().length() > 44) {
			model.addAttribute("title", "Tag manager");
			model.addAttribute("model", "tageditform.jsp");
			model.addAttribute("command", tag);
			model.addAttribute("error", "Tag name length error!");
			return "logged";
		}

		if (tag.getTagId() == user.getTags().lastKey()) {
			model.addAttribute("title", "Tag manager");
			model.addAttribute("model", "tageditform.jsp");
			model.addAttribute("command", tag);
			model.addAttribute("error", "You can not edit default tag!!!");
			return "logged";
		}

		for (Tag tagElement : user.getTags().values()) {

			if (tagElement.getName().toLowerCase().equals(tag.getName().toLowerCase())
					&& tagElement.getTagId() != tag.getTagId()) {
				model.addAttribute("model", "tageditform.jsp");
				model.addAttribute("command", tag);
				model.addAttribute("error", "Tag " + tag.getName() + " exist!");
				return "logged";
			}
		}

		tag.setName(Jsoup.parse(tag.getName()).text());

		tagManager.update(tag.getTagId(), tag.getName());
		user.getTag(tag.getTagId()).setName(tag.getName());
		model.addAttribute("model", "viewtag.jsp");
		return "logged";
	}

	// test as post
	@RequestMapping(value = "/deletetag", method = RequestMethod.POST)
	public String deleteTagPost(@ModelAttribute("action") String action, Model model,
			@ModelAttribute("tagId") Integer tagId, HttpSession session) {
		User user = ((User) session.getAttribute("user"));
		if (user == null) {
			model.addAttribute("model", "login.jsp");
			return "redirect:/index";
		} // End

		if (tagId == user.getTags().lastKey()) {
			System.err.println("GLEDAI KUDE ME PRATI");
			model.addAttribute("error", "You can not delete default tag!!!");

			model.addAttribute("model", "viewtag.jsp");
			return "logged";
		}

		System.out.println("Will delete " + tagId);

		if (action.equals("delete")) {

			try {
				
				tagManager.delete(user, tagId, user.getTags().lastKey());

			} catch (DataAccessException e) {
				model.addAttribute("error", "The tag was not deleted!!!");
				e.printStackTrace();
			}

		}

		model.addAttribute("model", "viewtag.jsp");
		return "logged";
	}

}