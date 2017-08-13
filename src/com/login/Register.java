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
	private HttpSession session;
	private LoginDao dao;
	private String email;
	private String password;
	private String confirmPassword;
	private String firstname;
	private String lastname;
	private String cookieValue;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession();
		dao = new LoginDao();
		
		email = request.getParameter(LoginDao.getEmail());
		password = request.getParameter(LoginDao.getPassword());
		confirmPassword = request.getParameter("Confirm_"+LoginDao.getPassword());
		firstname = request.getParameter(LoginDao.getFirstname());
		lastname = request.getParameter(LoginDao.getLastname());
		cookieValue = email+"="+firstname+"="+lastname;
		

		// check if any textfields are empty
		if(!checkEmpty()) 
		{		
			session.setAttribute(LoginDao.REGISTER_FAILED, LoginDao.getRegisterEmptyFieldMessage());			
		}
		// briefly check if email is vaild 
		else if(!email.contains("@") && !email.contains("."))
		{
			session.setAttribute(LoginDao.REGISTER_FAILED, LoginDao.getRegisterInvalidEmailMessage());
		}
		// check if email is vaild 
		else if(email.length() > 4 && email.substring(0, 4).equals("www."))
		{
			session.setAttribute(LoginDao.REGISTER_FAILED, LoginDao.getRegisterInvalidEmailMessage());
		}
		// check if both passwords are identical
		else if(!password.equals(confirmPassword))
		{
			session.setAttribute(LoginDao.REGISTER_FAILED, LoginDao.getRegisterUnmatchedPasswordMessage());
		}
		// check if email already exists
		else if(dao.checkEmail(email.trim())) 
		{
			session.setAttribute(LoginDao.REGISTER_FAILED, LoginDao.getRegisterEmailExistMessage());
		}
		else
		{

			dao.addUser(email, confirmPassword, firstname, lastname);

			response.sendRedirect("Login.jsp");
			return;
		}
		
		
		
		registerCookie = new Cookie(LoginDao.getRegisterCookieName(), cookieValue); 
		registerCookie.setMaxAge(15);
		response.addCookie(registerCookie);
		response.sendRedirect("Register.jsp");
	}
	
	public boolean checkEmpty()
	{
		// return false if one the fields is empty
		return (!email.trim().equals("") && !password.trim().equals("") && !confirmPassword.trim().equals("") && !firstname.trim().equals("") && !lastname.trim().equals(""));
	}

}









