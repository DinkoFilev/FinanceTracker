package com.budgetbeat.controller;

import javax.servlet.http.HttpSession;

import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.budgetbeat.manager.AccountManager;
import com.budgetbeat.manager.TransactionManager;
import com.budgetbeat.pojo.Account;

import com.budgetbeat.pojo.User;

@Controller
public class AccountController {

	@Autowired
	AccountManager accountManager;// will inject dao from xml file

	@Autowired
	TransactionManager transactionManager;

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
	public String saveAccount(@ModelAttribute("account") Account account, HttpSession session, Model model) {
		User user = ((User) session.getAttribute("user"));
		if (user == null) {
			model.addAttribute("model", "login.jsp");
			return "redirect:/index";
		} // End

		if (account.getName().length() < 2 || account.getName().length() > 44) {
			model.addAttribute("model", "accountform.jsp");
			model.addAttribute("command", account);
			model.addAttribute("error", "Account name lenght error!!!");
			return "logged";
		}

		if (account.getInstitution().length() < 2 || account.getInstitution().length() > 49) {
			model.addAttribute("model", "accountform.jsp");
			model.addAttribute("command", account);
			model.addAttribute("error", "Account institution name lenght error!!!");
			return "logged";
		}

		for (Account accountElement : user.getAccounts().values()) {
			if (accountElement.getName().toLowerCase().equals(account.getName().toLowerCase())) {
				model.addAttribute("title", "Account managet");
				model.addAttribute("model", "accountform.jsp");
				model.addAttribute("command", account);
				model.addAttribute("error", "Account " + account.getName() + " exist!");
				return "logged";
			}
		}

		account.setName(Jsoup.parse(account.getName()).text());
		account.setInstitution(Jsoup.parse(account.getInstitution()).text());

		account.setFk_userId(user.getUserID());
		user.addAccount(accountManager.create(account));
		model.addAttribute("model", "viewaccount.jsp");
		return "logged";
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
	public String editSaveAccount(@ModelAttribute("account") Account account, HttpSession session, Model model) {
		User user = ((User) session.getAttribute("user"));

		// Check user
		if (user == null) {
			model.addAttribute("model", "login.jsp");
			return "redirect:/index";
		} // End
		
		
		if (account.getName().length() < 2 || account.getName().length() > 44) {
			model.addAttribute("model", "accounteditform.jsp");
			model.addAttribute("command", account);
			model.addAttribute("error", "Account name lenght error!!!");
			return "logged";
		}

		if (account.getInstitution().length() < 2 || account.getInstitution().length() > 49) {
			model.addAttribute("model", "accounteditform.jsp");
			model.addAttribute("command", account);
			model.addAttribute("error", "Account institution name lenght error!!!");
			return "logged";
		}
		

		if (account.getAccountId() == user.getAccounts().lastKey()) {
			model.addAttribute("title", "Accounts");
			model.addAttribute("model", "accounteditform.jsp");
			model.addAttribute("command", account);
			model.addAttribute("error", "You can not edit default account!!!");
			return "logged";
		}

		for (Account accountElement : user.getAccounts().values()) {

			if (accountElement.getName().toLowerCase().equals(account.getName().toLowerCase())
					&& accountElement.getAccountId() != account.getAccountId()) {
				model.addAttribute("title", "Accounts");
				model.addAttribute("model", "accounteditform.jsp");
				model.addAttribute("command", account);
				model.addAttribute("error", "Account " + account.getName() + " exist!");
				return "logged";
			}
		}

		accountManager.update(account.getAccountId(), account.getName(), account.getBalance(), account.getInstitution(),
				account.getStatus());

		user.getAccounts().put(account.getAccountId(), account);
		model.addAttribute("model", "viewaccount.jsp");
		return "logged";
	}

	/* It deletes record for the given id in URL and redirects to /viewemp */
	@RequestMapping(value = "/deleteaccount", method = RequestMethod.POST)
	public String deleteAccountPost(@ModelAttribute("action") String action,
			@ModelAttribute("accountId") Integer accountId, HttpSession session, Model model) {
		User user = ((User) session.getAttribute("user"));
		// Check user
		if (user == null) {
			model.addAttribute("model", "login.jsp");
			return "redirect:/index";
		} // End

		if (accountId == user.getAccounts().lastKey()) {
			model.addAttribute("error", "You can not delete default account!!!");
			model.addAttribute("model", "viewaccount.jsp");
			return "logged";
		}

		if (action.equals("delete")) {
			try {
				accountManager.delete(user, accountId, user.getAccounts().lastKey());
			} catch (DataAccessException e) {
				model.addAttribute("error", "Account was not deleted!!!");
				e.printStackTrace();
			}
		}
		model.addAttribute("model", "viewaccount.jsp");
		return "logged";
	}
}