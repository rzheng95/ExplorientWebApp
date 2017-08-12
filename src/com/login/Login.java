package com.login;

import java.io.*;
import javax.servlet.annotation.*;
import javax.servlet.*;  
import javax.servlet.http.*;  
import javax.websocket.Session;


@WebServlet("/Login")
public class Login extends HttpServlet {

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		LoginDao dao = new LoginDao();
		HttpSession session = request.getSession();
		SessionIdentifierGenerator randomSessionID = new SessionIdentifierGenerator();
		
		String email = request.getParameter(LoginDao.getEmail());
		String password = request.getParameter(LoginDao.getPassword());
		
		String cookieValue = email+"="+randomSessionID.nextSessionId();	
		

		
		
		if(dao.check(email, password))
		{
			// cookies for email
			// add cookie before redirect to other jsp page
			Cookie emailCookie = new Cookie(LoginDao.getLoginCookieName(), cookieValue); 
			emailCookie.setMaxAge(60*60*24*365);
			response.addCookie(emailCookie);
			
			
			session.setAttribute(LoginDao.getEmail(), email);
			session.removeAttribute(LoginDao.LOGINFAILED);
			response.sendRedirect("Homepage.jsp");

		}
		else
		{		
			session.setAttribute(LoginDao.LOGINFAILED, LoginDao.getLoginFailed());
			response.sendRedirect("Login.jsp");
		}
				
	}

}
