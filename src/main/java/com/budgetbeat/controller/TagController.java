package com.budgetbeat.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.budgetbeat.manager.TagManager;
import com.budgetbeat.manager.UserManager;
import com.budgetbeat.pojo.Account;
import com.budgetbeat.pojo.Tag;
import com.budgetbeat.pojo.Transaction;
import com.budgetbeat.pojo.User;
import com.sun.media.sound.ModelDestination;

@Controller
public class TagController {

	@Autowired
	TagManager tagManager;// will inject dao from xml file

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
		Tag tag = new Tag();

		model.addAttribute("command", tag);
		return "logged";
	}

	/*
	 * It saves object into database. The @ModelAttribute puts request data into
	 * model object. You need to mention RequestMethod.POST method because
	 * default request is GET
	 */
	@RequestMapping(value = "/savetag", method = RequestMethod.POST)
	public ModelAndView saveTag(@ModelAttribute("tag") Tag tag, HttpSession session, Model model) {
		User user = ((User) session.getAttribute("user"));

		if (user == null) {
			model.addAttribute("model", "login.jsp");
			return new ModelAndView("redirect:/index");
		} // End
		tag.setUserId(user.getUserID());
		tag.setParentId(3);

		user.addTag(tagManager.create(tag));

		return new ModelAndView("redirect:/viewtag");// will redirect to viewemp
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
			// Integer userId = ((User)
			// session.getAttribute("user")).getUserID();

		// List<Tag> list = tagManager.listTgs(userId);
		model.addAttribute("title", "TAGOVETE");
		model.addAttribute("model", "viewtag.jsp");
		// model.addAttribute("list",list);
		return "logged";
	}

	/*
	 * It displays object data into form for the given id. The @PathVariable
	 * puts URL data into variable.
	 */
	// @RequestMapping(value = "/edittag/{id}")
	// public ModelAndView editTag(@PathVariable int id) {
	// Tag tag = tagManager.getTag(id);
	// return new ModelAndView("tageditform", "command", tag);
	// }

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

	@RequestMapping(value = "/transactions_by_tag", method = RequestMethod.POST)
	public String showTransactionByTag(@ModelAttribute("tagId") Integer tagId, HttpSession session, Model model) {
		User user = ((User) session.getAttribute("user"));
		
		// Check user
		if (user == null) {
			model.addAttribute("model", "login.jsp");
			return "index";
		} // End
		
		
		
		
		Tag tag = tagManager.getTag(tagId);
		

		// TODO
		// List<Transaction> transactions =
		// userManager.listReansactionsByTagId(tagId);

		List<Transaction> transactions = new ArrayList<Transaction>();

		Double income = 0.0;
		Double expence = 0.0;
		for (Transaction element : transactions) {
			if (element.getAmount() < 0) {
				expence += element.getAmount();
			} else {
				income += element.getAmount();
			}
		}

		model.addAttribute("income", String.format("%.2f", income));
		model.addAttribute("expence", String.format("%.2f", expence));
		model.addAttribute("tagName", tag.getName());
		model.addAttribute("transactions", transactions);
		model.addAttribute("model", "view_transaction_by_tag.jsp");

		return "logged";
	}

	/* It updates model object. */
	@RequestMapping(value = "/editsavetag", method = RequestMethod.POST)
	public ModelAndView editSaveTag(@ModelAttribute("tag") Tag tag, HttpSession session, Model model) {
		User user = ((User) session.getAttribute("user"));

		if (user == null) {
			model.addAttribute("model", "login.jsp");
			return new ModelAndView("redirect:/index");
		} // End

		tagManager.update(tag.getTagId(), tag.getName());

		return new ModelAndView("redirect:/viewtag");
	}

	/* It deletes record for the given id in URL and redirects to /viewemp */
	// @RequestMapping(value = "/deletetag/{id}", method = RequestMethod.GET)
	// public ModelAndView deleteTag(@PathVariable int id) {
	// tagManager.delete(id);
	// return new ModelAndView("redirect:/viewtag");
	// }

	// test as post
	@RequestMapping(value = "/deletetag", method = RequestMethod.POST)
	public ModelAndView deleteTagPost(@ModelAttribute("action") String action, Model model,
			@ModelAttribute("tagId") Integer tagId, HttpSession session) {
		User user = ((User) session.getAttribute("user"));
		if (user == null) {
			model.addAttribute("model", "login.jsp");
			return new ModelAndView("redirect:/index");
		} // End
		if (action.equals("delete")) {
			// user.deleteTag(tagId)
			tagManager.delete(tagId);
		}
		return new ModelAndView("redirect:/viewtag");
	}

}