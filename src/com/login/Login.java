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
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.getRequestDispatcher("Login.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		dao = new LoginDao();
		session = request.getSession(false);
		nonceGenerator = new NonceGenerator();
		
		email = request.getParameter(LoginDao.getLoginEmail());
		password = request.getParameter(LoginDao.getLoginPassword());
		
		nonce = nonceGenerator.nextNonce();	
		
		cookieValue = email+"="+nonce;
		
		
		Cookie[] emailAndNonceCookies = request.getCookies();
		
		
		// if user tries to log himself in again
		if(session.getAttribute(LoginDao.getSessionName())!=null && email.equals(dao.getEmailCookie(emailAndNonceCookies)))
		{
			response.sendRedirect("Homepage.jsp");
			return;		
		}
		// maximum length allowed
		if(dao.checkMaxLength(email) || dao.checkMaxLength(password))
		{
			request.setAttribute(LoginDao.LOGIN_EMAIL, "");
			request.setAttribute(LoginDao.LOGIN_FAILED, LoginDao.getLoginMaxLengthFailed());
			request.getRequestDispatcher("Login.jsp").forward(request, response);
		}
		// check if email and password are correct
		else if(dao.checkEmailAndPassword(email, password))
		{
			// check if user is currently logged in but tries to login with different valid account
			if(session.getAttribute(LoginDao.getSessionName())!=null)
			{
				String nonce = "";
				String sessionValue = session.getAttribute(LoginDao.getSessionName()).toString();
				
				if(!sessionValue.isEmpty() && sessionValue.contains("="))
					nonce = sessionValue.split("=")[1];
				
				dao.deleteNonce(nonce);
				session.removeAttribute(LoginDao.getSessionName());
			}
			
			
			
			// if user lost connection to session due to max timeout or auto logout from the maxInactiveInteval, then no need to create a new nonce		
			if(dao.checkEmailAndNonce(dao.getEmailCookie(emailAndNonceCookies), dao.getNonceCookie(emailAndNonceCookies)) && email.equals(dao.getEmailCookie(emailAndNonceCookies)))
			{
				session.setAttribute(LoginDao.getSessionName(), email+"="+dao.getNonceCookie(emailAndNonceCookies)); 
			}
			// check if user's session expired and user modified the cookie or clear the cookie, then check if database has such email. if it does, then add cookie on user's browser 
			else if(dao.checkEmailInNonceTable(email))
			{
				cookieValue = email +"="+ dao.getNonceByEmail(email);
				emailCookie = new Cookie(LoginDao.getLoginCookieName(), cookieValue); 
				emailCookie.setMaxAge(60*60*24*365);
				response.addCookie(emailCookie);
				
				session.setAttribute(LoginDao.getSessionName(), cookieValue); 
			}
			else
			{
		
				// cookie and session values are the same 'email=nonce'
				emailCookie = new Cookie(LoginDao.getLoginCookieName(), cookieValue); 
				emailCookie.setMaxAge(60*60*24*365);
				response.addCookie(emailCookie);
				
				
				session.setAttribute(LoginDao.getSessionName(), cookieValue); 
				
				// check if such nonce is already in the database in case of getting the same nonce(very small chance)
				while(dao.checkNonce(nonce))
				{
					nonce = nonceGenerator.nextNonce();
				}
							
				dao.saveNonce(email, nonce);			
				
			}
			
			session.setMaxInactiveInterval(60*20);
			response.sendRedirect("Homepage.jsp");

		}
		else
		{		
			request.setAttribute(LoginDao.LOGIN_EMAIL, email);
			request.setAttribute(LoginDao.LOGIN_FAILED, LoginDao.getLoginFailed());
			request.getRequestDispatcher("Login.jsp").forward(request, response);
		}
				
	}

}
