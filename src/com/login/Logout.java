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

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		LoginDao dao = new LoginDao();

		String nonce = "";
		String sessionValue = request.getSession(false).getAttribute(LoginDao.getSessionName()).toString();
		
		if(!sessionValue.isEmpty() && sessionValue.contains("="))
			nonce = sessionValue.split("=")[1];
		
		dao.deleteNonce(nonce);
		
		//dao.deleteNonce(request.getSession(false).getAttribute(LoginDao.getSessionName()).toString());

		session.removeAttribute(LoginDao.getSessionName());
		session.invalidate();
		

		
		response.sendRedirect("Login.jsp");
	}


}
