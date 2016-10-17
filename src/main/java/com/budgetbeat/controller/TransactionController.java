package com.budgetbeat.controller;



import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.budgetbeat.SpringWebConfig;
import com.budgetbeat.manager.TransactionManager;
import com.budgetbeat.manager.UserManager;
import com.budgetbeat.pojo.Account;
import com.budgetbeat.pojo.KeyValue;
import com.budgetbeat.pojo.Tag;
import com.budgetbeat.pojo.Transaction;
import com.budgetbeat.pojo.User;

@Controller
public class TransactionController {
	
	@RequestMapping(value = "/transactions_by_tag", method = { RequestMethod.POST, RequestMethod.GET })
	public String showTransactions(HttpSession session, Model model, @ModelAttribute("accountId") Integer accountId,
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
		ArrayList<KeyValue> graphIncome = new ArrayList<KeyValue>();
		ArrayList<KeyValue> graphExpense = new ArrayList<KeyValue>();
		ArrayList<KeyValue> graphCompare = new ArrayList<KeyValue>();
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

				if (entry.getValue().getAmount() < 0) {
					expence += entry.getValue().getAmount();
					graphExpense.add(new KeyValue(entry.getValue().getDescription(),
							String.valueOf((Math.abs(entry.getValue().getAmount()))), null));

				} else {
					income += entry.getValue().getAmount();
					graphIncome.add(new KeyValue(entry.getValue().getDescription(),
							String.valueOf((Math.abs(entry.getValue().getAmount()))), null));

				}
			}
		}

		graphCompare.add(new KeyValue("Income", String.valueOf(income), null));
		graphCompare.add(new KeyValue("Expense", String.valueOf(expence * -1), null));
		System.out.println(transactions);
		
		model.addAttribute("graphIncome", graphIncome);
		model.addAttribute("graphExpense", graphExpense);
		model.addAttribute("graphCompare", graphCompare);

		model.addAttribute("income", String.format("%.2f", income));
		model.addAttribute("expence", String.format("%.2f", expence));
		model.addAttribute("tag", user.getTag(tagId));
		model.addAttribute("tagId", tagId);
		model.addAttribute("accountId", accountId);
		model.addAttribute("user", user);
		model.addAttribute("transactions", transactions);
		model.addAttribute("model", "view_transaction_by_tag.jsp");
		return "logged";
	}
	
	
	
	
	
	@RequestMapping(value = "/viewtransaction", method = RequestMethod.GET)
	public ModelAndView transGet(Locale locale, Model model, HttpServletRequest request,HttpSession session, HttpServletResponse response) {
		User user = ((User) session.getAttribute("user"));
		if(user == null){
			model.addAttribute("model","login.jsp");
			return new ModelAndView("index");
		}

		model.addAttribute("tagId", 0);
		model.addAttribute("accountId", 0);
		
		return new ModelAndView("redirect:/transactions_by_tag");
		
	}
	@RequestMapping(value = "/transactionform", method = RequestMethod.GET)
	public String transPost(Locale locale, Model model, HttpServletRequest request,HttpSession session) {
		User user = ((User) session.getAttribute("user"));
		if(user == null){
			model.addAttribute("model","login.jsp");
			return "index";
		}
		model.addAttribute("command", new Transaction());
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
		if(!transaction.getIncome()){
			transaction.setAmount(transaction.getAmount()*-1);
		}
		transaction.setTransaction_id(transactionManager.create(user.getUserID(),transaction.getFt_account_id(), transaction.getFk_tag_id(), transaction.getDescription(), transaction.getAmount(), transaction.getDate(), "", true, (long) 0, true,transaction.getIncome()));
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
			Transaction transaction = user.getTransaction(id);
			if(!transaction.getIncome()){
			transaction.setAmount(transaction.getAmount()*-1);
			}
			model.addAttribute("command", transaction);
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
		if(!transaction.getIncome()){
			transaction.setAmount(transaction.getAmount()*-1);
		}
		transactionManager.update(transaction.getTransaction_id(),user.getUserID(),transaction.getFt_account_id(), transaction.getFk_tag_id(), transaction.getDescription(), transaction.getAmount(), transaction.getDate(), "asdsa", true,(long) 0, true,transaction.getIncome());
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
