package com.login;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/Logout")
public class Logout extends HttpServlet {

	private HttpSession session;
	private LoginDao dao;
	private String nonce;
	private String sessionValue;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession();


		if(session.getAttribute(LoginDao.getSessionName())!=null)
		{
			dao = new LoginDao();
			nonce = "";
			
			sessionValue = session.getAttribute(LoginDao.getSessionName()).toString();
		
			if(!sessionValue.isEmpty() && sessionValue.contains("="))
				nonce = sessionValue.split("=")[1];
		
			dao.deleteNonce(nonce);

			session.removeAttribute(LoginDao.getSessionName());
			session.invalidate();
		}
		

		
		response.sendRedirect("Login");
	}


}
