package com.budgetbeat.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.budgetbeat.manager.TransactionManager;
import com.budgetbeat.pojo.Tag;
import com.budgetbeat.pojo.Transaction;

@Controller
public class TransactionController {
	@Autowired
	TransactionManager transactionManager;
	
	@RequestMapping(value = "/trans", method = RequestMethod.GET)
	public ModelAndView transGet(Locale locale, Model model, HttpServletRequest request) {
		
		List<Transaction> list = transactionManager.getListOfTransactionByUserID(2);
		return new ModelAndView("transactions", "list", list);
		
	}
	@RequestMapping(value = "/trans", method = RequestMethod.POST)
	public ModelAndView transPost(Locale locale, Model model, HttpServletRequest request) {
		
		
		return new ModelAndView("transactions");
		
	}
	

}
