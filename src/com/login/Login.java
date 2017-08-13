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
		
		
		// if user tries to log himself in again
		if(session.getAttribute(LoginDao.getSessionName())!=null && email.equals(dao.getEmailCookie(emailAndNonceCookies)))
		{
			response.sendRedirect("Homepage.jsp");
			return;		
		}
		// no length more than 254 due to the character limit in database is varchar(255)
		if(dao.checkMaxLength(email) || dao.checkMaxLength(password))
		{
			session.setAttribute(LoginDao.LOGIN_FAILED, LoginDao.getLoginMaxLengthFailed());
			response.sendRedirect("Login.jsp");
		}
		// check if email and password are correct
		else if(dao.checkEmailAndPassword(email, password))
		{
			// check if user is currently logged in but tries to login with different valid account
			if(session.getAttribute(LoginDao.getSessionName())!=null)
			{
				String nonce = "";
				String sessionValue = request.getSession(false).getAttribute(LoginDao.getSessionName()).toString();
				
				if(!sessionValue.isEmpty() && sessionValue.contains("="))
					nonce = sessionValue.split("=")[1];
				
				dao.deleteNonce(nonce);
				session.removeAttribute(LoginDao.getSessionName());
			}
			
			// cookie and session values are the same 'email=nonce'
			emailCookie = new Cookie(LoginDao.getLoginCookieName(), cookieValue); 
			emailCookie.setMaxAge(60*60*24*365);
			response.addCookie(emailCookie);
			
			
			session.setAttribute(LoginDao.getSessionName(), cookieValue); 
			dao.saveNonce(nonce);
			
			//session.setMaxInactiveInterval(5);
			
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
