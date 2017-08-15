package com.Homepage;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.login.LoginDao;

@WebServlet("/Navigation")
public class Navigation extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LoginDao dao = new LoginDao();
		String email = dao.getLoginEmail();
		
		if (request.getParameter(email) != null) {
            System.out.println("email clicked");
        } else if (request.getParameter("search") != null) {
        	System.out.println("search clicked");
        } else if (request.getParameter("booking") != null) {
        	System.out.println("booking clicked");
        } else {
        	System.out.println("log out clicked");
        }
		
		response.sendRedirect("Homepage.jsp");
	}

}
