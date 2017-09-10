package com.itinerary;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Itinerary")
public class Itinerary extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("Itinerary.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		String[] activities = request.getParameterValues("activities");
		
		
		if(activities != null)
			for(String a : activities)
			System.out.println(a);
		

		
		// check if customer id exists
		
		
		
		// check if there is any passenger added to this booking
		
		
		
		
		// generate 
		
		
		
		
	}

}
