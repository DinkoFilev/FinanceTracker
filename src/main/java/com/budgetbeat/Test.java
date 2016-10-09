package com.budgetbeat;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.budgetbeat.manager.AccountManager;
import com.budgetbeat.manager.TagManager;
import com.budgetbeat.pojo.Account;


public class Test {

	public static void main(String[] args) {
	
		ApplicationContext context = new FileSystemXmlApplicationContext(
				"src\\main\\webapp\\WEB-INF\\spring\\beans.xml");

		//AccountManager accountManager = (AccountManager) context.getBean("AccountManager");
//		accountManager.create(4, "Чекова", 100.55, "Western union", true);
//		accountManager.create(4, "Лихва", 300.55, "Western union", true);
//		accountManager.create(4, "Друга", 322.55, "Western union", true);
		//accountManager.update(1, "Спестовна", 1000.0, "Western union", false);
		Integer asd = 17;
		TagManager tagMan = (TagManager) context.getBean("TagManager");
		tagMan.getTag(asd);
		
	//List<Account> accounts = 	accountManager.listAccounts(4);
	System.out.println("===========================================");
	System.out.println(tagMan.getTag(17).getName());
	}

}
