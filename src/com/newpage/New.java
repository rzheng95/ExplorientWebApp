/*
 * This file is part of Explorient Web App
 * Copyright (C) 2016-2019 Richard R. Zheng
 *
 * https://github.com/rzheng95/ExplorientWebApp
 * 
 * All Right Reserved.
 */

package com.newpage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itinerary.ItineraryDao;
import com.login.LoginDao;
import com.passenger.PassengerDao;

@WebServlet("/New")
public class New extends HttpServlet {
	private int DATE_LENGTH = NewpageDao.DATE_LENGTH;
	private int YEAR = NewpageDao.YEAR;
	private int DAY = NewpageDao.DAY;
	private int MONTH = NewpageDao.MONTH;
	private String FORWARD_SLASH = NewpageDao.FORWARD_SLASH;
	private String EQUAL = NewpageDao.EQUAL;
	private String COMMA = NewpageDao.COMMA;
	private String DASH = NewpageDao.DASH;
	
	private NewpageDao npdao;
	private LoginDao ldao;
	private PassengerDao pdao;
	private String agent;
	private String customer_id;
	private String destination;
	private String air;
	private String date_of_departure;
	private String date_of_return;
	private String country;
	private String getPackages;
	private String tour_package;
	
	// customer IDs search box
	private String customerId;
	private String searchBoxLastname;
	private String departureDateFrom;
	private String departureDateTo;
	
	private String getCustomerIds;
	private String getBookingButton;
	private String createButton;
	private String updateButton;
	private String clearButton;
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("New.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		npdao = new NewpageDao();
		ldao = new LoginDao();
		pdao = new PassengerDao();
		
		
		// Customer ID search box textfields
		customerId = request.getParameter(NewpageDao.getSearchBoxNewCustomerId()).trim();
		searchBoxLastname = request.getParameter(PassengerDao.getPassengerSearchBoxLastname()).trim();
		departureDateFrom = request.getParameter(PassengerDao.getPassengerDepartureDateFrom()).trim();
		departureDateTo = request.getParameter(PassengerDao.getPassengerDepartureDateTo()).trim();
					
		// Customer ID search box buttons
		getCustomerIds = PassengerDao.getPassengerGetCustomerIds();
		getBookingButton = NewpageDao.getNewGetBookingButton();
		createButton = NewpageDao.getNewCreateButton();
		updateButton = NewpageDao.getNewUpdateButton();
		clearButton = NewpageDao.getNewClearButton();
		
		
		
		// variables
		agent = request.getParameter(NewpageDao.getNewAgent()).trim();
		customer_id = request.getParameter(NewpageDao.getNewCustomerId()).trim();
		destination = request.getParameter(NewpageDao.getNewDestination()).trim();
		air = request.getParameter(NewpageDao.getNewAir()).trim();
		date_of_departure = request.getParameter(NewpageDao.getNewDateOfDeparture()).trim();
		date_of_return =  request.getParameter(NewpageDao.getNewDateOfReturn()).trim();
		country = request.getParameter(NewpageDao.getCountry()).trim();
		getPackages = NewpageDao.getGetPackages();
		tour_package = request.getParameter(NewpageDao.getNewTourPackage()).trim(); 
		
		
		// Clear button clicked
		if(request.getParameter(clearButton) != null)
		{
			agent = "";
			customer_id = "";
			destination = "";
			air = "";
			date_of_departure = "";
			date_of_return = "";
			country = "";
			tour_package = "";
			
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
						request.setAttribute(NewpageDao.NEWPAGE_FAILED, PassengerDao.getPassengerOneLetterFailed());	
					}
					// entered last name has more than one letter
					else
					{
						ArrayList<String> ids = pdao.getCustomerIdLikeCustomerId(searchBoxLastname);
						if(ids.isEmpty())
							request.setAttribute(NewpageDao.NEWPAGE_FAILED, PassengerDao.getZeroResultFoundFailed());
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
						request.setAttribute(NewpageDao.NEWPAGE_FAILED, NewpageDao.getDateFormatInvalidFailed());
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
							request.setAttribute(NewpageDao.NEWPAGE_FAILED, PassengerDao.getZeroResultFoundFailed());
						else
							request.setAttribute(PassengerDao.PASSENGER_CUSTOMER_IDS, ids);
					}
				}
			}
			customerId = "";			
		}	
		// get booking button clicked
		else if(request.getParameter(getBookingButton) != null)
		{
			// customer Id does not exist
			if(!npdao.checkCustomerId(customerId))
			{
				request.setAttribute(NewpageDao.NEWPAGE_FAILED, NewpageDao.getCustomerIdDoesNotExistFailed());
				customerId = "";
			}
			// grab all info about this booking
			else
			{
				ArrayList<String> bookingInfo = npdao.getBookingInfoByCustomerId(customerId);
				agent = bookingInfo.get(NewpageDao.AGENT_VALUE_INDEX);

				ArrayList<String> agentInfo = npdao.getAgent(agent);
				String temp = "";
				temp += agentInfo.get(0); // agent name
				temp += " - ";
				temp += agentInfo.get(1); // last name
				temp += ", ";
				temp += agentInfo.get(2); // first name

				agent = temp;

				customer_id = bookingInfo.get(NewpageDao.CUSTOMER_ID_VALUE_INDEX);
				destination = bookingInfo.get(NewpageDao.DESTINATION_VALUE_INDEX);
				tour_package = bookingInfo.get(3);
				air = bookingInfo.get(4);
				date_of_departure = npdao.yyyymmdd_To_mmddyyy(bookingInfo.get(5));
				date_of_return = npdao.yyyymmdd_To_mmddyyy(bookingInfo.get(6));

				
			}
		}
		// get Packages button pressed
		else if (request.getParameter(getPackages) != null) 
		{
			if(npdao.getCountries().contains(country))
			{
				request.setAttribute(NewpageDao.NEW_TOUR_PACKAGE, npdao.getTourPackages(country));
				tour_package = "";
			}
			
		}
		// some other button is clicked
		else
		{
			
			// check for = sign, which is reserved for split method and other symbols
			if(ldao.checkInvalidSymbols(customer_id) || ldao.checkInvalidSymbols(destination) || ldao.checkInvalidSymbols(air) || ldao.checkInvalidSymbols(tour_package))
			{
				request.setAttribute(NewpageDao.NEWPAGE_FAILED, LoginDao.getRegisterInvalidSymbolsLFailed());
				agent = ""; customer_id = ""; destination = ""; air = ""; date_of_departure = ""; date_of_return = ""; tour_package = "";
			}
			// check for empty field
			else if(agent.isEmpty() || customer_id.isEmpty() || destination.isEmpty() || air.isEmpty() || date_of_departure.isEmpty() || date_of_return.isEmpty() || tour_package.isEmpty())
			{
				request.setAttribute(NewpageDao.NEWPAGE_FAILED, NewpageDao.getEmptydFieldFailed());
			}
			// check if agent exists (check includes agent's firstname and lastname)
			else if(!agent.contains(DASH) || !agent.contains(COMMA) || !npdao.checkAgent(agent))
			{
				request.setAttribute(NewpageDao.NEWPAGE_FAILED, NewpageDao.getAgentNotFoundFailed());
				agent = "";
			}
			// check customer id format
			else if(!customer_id.contains(DASH))
			{
				request.setAttribute(NewpageDao.NEWPAGE_FAILED, NewpageDao.getCustomerIdInvalidFailed());
				customer_id = "";
			}
			// check date format
			else if(!date_of_departure.contains(FORWARD_SLASH) || !date_of_return.contains(FORWARD_SLASH) || !npdao.compareDates(date_of_departure, date_of_return))
			{
				request.setAttribute(NewpageDao.NEWPAGE_FAILED, NewpageDao.getDateFormatInvalidFailed());
				date_of_departure = "";
				date_of_return = "";
			}
			// no fails
			else 
			{
				// check if customer id exists
				if(npdao.checkCustomerId(customer_id))
				{		
					// create button pressed
					if(request.getParameter(createButton) != null)
					{
						request.setAttribute(NewpageDao.NEWPAGE_FAILED, NewpageDao.getCustomerIdExistsFailed());
						customer_id = "";
					}
					// update button pressed
					else if(request.getParameter(updateButton) != null)
					{
						npdao.updateBooking(agent, destination, tour_package, air, npdao.mmddyyyy_To_yyyymmdd(date_of_departure), npdao.mmddyyyy_To_yyyymmdd(date_of_return), customer_id);
						request.setAttribute(NewpageDao.NEWPAGE_FAILED, NewpageDao.getBookingUpdatedMessage());
						agent = "";
						destination = "";
						tour_package = "";
						air = "";
						date_of_departure = "";
						date_of_return = "";
						customer_id = "";
					}			
					
				}
				// customer id doesn't exist
				else
				{
					// update button pressed
					if(request.getParameter(updateButton) != null)
					{
						request.setAttribute(NewpageDao.NEWPAGE_FAILED, NewpageDao.getCustomerIdDoesNotExistFailed());
						customer_id = "";
					}
					// create button pressed and add booking to db
					else if(request.getParameter(createButton) != null)
					{
						npdao.addBooking(agent, customer_id, destination, air, npdao.mmddyyyy_To_yyyymmdd(date_of_departure), npdao.mmddyyyy_To_yyyymmdd(date_of_return), tour_package);
						
						HashMap<String, String> map = NewpageDao.getHashMap();	
						map.put(NewpageDao.getNewCustomerId(), customer_id); 
						
						response.sendRedirect("BookingCreated");
						return;
					}
				}

			}
			

		}
		
		

		request.setAttribute(NewpageDao.NEWPAGE, 
				agent+EQUAL+
				customer_id+EQUAL+
				destination+EQUAL+
				air+EQUAL+
				date_of_departure+EQUAL+
				date_of_return+EQUAL+
				country+EQUAL+
				tour_package+EQUAL+
				
				searchBoxLastname+EQUAL+
				 departureDateFrom+EQUAL+
				 departureDateTo+EQUAL+
				 customerId);
		
		
		request.getRequestDispatcher("New.jsp").forward(request, response);

	}
}












