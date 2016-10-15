package com.budgetbeat.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.sql.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.budgetbeat.manager.TagManager;
import com.budgetbeat.manager.UserManager;
import com.budgetbeat.pojo.Account;
import com.budgetbeat.pojo.KeyValue;
import com.budgetbeat.pojo.Tag;
import com.budgetbeat.pojo.Transaction;
import com.budgetbeat.pojo.User;
import com.budgetbeat.util.Utils;
import com.sun.media.sound.ModelDestination;

@Controller
public class TagController {

	@Autowired
	TagManager tagManager;// will inject dao from xml file

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
			// Integer userId = ((User)
			// session.getAttribute("user")).getUserID();

		// List<Tag> list = tagManager.listTgs(userId);
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

	@RequestMapping(value = "/transactions_by_tag", method = RequestMethod.POST)
	public String showTransactionByTag(@ModelAttribute("tagId") Integer tagId, HttpSession session, Model model,
			HttpServletRequest request) {
		User user = ((User) session.getAttribute("user"));
		// if (fromDate == null) {
		// fromDate = Utils.addMonth(new Date(), 1);
		// }
		//
		// if (toDate == null) {
		// toDate = new Date();
		// }
		// Integer tagId = (Integer) request.getAttribute("tagId");
		// System.out.println(tagId);

		System.out.println(request.getParameter("from"));
		System.out.println(request.getParameter("to"));

		// System.out.println("From:" + fromDate.toString());
		// System.out.println("To:" + toDate.toString());

		// Check user
		if (user == null) {
			model.addAttribute("model", "login.jsp");
			return "index";
		} // End

		List<Transaction> transactions = new ArrayList<Transaction>();
		ArrayList<KeyValue> graph = new ArrayList<KeyValue>();
		Double income = 0.0;
		Double expence = 0.0;

		for (Entry<Integer, Transaction> transaction : user.getTransactions().entrySet()) {
			System.out.println(transaction.getValue());
			if (transaction.getValue().getFk_tag_id() == tagId
					&& transaction.getValue().getDate().after(Date.valueOf("2016-09-01"))
					&& transaction.getValue().getDate().before(Date.valueOf("2016-10-15"))) {
				transactions.add(transaction.getValue());
				System.out.println("TAG ID" + tagId + "TRAN" + transaction.getValue().getDescription());
			
				graph.add(new KeyValue(transaction.getValue().getDescription(),
						String.valueOf(transaction.getValue().getAmount())));

				if (transaction.getValue().getAmount() < 0) {
					expence += transaction.getValue().getAmount();
				} else {
					income += transaction.getValue().getAmount();

				}
			}
		}
		System.out.println(transactions);
		model.addAttribute("graph", graph);
		model.addAttribute("income", String.format("%.2f", income));
		model.addAttribute("expence", String.format("%.2f", expence));
		model.addAttribute("tag", user.getTag(tagId));
		model.addAttribute("transactions", transactions);
		model.addAttribute("model", "view_transaction_by_tag.jsp");
		for (KeyValue keyValue : graph) {
			System.out.println(keyValue.getKey() + " : " + keyValue.getValue());
		}
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
		user.getTag(tag.getTagId()).setName(tag.getName());

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
			user.getTags().remove(tagId);
		}
		return new ModelAndView("redirect:/viewtag");
	}

}