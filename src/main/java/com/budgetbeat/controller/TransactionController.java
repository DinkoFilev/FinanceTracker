package com.budgetbeat.controller;



import java.sql.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.budgetbeat.SpringWebConfig;
import com.budgetbeat.manager.TransactionManager;
import com.budgetbeat.manager.UserManager;
import com.budgetbeat.pojo.Account;
import com.budgetbeat.pojo.Tag;
import com.budgetbeat.pojo.Transaction;
import com.budgetbeat.pojo.User;

@Controller
public class TransactionController {
	
	@RequestMapping(value = "/viewtransaction", method = RequestMethod.GET)
	public String transGet(Locale locale, Model model, HttpServletRequest request,HttpSession session) {
		
		UserManager usermanager = (UserManager) SpringWebConfig.context.getBean("UserManager");
		TransactionManager transactionManager = (TransactionManager) SpringWebConfig.context.getBean("TransactionManager");
	
		Long time = System.currentTimeMillis();
		//transactionManager.create(4,4,10,"TransactionTest",550.0,new Date(time),"putqdofaila",true,(long)12312312,true);
		if(session.getAttribute("user") == null){
			model.addAttribute("model","login.jsp");
			return "index";
		}
		User user = (User)session.getAttribute("user");
		TreeMap<Integer,Transaction> list = user.getTransactions();
		model.addAttribute("list", list);
		model.addAttribute("model","viewtransactions.jsp");
		return "logged";
		
	}
	@RequestMapping(value = "/trans", method = RequestMethod.POST)
	public ModelAndView transPost(Locale locale, Model model, HttpServletRequest request) {
		
		
		return new ModelAndView("transactions");
		
	}
	

}
