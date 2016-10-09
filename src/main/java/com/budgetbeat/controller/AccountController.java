package com.budgetbeat.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.budgetbeat.manager.AccountManager;
import com.budgetbeat.pojo.Account;
import com.budgetbeat.pojo.User;

@Controller
public class AccountController {

	@Autowired
	AccountManager accountManager;// will inject dao from xml file

	/*
	 * It displays a form to input data, here "command" is a reserved request
	 * attribute which is used to display object data into form
	 */
	@RequestMapping("/accountform")
	public ModelAndView showAccountForm() {
		return new ModelAndView("accountform", "command", new Account());
	}

	/*
	 * It saves object into database. The @ModelAttribute puts request data into
	 * model object. You need to mention RequestMethod.POST method because
	 * default request is GET
	 */
	@RequestMapping(value = "/saveaccount", method = RequestMethod.POST)
	public ModelAndView saveAccount(@ModelAttribute("account") Account account, HttpSession session) {

		Integer userId = ((User) session.getAttribute("user")).getUserID();
		accountManager.create(userId, account.getName(), account.getBalance(), account.getInstitution(), account.getStatus());
		return new ModelAndView("redirect:/viewaccount");// will redirect to viewemp
														// request mapping
	}

	/* It provides list of employees in model object */
	@RequestMapping("/viewaccount")
	public ModelAndView viewAccounts(HttpSession session) {
		Integer userId = ((User) session.getAttribute("user")).getUserID();
		List<Account> list = accountManager.listAccounts(userId);
		return new ModelAndView("viewaccount", "list", list);
	}

	/*
	 * It displays object data into form for the given id. The @PathVariable
	 * puts URL data into variable.
	 */
	@RequestMapping(value = "/editaccount/{id}")
	public ModelAndView editAccount(@PathVariable int id) {
		Account account = accountManager.getAccount(id);
		return new ModelAndView("accounteditform", "command", account);
	}

	/* It updates model object. */
	@RequestMapping(value = "/editsaveaccount", method = RequestMethod.POST)
	public ModelAndView editSaveAccount(@ModelAttribute("account") Account account) {
		accountManager.update(account.getAccountId(), account.getName(), account.getBalance(), account.getInstitution(), account.getStatus());
		return new ModelAndView("redirect:/viewaccount");
	}

	/* It deletes record for the given id in URL and redirects to /viewemp */
	@RequestMapping(value = "/deleteaccount/{id}", method = RequestMethod.GET)
	public ModelAndView deleteAccount(@PathVariable int id) {
		accountManager.delete(id);
		return new ModelAndView("redirect:/viewaccount");
	}

}