package com.budgetbeat.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

//@Component
public class Config  {
	
	static{
		System.out.println("SEGA ME PUSNA");
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    System.setProperty("current.date", dateFormat.format(new Date()));
	}

}
