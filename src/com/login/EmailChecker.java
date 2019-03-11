/*
 * This file is part of Explorient Web App
 * Copyright (C) 2016-2019 Richard R. Zheng
 *
 * https://github.com/rzheng95/ExplorientWebApp
 * 
 * All Right Reserved.
 */

package com.login;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailChecker {
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = 
	    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

		public static boolean validate(String emailStr) {
		        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
		        return matcher.find();
		}
		
		public static void main(String[]args)
		{
			System.out.println(validate("asd.asd@"));
		}
}
