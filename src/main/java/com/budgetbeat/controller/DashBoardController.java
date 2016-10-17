package com.budgetbeat.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.budgetbeat.SpringWebConfig;
import com.budgetbeat.manager.TransactionManager;
import com.budgetbeat.pojo.Account;
import com.budgetbeat.pojo.KeyValue;
import com.budgetbeat.pojo.Tag;
import com.budgetbeat.pojo.Transaction;
import com.budgetbeat.pojo.User;

@Controller
public class DashBoardController {

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String index(Locale locale, Model model, HttpServletRequest request, HttpSession session) {
		User user = (User) session.getAttribute("user");

		// Vsqka tranzakciq kum Acc s tag
		TransactionManager tranManager = (TransactionManager) SpringWebConfig.context.getBean("TransactionManager");

		ArrayList<Transaction> tran = (ArrayList<Transaction>) tranManager
				.getListOfTransactionByUserID(user.getUserID());
		TreeMap<String, Double> test = new TreeMap<String, Double>();

		for (Transaction transaction : tran) {
			if (transaction.getFt_account_id() == 3 && !transaction.getIncome()) {
				Tag tag = user.getTag(transaction.getFk_tag_id());
				if (test.containsKey(tag.getName())) {
					Double value = test.get(tag.getName());
					value += (transaction.getAmount() * -1);
					test.put(tag.getName(), value);
				} else {

					test.put(tag.getName(), (transaction.getAmount() * -1));
				}

			}
		}

		ArrayList<KeyValue> graph = new ArrayList<KeyValue>();
		for (Map.Entry<String, Double> entry : test.entrySet()) {
			graph.add(new KeyValue(entry.getKey(), String.valueOf(entry.getValue()), null));

		}

		for (KeyValue key : graph) {
			System.out.println(key.getKey() + " : " + key.getValue());
		}
		model.addAttribute("list", graph);

		return "test";

	}

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public String index2(Locale locale, Model model, HttpServletRequest request, HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			model.addAttribute("model", "login.jsp");
			return "index";
		}

		// Vsqka tranzakciq kum Acc s tag
		TransactionManager tranManager = (TransactionManager) SpringWebConfig.context.getBean("TransactionManager");
		Account account = user.getAccount(3);
		ArrayList<Transaction> tran = (ArrayList<Transaction>) tranManager
				.getListOfTransactionByUserID(user.getUserID());
		TreeMap<String, Double> test = new TreeMap<String, Double>();
		ArrayList<KeyValue> graph = new ArrayList<KeyValue>();
		graph.add(new KeyValue("JANUARY", "0", null));
		graph.add(new KeyValue("FEBRUARY", "0", null));
		graph.add(new KeyValue("MARCH", "0", null));
		graph.add(new KeyValue("APRIL", "0", null));
		graph.add(new KeyValue("MAY", "0", null));
		graph.add(new KeyValue("JUNY", "0", null));
		graph.add(new KeyValue("JULY", "0", null));
		graph.add(new KeyValue("AUGUST", "0", null));
		graph.add(new KeyValue("SEPTEMBER", "0", null));
		graph.add(new KeyValue("OCTOMBER", "0", null));
		graph.add(new KeyValue("NOVEMBER", "0", null));
		graph.add(new KeyValue("DECEMBER", "0", null));
		for (Transaction transaction : tran) {
			if (transaction.getFt_account_id() == 3) {
				if (transaction.getDate().after(Date.valueOf("2016-01-01"))
						&& transaction.getDate().before(Date.valueOf("2016-01-31"))) {
					if (!transaction.getIncome()) {
						System.out.println(transaction.getAmount());
						graph.get(0).setValue(String.valueOf(
								Double.parseDouble(graph.get(0).getValue()) + (double) (transaction.getAmount() * -1)));
					} else {
						graph.get(0).setValue2(String.valueOf(
								Double.parseDouble(graph.get(0).getValue2()) + (double) transaction.getAmount()));
					}
				}
				if (transaction.getDate().after(Date.valueOf("2016-02-01"))
						&& transaction.getDate().before(Date.valueOf("2016-02-28"))) {
					if (!transaction.getIncome()) {
						System.out.println(transaction.getAmount());
						graph.get(1).setValue(String.valueOf(
								Double.parseDouble(graph.get(1).getValue()) + (double) (transaction.getAmount() * -1)));
					} else {
						graph.get(1).setValue2(String.valueOf(
								Double.parseDouble(graph.get(1).getValue2()) + (double) transaction.getAmount()));
					}
				}
				if (transaction.getDate().after(Date.valueOf("2016-03-01"))
						&& transaction.getDate().before(Date.valueOf("2016-03-31"))) {
					if (!transaction.getIncome()) {
						System.out.println(transaction.getAmount());
						graph.get(2).setValue(String.valueOf(
								Double.parseDouble(graph.get(2).getValue()) + (double) (transaction.getAmount() * -1)));
					} else {
						graph.get(2).setValue2(String.valueOf(
								Double.parseDouble(graph.get(2).getValue2()) + (double) transaction.getAmount()));
					}
				}
				if (transaction.getDate().after(Date.valueOf("2016-04-01"))
						&& transaction.getDate().before(Date.valueOf("2016-04-30"))) {
					if (!transaction.getIncome()) {
						System.out.println(transaction.getAmount());
						graph.get(3).setValue(String.valueOf(
								Double.parseDouble(graph.get(3).getValue()) + (double) (transaction.getAmount() * -1)));
					} else {
						graph.get(3).setValue2(String.valueOf(
								Double.parseDouble(graph.get(3).getValue2()) + (double) transaction.getAmount()));
					}
				}
				if (transaction.getDate().after(Date.valueOf("2016-05-01"))
						&& transaction.getDate().before(Date.valueOf("2016-05-31"))) {
					if (!transaction.getIncome()) {
						System.out.println(transaction.getAmount());
						graph.get(4).setValue(String.valueOf(
								Double.parseDouble(graph.get(4).getValue()) + (double) (transaction.getAmount() * -1)));
					} else {
						graph.get(4).setValue2(String.valueOf(
								Double.parseDouble(graph.get(4).getValue2()) + (double) transaction.getAmount()));
					}
				}
				if (transaction.getDate().after(Date.valueOf("2016-06-01"))
						&& transaction.getDate().before(Date.valueOf("2016-06-30"))) {
					if (!transaction.getIncome()) {
						System.out.println(transaction.getAmount());
						graph.get(5).setValue(String.valueOf(
								Double.parseDouble(graph.get(5).getValue()) + (double) (transaction.getAmount() * -1)));
					} else {
						graph.get(5).setValue2(String.valueOf(
								Double.parseDouble(graph.get(5).getValue2()) + (double) transaction.getAmount()));
					}
				}
				if (transaction.getDate().after(Date.valueOf("2016-07-01"))
						&& transaction.getDate().before(Date.valueOf("2016-07-31"))) {
					if (!transaction.getIncome()) {
						System.out.println(transaction.getAmount());
						graph.get(6).setValue(String.valueOf(
								Double.parseDouble(graph.get(6).getValue()) + (double) (transaction.getAmount() * -1)));
					} else {
						graph.get(6).setValue2(String.valueOf(
								Double.parseDouble(graph.get(6).getValue2()) + (double) transaction.getAmount()));
					}
				}
				if (transaction.getDate().after(Date.valueOf("2016-08-01"))
						&& transaction.getDate().before(Date.valueOf("2016-08-31"))) {
					if (!transaction.getIncome()) {
						System.out.println(transaction.getAmount());
						graph.get(7).setValue(String.valueOf(
								Double.parseDouble(graph.get(7).getValue()) + (double) (transaction.getAmount() * -1)));
					} else {
						graph.get(7).setValue2(String.valueOf(
								Double.parseDouble(graph.get(7).getValue2()) + (double) transaction.getAmount()));
					}
				}
				if (transaction.getDate().after(Date.valueOf("2016-09-01"))
						&& transaction.getDate().before(Date.valueOf("2016-09-30"))) {
					if (!transaction.getIncome()) {
						System.out.println(transaction.getAmount());
						graph.get(8).setValue(String.valueOf(
								Double.parseDouble(graph.get(8).getValue()) + (double) (transaction.getAmount() * -1)));
					} else {
						graph.get(8).setValue2(
								String.valueOf(Double.parseDouble(graph.get(8).getValue2()) + transaction.getAmount()));
					}
				}
				if (transaction.getDate().after(Date.valueOf("2016-10-01"))
						&& transaction.getDate().before(Date.valueOf("2016-10-31"))) {
					if (!transaction.getIncome()) {
						System.out.println(transaction.getAmount());
						graph.get(9).setValue(String.valueOf(
								Double.parseDouble(graph.get(9).getValue()) + (double) (transaction.getAmount() * -1)));
					} else {
						graph.get(9).setValue2(String.valueOf(
								Double.parseDouble(graph.get(9).getValue2()) + (double) transaction.getAmount()));
					}
				}
				if (transaction.getDate().after(Date.valueOf("2016-11-01"))
						&& transaction.getDate().before(Date.valueOf("2016-11-30"))) {
					if (!transaction.getIncome()) {
						System.out.println(transaction.getAmount());
						graph.get(10).setValue(String.valueOf(Double.parseDouble(graph.get(10).getValue())
								+ (double) (transaction.getAmount() * -1)));
					} else {
						graph.get(10).setValue2(String.valueOf(
								Double.parseDouble(graph.get(10).getValue2()) + (double) transaction.getAmount()));
					}
				}
				if (transaction.getDate().after(Date.valueOf("2016-12-01"))
						&& transaction.getDate().before(Date.valueOf("2016-12-31"))) {
					if (!transaction.getIncome()) {
						System.out.println(transaction.getAmount());
						graph.get(11).setValue(String.valueOf(
								Double.parseDouble(graph.get(11).getValue()) + (double) (transaction.getAmount())));
					} else {
						graph.get(11).setValue2(String.valueOf(
								Double.parseDouble(graph.get(11).getValue2()) + (double) transaction.getAmount()));
					}
				}

			}

		}

		for (KeyValue key : graph) {
			System.out.println(key.getKey() + " " + key.getValue() + " " + key.getValue2());
		}
		//Take number of transactions accounts tags
		model.addAttribute("accounts",user.getAccounts().size());
		model.addAttribute("tags",user.getTags().size());
		model.addAttribute("transactions",user.getTransactions().size());
		model.addAttribute("pagename", "Dashboard");
		model.addAttribute("model", "dashboard.jsp");
		model.addAttribute("list", graph);
		return "logged";

	}

}
