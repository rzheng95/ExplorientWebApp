package com.newpage;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.login.LoginDao;
import com.passenger.PassengerDao;


@WebServlet("/BookingCreated")
public class BookingCreated extends HttpServlet {

	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("BookingCreated.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
		String addPassenger = request.getParameter(NewpageDao.getAddPassenger());
		String addShippingAddress = request.getParameter(NewpageDao.getAddShippingAddress());
		String updateBooking = request.getParameter(NewpageDao.getUpdateBooking());
		
		
		if (addPassenger != null) 
		{
			response.sendRedirect(PassengerDao.PASSENGER);
		}
		else if (addShippingAddress != null) 
		{
			//request.getRequestDispatcher("").forward(request, response);
			response.sendRedirect(LoginDao.HOMEPAGE);
		}
		else if (updateBooking!= null) 
		{
			response.sendRedirect(NewpageDao.NEW);
			//request.getRequestDispatcher("").forward(request, response);
		}
		
		
		
	}

}
