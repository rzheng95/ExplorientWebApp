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

	//buttons
	private String getCustomerIds;
	private String getPassengers;
	private String add;
	private String delete;
	
	// text fields
	private String customerId;
	private String searchBoxLastname;
	private String departureDateFrom;
	private String departureDateTo;
	private String title;
	private String firstname;
	private String middlename ;
	private String lastname;
	
	
	private NewpageDao npdao;
	private PassengerDao pdao;
	private LoginDao ldao;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("Passenger.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		npdao = new NewpageDao();
		pdao = new PassengerDao();
		ldao = new LoginDao();
		
		// text fields
		customerId = request.getParameter(NewpageDao.getNewCustomerId()).trim();
		searchBoxLastname = request.getParameter(PassengerDao.getPassengerSearchBoxLastname()).trim();
		departureDateFrom = request.getParameter(PassengerDao.getPassengerDepartureDateFrom()).trim();
		departureDateTo = request.getParameter(PassengerDao.getPassengerDepartureDateTo()).trim();
		title = request.getParameter(PassengerDao.getPassengerTitle()).trim();
		firstname = request.getParameter(LoginDao.getRegisterFirstname()).trim();
		middlename = request.getParameter(PassengerDao.getPassengerMiddlename()).trim();
		lastname = request.getParameter(LoginDao.getRegisterLastname()).trim();
		
		// buttons 
		getCustomerIds = PassengerDao.getPassengerGetCustomerIds();
		getPassengers = PassengerDao.getPassengerGetPassengers();
		add = PassengerDao.getPassengerAdd();
		delete = PassengerDao.getPassengerDelete();
		
		// check invalid symbols
		if(ldao.checkInvalidSymbols(searchBoxLastname) || ldao.checkInvalidSymbols(title) || ldao.checkInvalidSymbols(firstname) || ldao.checkInvalidSymbols(middlename) || ldao.checkInvalidSymbols(lastname))
		{
			request.setAttribute(PassengerDao.PASSENGER_FAILED, LoginDao.getRegisterInvalidSymbolsLFailed());	
		}	
		// Get Customer Ids button clicked
		else if(request.getParameter(getCustomerIds) != null)
		{
			// searchBoxLastname is not null
			if(searchBoxLastname != null)
			{
				// searchBoxLastname is not empty
				if(!searchBoxLastname.isEmpty())
				{
					// entered last name only has one letter
					if(searchBoxLastname.length() < 2)
					{
						request.setAttribute(PassengerDao.PASSENGER_FAILED, PassengerDao.getPassengerOneLetterFailed());	
					}
					// entered last name has more than one letter
					else
					{
						ArrayList<String> ids = pdao.getCustomerIdLikeCustomerId(searchBoxLastname);
						if(ids.isEmpty())
							request.setAttribute(PassengerDao.PASSENGER_FAILED, PassengerDao.getZeroResultFoundFailed());
						else
							request.setAttribute(PassengerDao.PASSENGER_CUSTOMER_IDS, ids);
					}		
				}
				// both entered daparture date textfields are not empty
				else if(!departureDateFrom.isEmpty() && !departureDateTo.isEmpty())
				{
					// when dop FROM is greater than dop TO
					if(!npdao.compareDates(departureDateFrom, departureDateTo))
					{
						request.setAttribute(PassengerDao.PASSENGER_FAILED, NewpageDao.getDateFormatInvalidFailed());
						departureDateFrom = "";
						departureDateTo = "";
					}
					// is a vaild date range
					else
					{
						String from = pdao.convertDateFormat(departureDateFrom);
						String to = pdao.convertDateFormat(departureDateTo);
						
						ArrayList<String> ids = pdao.getCustomerIdWithDateRange(from, to);
						
						if(ids.isEmpty())
							request.setAttribute(PassengerDao.PASSENGER_FAILED, PassengerDao.getZeroResultFoundFailed());
						else
							request.setAttribute(PassengerDao.PASSENGER_CUSTOMER_IDS, ids);
					}
				}
				//else{}
			}
			customerId = "";			
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
			if (request.getParameter(getPassengers) != null) 
			{
				title = "";
				firstname = "";
				middlename = "";
				lastname = "";
			}
			// check empty fields
			else if(customerId.isEmpty() || firstname.isEmpty() || lastname.isEmpty())
			{
				request.setAttribute(PassengerDao.PASSENGER_FAILED, PassengerDao.getPassengerEmptyFieldFailed());
			}		
			else 
			{
			// add clicked
				if(request.getParameter(add) != null) 
				{
					// if passenger does not exist
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
				}
				// delete clicked
				else if(request.getParameter(delete) != null) 
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
				}
				title = "";
				firstname = "";
				middlename = "";
				lastname = "";
			}		
			// update passenger list
			request.setAttribute(PassengerDao.PASSENGER_LIST, pdao.getPassenger(customerId));
		}
		
		request.setAttribute(PassengerDao.PASSENGER, 
				 searchBoxLastname+PassengerDao.EQUAL+
				 departureDateFrom+PassengerDao.EQUAL+
				 departureDateTo+PassengerDao.EQUAL+
				 customerId+PassengerDao.EQUAL+
				 title+PassengerDao.EQUAL+
				 firstname+PassengerDao.EQUAL+
				 middlename+PassengerDao.EQUAL+
				 lastname);

		request.getRequestDispatcher("Passenger.jsp").forward(request, response);
	}
}















