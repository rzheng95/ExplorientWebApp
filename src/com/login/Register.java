/*
 * This file is part of Explorient Web App
 * Copyright (C) 2016-2019 Richard R. Zheng
 *
 * https://github.com/rzheng95/ExplorientWebApp
 * 
 * All Right Reserved.
 */

package com.login;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/Register")
public class Register extends HttpServlet {

	private HttpSession session;
	private LoginDao dao;
	private String email;
	private String salt;
	private String password;
	private String confirmPassword;
	private String firstname;
	private String lastname;
	private String cookieValue;
	private String nonce;
	private Cookie emailCookie;
	private NonceGenerator nonceGenerator;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.getRequestDispatcher("Register.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession();
		
		// check if user is already logged in
		if(session.getAttribute(LoginDao.getSessionName())!=null)
		{
			response.sendRedirect(LoginDao.HOMEPAGE);
			return;
		}
		

		dao = new LoginDao();
		nonceGenerator = new NonceGenerator();		
		email = request.getParameter(LoginDao.getLoginEmail());
		password = request.getParameter(LoginDao.getLoginPassword());
		confirmPassword = request.getParameter(LoginDao.getRegisterConfirmPassword());
		firstname = request.getParameter(LoginDao.getRegisterFirstname());
		lastname = request.getParameter(LoginDao.getRegisterLastname());
		
		request.setAttribute(LoginDao.REGISTER, email+"="+firstname+"="+lastname);
		

		// maximum length allowed
		if(dao.checkMaxLength(email) || dao.checkMaxLength(password) || dao.checkMaxLength(confirmPassword) || dao.checkMaxLength(firstname) || dao.checkMaxLength(lastname))
		{
			request.setAttribute(LoginDao.REGISTER, "==");
			request.setAttribute(LoginDao.REGISTER_FAILED, LoginDao.getRegisterMaxLengthFailed());
		}
		// Emailchecker already checks the symbol for email. Password does not matter because it will never be shown
		else if(dao.checkInvalidSymbols(firstname) || dao.checkInvalidSymbols(lastname))
		{
			request.setAttribute(LoginDao.REGISTER, "==");
			request.setAttribute(LoginDao.REGISTER_FAILED, LoginDao.getRegisterInvalidSymbolsLFailed());
		}
		// check if any textfields are empty
		else if(!checkEmpty()) 
		{		
			request.setAttribute(LoginDao.REGISTER_FAILED, LoginDao.getRegisterEmptyFieldMessage());
		}
		// briefly check if email is vaild 
		else if( !EmailChecker.validate(email) )
		{
			request.setAttribute(LoginDao.REGISTER_FAILED, LoginDao.getRegisterInvalidEmailMessage());
		}
		// check if both passwords are identical
		else if(!password.equals(confirmPassword))
		{
			request.setAttribute(LoginDao.REGISTER_FAILED, LoginDao.getRegisterUnmatchedPasswordMessage());
		}
		// check if email already exists
		else if(dao.checkEmail(email.trim())) 
		{
			request.setAttribute(LoginDao.REGISTER_FAILED, LoginDao.getRegisterEmailExistMessage());
		}
		else
		{
			salt = nonceGenerator.nextNonce();
			dao.addUser(email, salt, confirmPassword, firstname, lastname);
			
			nonce = nonceGenerator.nextNonce();	
			
			cookieValue = email+"="+nonce;
			
			emailCookie = new Cookie(LoginDao.getLoginCookieName(), cookieValue); 
			emailCookie.setMaxAge(LoginDao.getMaxLoginCookieAge());
			response.addCookie(emailCookie);
			
			
			session.setAttribute(LoginDao.getSessionName(), cookieValue); 
			dao.saveNonce(email, nonce);
			
			response.sendRedirect(LoginDao.HOMEPAGE);
			return;
		}
			

		request.getRequestDispatcher("Register.jsp").forward(request, response);
	}
	
	public boolean checkEmpty()
	{
		// return false if one the fields is empty
		return (!email.trim().equals("") && !password.trim().equals("") && !confirmPassword.trim().equals("") && !firstname.trim().equals("") && !lastname.trim().equals(""));
	}

}









