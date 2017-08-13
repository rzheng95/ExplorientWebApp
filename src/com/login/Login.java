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
	private SessionIdentifierGenerator sessionIDGenerator;
	private String email;
	private String password;
	private String cookieValue;
	private String randomSessionID;
	private Cookie emailCookie;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		dao = new LoginDao();
		session = request.getSession();
		sessionIDGenerator = new SessionIdentifierGenerator();
		
		email = request.getParameter(LoginDao.getEmail());
		password = request.getParameter(LoginDao.getPassword());
		
		randomSessionID = sessionIDGenerator.nextSessionId();	
		
		cookieValue = email+"="+randomSessionID;
		

		
		
		if(dao.checkEmailAndPassword(email, password))
		{
			// cookies for email
			// add cookie before redirect to other jsp page
			emailCookie = new Cookie(LoginDao.getLoginCookieName(), cookieValue); 
			emailCookie.setMaxAge(60*60*24*365);
			response.addCookie(emailCookie);
			
			
			session.setAttribute(LoginDao.getSessionID(), randomSessionID); 
			dao.saveSessionId(randomSessionID);
			
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
