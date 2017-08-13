package com.login;

import java.io.*;
import javax.servlet.annotation.*;
import javax.servlet.*;  
import javax.servlet.http.*;  
import javax.websocket.Session;


@WebServlet("/Login")
public class Login extends HttpServlet {
	private LoginDao dao;
	private HttpSession session;
	private NonceGenerator nonceGenerator;
	private String email;
	private String password;
	private String cookieValue;
	private String nonce;
	private Cookie emailCookie;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		dao = new LoginDao();
		session = request.getSession();
		nonceGenerator = new NonceGenerator();
		
		email = request.getParameter(LoginDao.getEmail());
		password = request.getParameter(LoginDao.getPassword());
		
		nonce = nonceGenerator.nextNonce();	
		
		cookieValue = email+"="+nonce;
		
		
		Cookie[] emailAndNonceCookies = request.getCookies();
		
		
		// check if user is already logged in
		if(session.getAttribute(LoginDao.getSessionName())!=null && email.equals(dao.getEmailCookie(emailAndNonceCookies)))
		{
			response.sendRedirect("Homepage.jsp");
			return;		
		}	
		if(dao.checkMaxLength(email) || dao.checkMaxLength(password))
		{
			session.setAttribute(LoginDao.LOGIN_FAILED, LoginDao.getLoginMaxLengthFailed());
			response.sendRedirect("Login.jsp");
		}
		else if(dao.checkEmailAndPassword(email, password))
		{
			if(session.getAttribute(LoginDao.getSessionName())!=null)
			{
				dao.deleteNonce(dao.getNonceCookie(emailAndNonceCookies));		
				session.removeAttribute(LoginDao.getSessionName());
			}
			// cookies for email
			// add cookie before redirect to other jsp page
			emailCookie = new Cookie(LoginDao.getLoginCookieName(), cookieValue); 
			emailCookie.setMaxAge(60*60*24*365);
			response.addCookie(emailCookie);
			
			
			session.setAttribute(LoginDao.getSessionName(), cookieValue); 
			dao.saveNonce(nonce);
			
			session.removeAttribute(LoginDao.LOGIN_FAILED);
			response.sendRedirect("Homepage.jsp");

		}
		else
		{		
			session.setAttribute(LoginDao.LOGIN_FAILED, LoginDao.getLoginFailed());
			response.sendRedirect("Login.jsp");
		}
				
	}

}
