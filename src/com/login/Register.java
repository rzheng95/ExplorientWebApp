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

	private Cookie registerCookie;
	
	private String email;
	private String password;
	private String confirmPassword;
	private String firstname;
	private String lastname;
	private String cookieValue;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		email = request.getParameter(LoginDao.getEmail());
		password = request.getParameter(LoginDao.getPassword());
		confirmPassword = request.getParameter("Confirm_"+LoginDao.getPassword());
		firstname = request.getParameter(LoginDao.getFirstname());
		lastname = request.getParameter(LoginDao.getLastname());
		cookieValue = email+"="+firstname+"="+lastname;
		

		// check if any textfields are empty
		if(!checkEmpty()) 
		{
			registerCookie = new Cookie(LoginDao.getRegisterCookieName(), cookieValue); 
			registerCookie.setMaxAge(15);
			response.addCookie(registerCookie);
			
			session.setAttribute(LoginDao.REGISTERFAILED, LoginDao.getRegisterEmptyFieldMessage());
			response.sendRedirect("Register.jsp");
			return;
		}
		
		// check if email already exists
		
		
		// check if both passwords are identical
		
		
		
		//response.sendRedirect("Login.jsp");
	}
	
	public boolean checkEmpty()
	{
		// return false if one the fields is empty
		return (!email.trim().equals("") && !password.trim().equals("") && !confirmPassword.trim().equals("") && !firstname.trim().equals("") && !lastname.trim().equals(""));
	}

}









