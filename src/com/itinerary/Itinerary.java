package com.itinerary;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.newpage.NewpageDao;
import com.passenger.PassengerDao;
import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;
import com.hotel.HotelDao;

@WebServlet("/Itinerary")
public class Itinerary extends HttpServlet {
	private final int DATE_SIZE = 2;
	private final int DEPARTURE_DATE = 0;
	private final int RETURN_DATE = 1;
	
	public final static int TRAVEL_DATE = 0;
	public final static int TOUR = 1;
	public final static int ACTIVITY = 2;
	public final static int VENDOR = 3;
	public final static int HOTEL = 4;
	public final static int CITY = 5;
	public final static int COUNTRY = 6;
	
	private String customerId;
	private String searchBoxLastname;
	private String departureDateFrom;
	private String departureDateTo;
	
	private String getCustomerIdsButton;
	private String getItineraryButton;
	private String getHotelsButton;
	
	private String hotelCountry;
	private String hotelCity;
	
	private PassengerDao pdao;
	private NewpageDao npdao;
	private ItineraryDao idao;
	private HotelDao hdao;
	
	private ArrayList<ArrayList<String>> tourList;
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("Itinerary.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		pdao = new PassengerDao();
		npdao = new NewpageDao();
		idao = new ItineraryDao();
		hdao = new HotelDao();
		
		// textfields
		customerId = request.getParameter(NewpageDao.getNewCustomerId()).trim();
		searchBoxLastname = request.getParameter(PassengerDao.getPassengerSearchBoxLastname()).trim();
		departureDateFrom = request.getParameter(PassengerDao.getPassengerDepartureDateFrom()).trim();
		departureDateTo = request.getParameter(PassengerDao.getPassengerDepartureDateTo()).trim();
		
		
		// buttons
		getCustomerIdsButton = PassengerDao.getPassengerGetCustomerIds();
		getItineraryButton = ItineraryDao.getItineraryGetItineraryButton();
		getHotelsButton = HotelDao.getHotelGetHotelsButton();
		
		// hotel search textfields
		hotelCountry = ItineraryDao.getItineraryHotelCountry();
		hotelCity = ItineraryDao.getItineraryHotelCity();
		

		// Get Customer Ids button clicked
		if(request.getParameter(getCustomerIdsButton) != null)
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
			}
			customerId = "";			
		}
		// Get Itinerary button clicked
		else if(request.getParameter(getItineraryButton) != null)
		{
			ArrayList<ArrayList<String>> returnTourList = new ArrayList<>();
			ArrayList<String> dates = idao.getBookingDatesByCustomerId(customerId);
			tourList = idao.getToursByCustomerId(customerId);
			
			
	 		int daysBetweenDates = 0;
	 		if(dates != null && !dates.isEmpty())
	 		{
				if(dates.size() == DATE_SIZE) 
					daysBetweenDates = idao.daysBetweenDates(dates.get(DEPARTURE_DATE), dates.get(RETURN_DATE));
	 		}
	 		
	 		LocalDate day = LocalDate.parse(dates.get(DEPARTURE_DATE));
	 		for(int i=0; i <= daysBetweenDates; i++) 
	 		{
	 			boolean found = false;
	 			ArrayList<String> emptyTour = new ArrayList<>();
	 			
	 			
	 			for(int j=0; j < tourList.size(); j++)
	 			{
		 			// check if the dates are the same
		 			if(day.equals(LocalDate.parse(tourList.get(j).get(TRAVEL_DATE))))
		 			{
		 				returnTourList.add(tourList.get(j));
		 				found = true;
		 			}
	 			}
	 			
	 			if(!found)
	 			{
		 			emptyTour.add(day.toString());
		 			returnTourList.add(emptyTour);
	 			}
	 			day = day.plusDays(1);
	 		}
	 			 		
	 		tourList = returnTourList;		
			request.setAttribute(ItineraryDao.TOUR_LIST, tourList);
	
		}
		
		if(tourList != null && !tourList.isEmpty())
		{
			// loop through all the tours
			for(int i=0; i<tourList.size(); i++)
			{
				// one of the Get Hotels button clicked
				if(request.getParameter(getHotelsButton+i) != null)
				{
					String country = request.getParameter(hotelCountry+i);
					String city = request.getParameter(hotelCity+i);
					
							
					request.setAttribute(ItineraryDao.HOTEL_LIST, hdao.getHotels(country, city));
				}
			}
		}
		
		
		
		
		
		
		// entered value
			
			// date
			// tour
			// hotel country, city
			// hotel list
		
		
		
		request.setAttribute(ItineraryDao.ITINERARY, 
				 searchBoxLastname+ItineraryDao.EQUAL+
				 departureDateFrom+ItineraryDao.EQUAL+
				 departureDateTo+ItineraryDao.EQUAL+
				 customerId+ItineraryDao.EQUAL);

		request.getRequestDispatcher("Itinerary.jsp").forward(request, response);
		
	}

}
