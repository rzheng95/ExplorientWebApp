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
	private String password;
	private String confirmPassword;
	private String firstname;
	private String lastname;
	private String cookieValue;
	private String nonce;
	private Cookie emailCookie;
	private NonceGenerator nonceGenerator;


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession();
		
		// check if user is already logged in
		if(session.getAttribute(LoginDao.getSessionName())!=null)
		{
			response.sendRedirect("Homepage.jsp");
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
		// check if any textfields are empty
		else if(!checkEmpty()) 
		{		
			request.setAttribute(LoginDao.REGISTER_FAILED, LoginDao.getRegisterEmptyFieldMessage());
		}
		// briefly check if email is vaild 
		else if( !email.contains("@") || !email.contains(".") || (email.length() > 4 && email.substring(0, 4).equals("www.")) )
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
			dao.addUser(email, confirmPassword, firstname, lastname);
			
			nonce = nonceGenerator.nextNonce();	
			
			cookieValue = email+"="+nonce;
			
			emailCookie = new Cookie(LoginDao.getLoginCookieName(), cookieValue); 
			emailCookie.setMaxAge(60*60*24*365);
			response.addCookie(emailCookie);
			
			
			session.setAttribute(LoginDao.getSessionName(), cookieValue); 
			dao.saveNonce(nonce);
			
			response.sendRedirect("Homepage.jsp");
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









