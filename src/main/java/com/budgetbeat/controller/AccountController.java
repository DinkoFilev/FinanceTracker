package com.budgetbeat.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.budgetbeat.manager.AccountManager;
import com.budgetbeat.pojo.Account;
import com.budgetbeat.pojo.Tag;
import com.budgetbeat.pojo.User;
import com.sun.scenario.effect.Blend.Mode;

@Controller
public class AccountController {

	@Autowired
	AccountManager accountManager;// will inject dao from xml file

	/*
	 * It displays a form to input data, here "command" is a reserved request
	 * attribute which is used to display object data into form
	 */
	@RequestMapping("/accountform")
	public String showAccountForm(Model model, HttpSession session) {
		User user = ((User) session.getAttribute("user"));
		if (user == null) {
			model.addAttribute("model", "login.jsp");
			return "index";
		} // End

		model.addAttribute("command", new Account());
		model.addAttribute("model", "accountform.jsp");
		return "logged";
	}

	/*
	 * It saves object into database. The @ModelAttribute puts request data into
	 * model object. You need to mention RequestMethod.POST method because
	 * default request is GET
	 */
	@RequestMapping(value = "/saveaccount", method = RequestMethod.POST)
	public ModelAndView saveAccount(@ModelAttribute("account") Account account, HttpSession session, Model model) {
		User user = ((User) session.getAttribute("user"));
		if (user == null) {
			model.addAttribute("model", "login.jsp");
			return new ModelAndView("redirect:/index");
		} // End
		
		account.setFk_userId(user.getUserID());
		user.addAccount(accountManager.create(account));

		return new ModelAndView("redirect:/viewaccount");// will redirect to
															// viewemp
															// request mapping
	}

	/* It provides list of employees in model object */
	@RequestMapping("/viewaccount")
	public String viewAccounts(HttpSession session, Model model) {
		User user = ((User) session.getAttribute("user"));
		// Check user
		if (user == null) {
			model.addAttribute("model", "login.jsp");
			return "index";
		} // End

		model.addAttribute("model", "viewaccount.jsp");
		return "logged";

	}

	@RequestMapping(value = "/editaccount", method = RequestMethod.POST)
	public String editAccountPost(@ModelAttribute("action") String action,
			@ModelAttribute("accountId") Integer accountId, HttpSession session, Model model) {
		User user = ((User) session.getAttribute("user"));
		// Check user
		if (user == null) {
			model.addAttribute("model", "login.jsp");
			return "index";
		} // End

		if (action.equals("edit")) {
			model.addAttribute("command", user.getAccount(accountId));
			model.addAttribute("model", "accounteditform.jsp");
			return "logged";
		}
		model.addAttribute("model", "viewaccount.jsp");
		return "redirect:/logged";
	}

	/* It updates model object. */
	@RequestMapping(value = "/editsaveaccount", method = RequestMethod.POST)
	public ModelAndView editSaveAccount(@ModelAttribute("account") Account account, HttpSession session, Model model) {
		User user = ((User) session.getAttribute("user"));
		// Check user
		if (user == null) {
			model.addAttribute("model", "login.jsp");
			return new ModelAndView("redirect:/index");
		} // End

		accountManager.update(account.getAccountId(), account.getName(), account.getBalance(), account.getInstitution(),
				account.getStatus());

		user.getAccounts().put(account.getAccountId(), account);
		return new ModelAndView("redirect:/viewaccount");
	}

	/* It deletes record for the given id in URL and redirects to /viewemp */
	@RequestMapping(value = "/deleteaccount", method = RequestMethod.POST)
	public ModelAndView deleteAccountPost(@ModelAttribute("action") String action,
			@ModelAttribute("accountId") Integer accountId, HttpSession session, Model model) {
		User user = ((User) session.getAttribute("user"));
		// Check user
		if (user == null) {
			model.addAttribute("model", "login.jsp");
			return new ModelAndView("redirect:/index");
		} // End

		if (action.equals("delete")) {
			
			System.err.println("Delete " + accountId);
			accountManager.delete(accountId);
			user.getAccounts().remove(accountId);
		}

		return new ModelAndView("redirect:/viewaccount");
	}
}