package com.itinerary;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.newpage.NewpageDao;
import com.passenger.PassengerDao;

@WebServlet("/Itinerary")
public class Itinerary extends HttpServlet {
	private String customerId;
	private String searchBoxLastname;
	private String departureDateFrom;
	private String departureDateTo;
	
	private String getCustomerIds;
	private String getPassengers;
	
	private PassengerDao pdao;
	private NewpageDao npdao;
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("Itinerary.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		String[] activities = request.getParameterValues("activities");
		
		pdao = new PassengerDao();
		npdao = new NewpageDao();
		
		// textfields
		customerId = request.getParameter(NewpageDao.getNewCustomerId()).trim();
		searchBoxLastname = request.getParameter(PassengerDao.getPassengerSearchBoxLastname()).trim();
		departureDateFrom = request.getParameter(PassengerDao.getPassengerDepartureDateFrom()).trim();
		departureDateTo = request.getParameter(PassengerDao.getPassengerDepartureDateTo()).trim();
		
		
		// buttons
		getCustomerIds = PassengerDao.getPassengerGetCustomerIds();
		getPassengers = PassengerDao.getPassengerGetPassengers();
		
		
		/*if(activities != null)
			for(String a : activities)
			System.out.println(a);*/
		

		// Get Customer Ids button clicked
		if(request.getParameter(getCustomerIds) != null)
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
		// check if customer id exists
		
		
		
		// check if there is any passenger added to this booking
		
		
		
		
		// generate 
		
		
		request.setAttribute(ItineraryDao.ITINERARY, 
				 searchBoxLastname+ItineraryDao.EQUAL+
				 departureDateFrom+ItineraryDao.EQUAL+
				 departureDateTo+ItineraryDao.EQUAL+
				 customerId+ItineraryDao.EQUAL);

		request.getRequestDispatcher("Itinerary.jsp").forward(request, response);
		
	}

}
