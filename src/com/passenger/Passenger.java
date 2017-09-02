	package com.passenger;

import java.io.IOException;
import java.util.ArrayList;

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
	private String title;
	private String firstname;
	private String middlename ;
	private String lastname;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("Passenger.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		NewpageDao npdao = new NewpageDao();
		PassengerDao pdao = new PassengerDao();
		
		customerId = request.getParameter(NewpageDao.getNewCustomerId()).trim();
		title = request.getParameter(PassengerDao.getPassengerTitle()).trim();
		firstname = request.getParameter(LoginDao.getRegisterFirstname()).trim();
		middlename = request.getParameter(PassengerDao.getPassengerMiddlename()).trim();
		lastname = request.getParameter(LoginDao.getRegisterLastname()).trim();
		
		// check equal signs
		if(customerId.contains(PassengerDao.EQUAL) || title.contains(PassengerDao.EQUAL) || firstname.contains(PassengerDao.EQUAL) || middlename.contains(PassengerDao.EQUAL) || lastname.contains(PassengerDao.EQUAL))
		{
			request.setAttribute(PassengerDao.PASSENGER_FAILED, NewpageDao.getEqualSignFailed());	
		}				
		// customer Id doesn't exist
		else if(customerId == null || !npdao.checkCustomerId(customerId))
		{
			request.setAttribute(PassengerDao.PASSENGER_FAILED, PassengerDao.getPassengerCustomerIdInvalidFailed());	
		}	
		// customer Id exists
		else
		{ 
			// get Passengers clicked
			if (request.getParameter("getPassengers") != null) 
			{
				request.setAttribute(PassengerDao.PASSENGER, customerId+PassengerDao.EQUAL+
																		PassengerDao.EQUAL+
																		PassengerDao.EQUAL+
																		PassengerDao.EQUAL);
			}
			else if(customerId.isEmpty() || firstname.isEmpty() || lastname.isEmpty())
			{
				request.setAttribute(PassengerDao.PASSENGER_FAILED, PassengerDao.getPassengerEmptyFieldFailed());
				request.setAttribute(PassengerDao.PASSENGER, customerId+PassengerDao.EQUAL+
															 title+PassengerDao.EQUAL+
															 firstname+PassengerDao.EQUAL+
															 middlename+PassengerDao.EQUAL+
															 lastname);
			}		
			// add clicked
			else if(request.getParameter("add") != null) 
			{
				// check if passenger already exists
				if(!pdao.checkDuplicatePassenger(customerId, title, firstname, middlename, lastname))
				{
					pdao.addPassenger(customerId, title, firstname, middlename, lastname);
					request.setAttribute(PassengerDao.PASSENGER_FAILED, PassengerDao.getPassengerAddedMessage());
				}
				// passenger already exists
				else
				{
					request.setAttribute(PassengerDao.PASSENGER_FAILED, PassengerDao.getPassengerAlreadyExistsFailed());
				}
				request.setAttribute(PassengerDao.PASSENGER, customerId+PassengerDao.EQUAL+
						PassengerDao.EQUAL+
						PassengerDao.EQUAL+
						PassengerDao.EQUAL);	
			}
			// delete clicked
			else if(request.getParameter("delete") != null) 
			{
				// passenger exists
				if(pdao.checkDuplicatePassenger(customerId, title, firstname, middlename, lastname))
				{
					pdao.deletePassenger(customerId, title, firstname, middlename, lastname);
					request.setAttribute(PassengerDao.PASSENGER_FAILED, PassengerDao.getPassengerDeletedMessage());
				}
				// passenger doesn't exist
				else
				{
					request.setAttribute(PassengerDao.PASSENGER_FAILED, PassengerDao.getPassengerDoesNotExistFailed());
				}
				request.setAttribute(PassengerDao.PASSENGER, customerId+PassengerDao.EQUAL+
						PassengerDao.EQUAL+
						PassengerDao.EQUAL+
						PassengerDao.EQUAL);	
			}

			
			// update passenger list
			request.setAttribute(PassengerDao.PASSENGER_LIST, pdao.getPassenger(customerId));
		}
		

		request.getRequestDispatcher("Passenger.jsp").forward(request, response);
	}
}















