package com.budgetbeat.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.sql.Date;
import java.util.List;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.budgetbeat.manager.TagManager;
import com.budgetbeat.manager.TransactionManager;
import com.budgetbeat.pojo.Account;
import com.budgetbeat.pojo.KeyValue;
import com.budgetbeat.pojo.Tag;
import com.budgetbeat.pojo.Transaction;
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
	public String showTransactionByTag(HttpSession session, Model model, @ModelAttribute("accountId") Integer accountId,
			@ModelAttribute("tagId") Integer tagId, HttpServletRequest request) {
		User user = ((User) session.getAttribute("user"));

		System.err.println("ERRRRROR" + tagId);
		// Check user
		if (user == null) {
			model.addAttribute("model", "login.jsp");
			return "index";
		} // End

		System.out.println("ACCOUNT: " + accountId);
		System.out.println("TAG: " + tagId);

		System.err.println(request.getParameter("tadId"));
		System.err.println(request.getParameter("accountId"));

		if (request.getParameter("tadId") != null) {
			tagId = Integer.valueOf(request.getParameter("tagId"));
		}

		if (request.getParameter("accountId") != null) {
			accountId = Integer.valueOf(request.getParameter("accountId"));
		}

		System.out.println("ACCOUNT: " + accountId);
		System.out.println("TAG: " + tagId);

		Calendar currenttime = Calendar.getInstance();
		Date toDate;
		if (request.getParameter("toDate") == null) {
			toDate = new Date((currenttime.getTime()).getTime());
		} else {
			toDate = Date.valueOf(request.getParameter("toDate"));
		}

		Date fromDate;
		if (request.getParameter("fromDate") == null) {
			fromDate = Date.valueOf(toDate.toLocalDate().minusMonths(1));
		} else {
			fromDate = Date.valueOf(request.getParameter("fromDate"));
		}

		System.out.println("From:" + fromDate.toString());
		System.out.println("To:" + toDate.toString());

		List<Transaction> transactions = new ArrayList<Transaction>();
		ArrayList<KeyValue> graph = new ArrayList<KeyValue>();
		Double income = 0.0;
		Double expence = 0.0;

		boolean accountCheck, tagCheck, fromDateCheck, toDateCheck;

		for (Entry<Integer, Transaction> entry : user.getTransactions().entrySet()) { // Transactions
			System.out.println(entry.getValue());

			// Tag
			if (tagId == 0) {
				tagCheck = true;
			} else {
				tagCheck = (entry.getValue().getFk_tag_id() == tagId);
			}

			// Account
			if (accountId == 0) {
				accountCheck = true;
			} else {
				accountCheck = (entry.getValue().getFt_account_id() == accountId);
			}

			fromDateCheck = entry.getValue().getDate().after(fromDate) || entry.getValue().getDate().equals(fromDate);

			toDateCheck = entry.getValue().getDate().before(toDate) || entry.getValue().getDate().equals(toDate);

			// All check
			if (accountCheck && tagCheck && fromDateCheck && toDateCheck) {

				transactions.add(entry.getValue());

				// if (!entry.getValue().getIncome()) {
				// graph.add(new KeyValue(entry.getValue().getDescription(),
				// String.valueOf((entry.getValue().getAmount() * -1)), null));
				// }

				graph.add(new KeyValue(entry.getValue().getDescription(),
						String.valueOf((Math.abs(entry.getValue().getAmount()))), null));

				if (entry.getValue().getAmount() < 0) {
					expence += entry.getValue().getAmount();
				} else {
					income += entry.getValue().getAmount();
				}
			}
		}

		System.out.println(transactions);
		model.addAttribute("graph", graph);
		model.addAttribute("income", String.format("%.2f", income));
		model.addAttribute("expence", String.format("%.2f", expence));
		model.addAttribute("tag", user.getTag(tagId));
		model.addAttribute("tagId", tagId);
		model.addAttribute("accountId", accountId);
		model.addAttribute("user", user);
		model.addAttribute("transactions", transactions);
		model.addAttribute("model", "view_transaction_by_tag.jsp");

		for (KeyValue keyValue : graph) {
			System.out.println(keyValue.getKey() + " : " + keyValue.getValue());
		}
		return "logged";
	}

	/* It updates model object. */
	@RequestMapping(value = "/editsavetag", method = RequestMethod.POST)
	public String editSaveTag(@ModelAttribute("tag") Tag tag, HttpSession session, Model model) {
		User user = ((User) session.getAttribute("user"));

		if (user == null) {
			model.addAttribute("model", "login.jsp");
			return "redirect:/index";
		} // End

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
				model.addAttribute("title", "Tag manager");
				model.addAttribute("model", "tageditform.jsp");
				model.addAttribute("command", tag);
				model.addAttribute("error", "Tag " + tag.getName() + " exist!");
				return "logged";
			}
		}

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
					
			
			tagManager.delete(user,tagId,user.getTags().lastKey());
			
		}
		
		model.addAttribute("model", "viewtag.jsp");
		return "logged";
	}

}