package com.budgetbeat.util;

import java.util.Calendar;
import java.util.Date;

public class Utils {
	public static Date addMonth(Date date, int montsToAdd) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, montsToAdd);
		return cal.getTime();
	}
}
