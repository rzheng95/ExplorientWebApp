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
import com.vendor.VendorDao;
import com.hotel.HotelDao;

@WebServlet("/Itinerary")
public class Itinerary extends HttpServlet {
	private final int DATE_SIZE = 2;
	private final int DEPARTURE_DATE = 0;
	private final int RETURN_DATE = 1;
	
	
	public final static int TOUR_LIST_SIZE = 7;
	public final static int TRAVEL_DATE = 0;
	public final static int TOUR = 1;
	public final static int ACTIVITY = 2;
	public final static int VENDOR = 3;
	public final static int ACCOMMODATION = 4;
	public final static int CITY = 5;
	public final static int COUNTRY = 6;
	
	private String customerId;
	private String searchBoxLastname;
	private String departureDateFrom;
	private String departureDateTo;
	
	private String getCustomerIdsButton;
	private String getItineraryButton;
	private String getHotelsButton;
	private String getToursButton;
	private String getActivityButton;
	
	private String hotelCountry;
	private String hotelCity;
	private String tourCountry;
	private String tourCity;
	
	private PassengerDao pdao;
	private NewpageDao npdao;
	private ItineraryDao idao;
	private HotelDao hdao;
	private VendorDao vdao;
	
	private ArrayList<ArrayList<String>> tourList;
	private ArrayList<String> dates;
	private boolean buttonClicked;
	
	private String day;
	private String activity;
	private String accommodations;
	private String tours;
	private String vendors;

	private int daysBetweenDates;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("Itinerary.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		pdao = new PassengerDao();
		npdao = new NewpageDao();
		idao = new ItineraryDao();
		hdao = new HotelDao();
		vdao = new VendorDao();
		
		// textfields
		customerId = request.getParameter(NewpageDao.getNewCustomerId()).trim();
		searchBoxLastname = request.getParameter(PassengerDao.getPassengerSearchBoxLastname()).trim();
		departureDateFrom = request.getParameter(PassengerDao.getPassengerDepartureDateFrom()).trim();
		departureDateTo = request.getParameter(PassengerDao.getPassengerDepartureDateTo()).trim();
		
		
		// buttons
		getCustomerIdsButton = PassengerDao.getPassengerGetCustomerIds();
		getItineraryButton = ItineraryDao.getItineraryGetItineraryButton();
		getHotelsButton = HotelDao.getHotelGetHotelsButton();
		getToursButton = ItineraryDao.getItineraryGetToursButton();
		getActivityButton = ItineraryDao.getItineraryGetActivityButton();
		
		// itinerary variables
		day = ItineraryDao.getItineraryDay();
		activity = ItineraryDao.getItineraryActivity();
		accommodations = ItineraryDao.getItineraryAccommodations();
		tours = ItineraryDao.getItineraryTours();
		vendors = ItineraryDao.getItineraryVendors();
		
		// hotel search textfields
		hotelCountry = ItineraryDao.getItineraryHotelCountry();
		hotelCity = ItineraryDao.getItineraryHotelCity();
		String tourCountry = ItineraryDao.getItineraryHotelCountry();
		String tourCity = ItineraryDao.getItineraryHotelCity();
		
		buttonClicked = false;
		
		// two items, 1. departure date   2. return date
		dates = idao.getBookingDatesByCustomerId(customerId);
		
 		daysBetweenDates = 0;
 		if(dates != null && !dates.isEmpty())
 		{
			if(dates.size() == DATE_SIZE) 
				daysBetweenDates = idao.daysBetweenDates(dates.get(DEPARTURE_DATE), dates.get(RETURN_DATE));
 		}
 		
 		
		

		// Get Customer Ids button clicked
		if(request.getParameter(getCustomerIdsButton) != null)
		{
			buttonClicked = true;
			
			// searchBoxLastname is not null
			if(searchBoxLastname != null)
			{
				// searchBoxLastname is not empty
				if(!searchBoxLastname.isEmpty())
				{
					// entered last name only has one letter
					if(searchBoxLastname.length() < 2)
					{
						request.setAttribute(ItineraryDao.ITINERARY_FAILED, PassengerDao.getPassengerOneLetterFailed());	
					}
					// entered last name has more than one letter
					else
					{
						ArrayList<String> ids = pdao.getCustomerIdLikeCustomerId(searchBoxLastname);
						if(ids.isEmpty())
							request.setAttribute(ItineraryDao.ITINERARY_FAILED, PassengerDao.getZeroResultFoundFailed());
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
						request.setAttribute(ItineraryDao.ITINERARY_FAILED, NewpageDao.getDateFormatInvalidFailed());
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
							request.setAttribute(ItineraryDao.ITINERARY_FAILED, PassengerDao.getZeroResultFoundFailed());
						else
							request.setAttribute(PassengerDao.PASSENGER_CUSTOMER_IDS, ids);
					}
				}
			}
			customerId = "";			
		}
		
		// Get Itinerary button clicked
		if(request.getParameter(getItineraryButton) != null && !buttonClicked)
		{
			buttonClicked = true;
			
			// customerId exist doesn't exist
			if(!npdao.checkCustomerId(customerId))
			{
				request.setAttribute(ItineraryDao.ITINERARY_FAILED, NewpageDao.getCustomerIdDoesNotExistFailed());
				customerId = "";
			}
			// customerId exists
			else
			{
				ArrayList<ArrayList<String>> returnTourList = new ArrayList<>();
				
				// get all the tours with the customerId given
				tourList = idao.getToursByCustomerId(customerId);
	
		 		
		 		// starting day, first day, also the departure day
		 		LocalDate day = LocalDate.parse(dates.get(DEPARTURE_DATE));
		 		
		 		// loop total travel days times
		 		for(int i=0; i <= daysBetweenDates; i++) 
		 		{
		 			boolean found = false;
		 			ArrayList<String> emptyTour = new ArrayList<>();
		 			
		 			// loop through the existing tours or previously added tours in the database
		 			for(int j=0; j < tourList.size(); j++)
		 			{
			 			// check if the dates are the same
			 			if(day.equals(LocalDate.parse(tourList.get(j).get(TRAVEL_DATE))))
			 			{
			 				returnTourList.add(tourList.get(j));
			 				found = true;
			 			}
		 			}
		 			
		 			// if tour can not be found, add empty tour & the date
		 			if(!found)
		 			{
			 			emptyTour.add(day.toString().trim());
			 			returnTourList.add(emptyTour);
		 			}
		 			// increment day
		 			day = day.plusDays(1);
		 		}
		 			 		
		 		tourList = returnTourList;		
				request.setAttribute(ItineraryDao.TOUR_LIST, tourList);
			}
		}
		
		

		// check if tourList is empty
		if(tourList != null && !tourList.isEmpty() && !buttonClicked)
		{
			// loop through all the tours
			for(int i=0; i<tourList.size(); i++)
			{
				// one of the Get Hotels button clicked
				if(request.getParameter(getHotelsButton+i) != null)
				{
					buttonClicked = true;
					
					String country = request.getParameter(hotelCountry+i);
					String city = request.getParameter(hotelCity+i);
					
							
					request.setAttribute(ItineraryDao.HOTEL_LIST, hdao.getHotels(country, city));
					break;
				}
				
				// one of the Get Tours button clicked
				if(request.getParameter(getToursButton+i) != null)
				{
					buttonClicked = true;
					
					String country = request.getParameter(tourCountry+i);
					String city = request.getParameter(tourCity+i);
					
					request.setAttribute(ItineraryDao.TOUR_NAME_LIST, idao.getTourNames(country, city));
					
					request.setAttribute(ItineraryDao.VENDOR_LIST, vdao.getVendors(country, city));
					
					break;
				}
				
			}
			
	 		
			
			// entered values 
	 		tourList = new ArrayList<>();
	 		
			for(int i=0; i <= daysBetweenDates; i++)
			{
		 		ArrayList<String> tour = new ArrayList<>();
		 		// create empty slots
		 		for(int j=0; j<Itinerary.TOUR_LIST_SIZE; j++)
		 		{
		 			tour.add("");
		 		}

		 		tour.set(Itinerary.TRAVEL_DATE, request.getParameter(day+i));
				tour.set(Itinerary.ACTIVITY, request.getParameter(activity+i));
				tour.set(Itinerary.ACCOMMODATION, request.getParameter(accommodations+i));
				tour.set(Itinerary.COUNTRY, request.getParameter(tourCountry+i));
				tour.set(Itinerary.CITY, request.getParameter(tourCity+i));
				tour.set(Itinerary.TOUR, request.getParameter(tours+i));
				tour.set(Itinerary.VENDOR, request.getParameter(vendors+i));
				tourList.add(tour);
			}
			
			request.setAttribute(ItineraryDao.TOUR_LIST, tourList);
			
		}
		
		

		
		// customer ID search box entered values
		request.setAttribute(ItineraryDao.ITINERARY, 
				 searchBoxLastname+ItineraryDao.EQUAL+
				 departureDateFrom+ItineraryDao.EQUAL+
				 departureDateTo+ItineraryDao.EQUAL+
				 customerId+ItineraryDao.EQUAL);

		request.getRequestDispatcher("Itinerary.jsp").forward(request, response);
		
	}

}






























