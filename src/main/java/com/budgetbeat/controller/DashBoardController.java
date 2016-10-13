package com.budgetbeat.controller;

import java.util.ArrayList;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.budgetbeat.pojo.KeyValue;
import com.budgetbeat.pojo.User;

@Controller
public class DashBoardController {

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String index(Locale locale, Model model, HttpServletRequest request,HttpSession session){
		User user = (User)session.getAttribute("user");
		ArrayList<KeyValue> graph = new ArrayList<KeyValue>();
		graph.add(new KeyValue("asd", "17098242"));
		graph.add(new KeyValue("asdasd", "9984670"));
		graph.add(new KeyValue("UffffSA", "9826675"));
		graph.add(new KeyValue("Chiffffna", "9596961"));
		graph.add(new KeyValue("Braffzil", "8514877"));
		graph.add(new KeyValue("Austffralia", "7741220"));
		graph.add(new KeyValue("Indfffia", "3287263"));
	
		model.addAttribute("list", graph);
		
		return "test";
		
		
	}
	
}
