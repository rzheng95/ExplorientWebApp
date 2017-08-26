package com.login;

import java.io.*;
import javax.servlet.annotation.*;
import javax.servlet.*;  
import javax.servlet.http.*;  

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
	private Cookie[] emailAndNonceCookies;
	private String sessionValue;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.getRequestDispatcher("Login.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		dao = new LoginDao();
		session = request.getSession();
		nonceGenerator = new NonceGenerator();
		
		email = request.getParameter(LoginDao.getLoginEmail());
		password = request.getParameter(LoginDao.getLoginPassword());	
		nonce = nonceGenerator.nextNonce();			
		cookieValue = email+"="+nonce;	
		emailAndNonceCookies = request.getCookies();
		
		 
		
		
		// if user tries to log himself in again
		if(session.getAttribute(LoginDao.getSessionName())!=null && email.equals(dao.getEmailCookie(emailAndNonceCookies)))
		{
			response.sendRedirect(LoginDao.HOMEPAGE);
			return;		
		}
		// maximum length allowed
		if(dao.checkMaxLength(email) || dao.checkMaxLength(password))
		{
			request.setAttribute(LoginDao.LOGIN_EMAIL, "");
			request.setAttribute(LoginDao.LOGIN_FAILED, LoginDao.getLoginMaxLengthFailed());
			request.getRequestDispatcher(LoginDao.LOGIN).forward(request, response);
		}
		// check if email and password are correct
		else if(dao.checkEmailAndPassword(email, password))
		{
			
			// if user is logged in in one browser and tries to log in again with different browser or computer, change the nonce in database and cookie
			if(dao.checkEmailInNonceTable(email) && session.getAttribute(LoginDao.getSessionName())==null)
			{
				// check if such nonce is already in the database in case of getting the same nonce(very small chance)
				while(dao.checkNonce(nonce))
				{
					nonce = nonceGenerator.nextNonce();
					cookieValue = email+"="+nonce;	
				}
				dao.updateNonceByEmail(nonce, email);
				
				
				session.setAttribute(LoginDao.getSessionName(), cookieValue);
				emailCookie = new Cookie(LoginDao.getLoginCookieName(), cookieValue); 
			
				emailCookie.setMaxAge(LoginDao.getMaxLoginCookieAge());
				response.addCookie(emailCookie);
				session.setMaxInactiveInterval(LoginDao.getMaxInactiveInterval());
				response.sendRedirect(LoginDao.HOMEPAGE);
				return;
			}
						
				
			
			// check if user is currently logged in but tries to login with different valid account
			if(session.getAttribute(LoginDao.getSessionName())!=null && !dao.checkEmailInNonceTable(email))
			{
				String tempNonce = "";
				sessionValue = session.getAttribute(LoginDao.getSessionName()).toString();
				
				if(!sessionValue.isEmpty() && sessionValue.contains("="))
					tempNonce = sessionValue.split("=")[1];
				
				dao.deleteNonce(tempNonce);
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
				// if user A is logged in with account A in browser A
				// if user B is logged in with account B in browser B
				// if user B press back button and change browser B's cookie to the cookie stored in browser A, then tries to log in with account A on browser B
				// send him back to homepage which will lose his connection due to invalid cookie (session will be removed in homepage)	
				if(session.getAttribute(LoginDao.getSessionName())!=null)
				{
					response.sendRedirect(LoginDao.HOMEPAGE);
					return;
				}
				cookieValue = email +"="+ dao.getNonceByEmail(email);
				emailCookie = new Cookie(LoginDao.getLoginCookieName(), cookieValue); 
				emailCookie.setMaxAge(LoginDao.getMaxLoginCookieAge());
				response.addCookie(emailCookie);
				
				session.setAttribute(LoginDao.getSessionName(), cookieValue); 
			}
			else
			{
				// check if such nonce is already in the database in case of getting the same nonce(very small chance)
				while(dao.checkNonce(nonce))
				{
					nonce = nonceGenerator.nextNonce();
					cookieValue = email+"="+nonce;	
				}
				
				// cookie and session values are the same 'email=nonce'
				emailCookie = new Cookie(LoginDao.getLoginCookieName(), cookieValue); 
				
				emailCookie.setMaxAge(LoginDao.getMaxLoginCookieAge());
				response.addCookie(emailCookie);
				
				
				session.setAttribute(LoginDao.getSessionName(), cookieValue); 
				

							
				dao.saveNonce(email, nonce);			
				
			}
			
			session.setMaxInactiveInterval(LoginDao.getMaxInactiveInterval());
			response.sendRedirect(LoginDao.HOMEPAGE);

		}
		else
		{		
			request.setAttribute(LoginDao.LOGIN_EMAIL, email);
			request.setAttribute(LoginDao.LOGIN_FAILED, LoginDao.getLoginFailed());
			request.getRequestDispatcher(LoginDao.LOGIN).forward(request, response);
		}
				
	}

}
