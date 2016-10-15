package com.budgetbeat.controller;



import java.sql.Date;
import java.time.LocalDate;
import java.util.Locale;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.budgetbeat.SpringWebConfig;
import com.budgetbeat.manager.TransactionManager;
import com.budgetbeat.manager.UserManager;
import com.budgetbeat.pojo.Transaction;
import com.budgetbeat.pojo.User;

@Controller
public class TransactionController {
	
	@RequestMapping(value = "/viewtransaction", method = RequestMethod.GET)
	public String transGet(Locale locale, Model model, HttpServletRequest request,HttpSession session) {
		User user = ((User) session.getAttribute("user"));
		if(user == null){
			model.addAttribute("model","login.jsp");
			return "index";
		}
		UserManager usermanager = (UserManager) SpringWebConfig.context.getBean("UserManager");
		TransactionManager transactionManager = (TransactionManager) SpringWebConfig.context.getBean("TransactionManager");
		
		
		TreeMap<Integer,Transaction> list = user.getTransactions();
		model.addAttribute("list", list);
		model.addAttribute("model","viewtransactions.jsp");
		return "logged";
		
	}
	@RequestMapping(value = "/transactionform", method = RequestMethod.GET)
	public String transPost(Locale locale, Model model, HttpServletRequest request,HttpSession session) {
		User user = ((User) session.getAttribute("user"));
		if(user == null){
			model.addAttribute("model","login.jsp");
			return "index";
		}
		Transaction transaction = new Transaction();
		transaction.setDate(Date.valueOf(LocalDate.now()));
		model.addAttribute("command", transaction);
		//model.addAttribute("collection",user.getAccounts());
		
		model.addAttribute("model","transactionform.jsp");
		return "logged";
		
	}
	
	@RequestMapping(value = "/savetransaction", method = RequestMethod.POST)
	public ModelAndView saveAccount(@ModelAttribute("transaction") Transaction transaction, HttpSession session,Model model) {
		User user = ((User) session.getAttribute("user"));
	
		if(user == null){
			model.addAttribute("model","login.jsp");
			return new ModelAndView("redirect:/index");
		}
		TransactionManager transactionManager = (TransactionManager) SpringWebConfig.context.getBean("TransactionManager");
		
		transaction.setTransaction_id(transactionManager.create(user.getUserID(),transaction.getFt_account_id(), transaction.getFk_tag_id(), transaction.getDescription(), transaction.getAmount(), transaction.getDate(), "asdsa", true, (long) 123123, true));
		user.addTransaction(transaction);

		return new ModelAndView("redirect:/viewtransaction");
	}
	
	@RequestMapping(value = "/edittransaction", method = RequestMethod.POST)
	public String editTagPost(@ModelAttribute("action") String action, @ModelAttribute("transactionId") Integer id,
			HttpSession session, Model model) {
		User user = ((User) session.getAttribute("user"));
		// Check user
		if (user == null) {
			model.addAttribute("model", "login.jsp");
			return "index";
		} // End
		if (action.equals("edit")) {
			model.addAttribute("command", user.getTransaction(id));
			model.addAttribute("model", "transactioneditform.jsp");
			return ("logged");
		}
		return "redirect:/viewtransaction";
	}
	
	@RequestMapping(value = "/editsavetransaction", method = RequestMethod.POST)
	public ModelAndView editSaveTag(@ModelAttribute("transaction") Transaction transaction, HttpSession session, Model model) {
		User user = ((User) session.getAttribute("user"));
		if (user == null) {
			model.addAttribute("model", "login.jsp");
			return new ModelAndView("redirect:/index");
		} 
		TransactionManager transactionManager = (TransactionManager) SpringWebConfig.context.getBean("TransactionManager");
		transactionManager.update(transaction.getTransaction_id(),user.getUserID(),transaction.getFt_account_id(), transaction.getFk_tag_id(), transaction.getDescription(), transaction.getAmount(), transaction.getDate(), "asdsa", true, (long) 123123, true);
		transaction.setFk_user_id(user.getUserID());
		user.addTransaction(transaction);
		return new ModelAndView("redirect:/viewtransaction");
	}
	
	@RequestMapping(value = "/deletetransaction", method = RequestMethod.POST)
	public ModelAndView deleteTagPost(@ModelAttribute("action") String action, Model model,
			@ModelAttribute("transactionId") Integer transactionID, HttpSession session) {
		User user = ((User) session.getAttribute("user"));
		if (user == null) {
			model.addAttribute("model", "login.jsp");
			return new ModelAndView("redirect:/index");
		} 
		
		TransactionManager transactionManager = (TransactionManager) SpringWebConfig.context.getBean("TransactionManager");
		if (action.equals("delete")) {
			transactionManager.delete(transactionID);
			user.getTransactions().remove(transactionID);
		}
		return new ModelAndView("redirect:/viewtransaction");
	}


	

}
