	package com.passenger;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.login.*;

import com.newpage.NewpageDao;

@WebServlet("/Passenger")
public class Passenger extends HttpServlet {

	private String customerId;
	private String title = null;
	private String firstname;
	private String middlename = null;
	private String lastname;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("Passenger.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		NewpageDao npdao = new NewpageDao();
		PassengerDao pdao = new PassengerDao();
		
		customerId = request.getParameter(NewpageDao.getNewCustomerId()).trim();
		if(!request.getParameter(PassengerDao.getPassengerTitle()).trim().isEmpty())
			title = request.getParameter(PassengerDao.getPassengerTitle()).trim();
		firstname = request.getParameter(LoginDao.getRegisterFirstname()).trim();
		if(!request.getParameter(PassengerDao.getPassengerMiddlename()).trim().isEmpty())
			middlename = request.getParameter(PassengerDao.getPassengerMiddlename()).trim();
		lastname = request.getParameter(LoginDao.getRegisterLastname()).trim();
		
		
		
		// search clicked
		if (request.getParameter("search") != null) 
		{
			// check if customer Id exists
			if(!npdao.checkCustomerId(customerId))
			{
				//request.setAttribute(PassengerDao.PASSENGER, "");
				request.setAttribute(PassengerDao.PASSENGER_FAILED, PassengerDao.getPassengerCustomerIdInvalidFailed());
				
			}	
			else
			{
				request.setAttribute(PassengerDao.PASSENGER_LIST, pdao.getPassenger(customerId));
 
			}
			
			request.getRequestDispatcher("Passenger.jsp").forward(request, response);
		}
		// not search clicked
		else
		{
			// check required fields
			if(customerId.isEmpty() || firstname.isEmpty() || lastname.isEmpty())
			{
				request.setAttribute(PassengerDao.PASSENGER_FAILED, PassengerDao.getPassengerEmptyFieldFailed());
				//request.setAttribute(PassengerDao.PASSENGER, "");
				return;
			}
			
			// add clicked
			if(request.getParameter("add") != null) 
			{

			}
			// update clicked
			else if(request.getParameter("update") != null) 
			{
				
			}
			else
			{
				
			}
		}
		
			


	}

}















