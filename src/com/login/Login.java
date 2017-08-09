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
		
		String email = request.getParameter(LoginDao.getEmail());
		String password = request.getParameter(LoginDao.getPassword());
		
		
		if(dao.check(email, password))
		{
			// cookies for email
			// add cookie before redirect to other jsp page
			Cookie emailCookie = new Cookie("Explorient.email", email); 
			emailCookie.setMaxAge(60*60*24*365);
			response.addCookie(emailCookie);
			
			
			session.setAttribute(LoginDao.getEmail(), email);
			response.sendRedirect("Homepage.jsp");

		}
		else
		{		
			session.setAttribute(LoginDao.FAILED, LoginDao.getFailed());
			response.sendRedirect("Login.jsp");
		}
				
	}

}
