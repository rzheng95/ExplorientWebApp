package com.homepage;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/New")
public class New extends HttpServlet {

	private String agent;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("New.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		agent = (String) request.getParameter("editable-select");
		System.out.println("Agent is: "+ agent);
		
		String customer = (String) request.getParameter("Customer id");
		System.out.println("custerm id is: "+ customer);
	}

}
