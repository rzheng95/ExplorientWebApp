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
	public final static int ACTIVITY_TEMPLATE = 1;
	public final static int ACTIVITY = 2;
	public final static int CITY = 3;
	public final static int COUNTRY = 4;
	public final static int LAND_VOUCHERS = 5;
	public final static int HOTEL_VOUCHERS = 6;
	
	
	public final static int LAND_VOUCHER_LIST_SIZE = 9;
	public final static int LAND_VOUCHER_CUSTOMER_ID = 0;
	public final static int LAND_VOUCHER_SERVICE_DATE = 1;
	public final static int LAND_VOUCHER_SERVICE = 2;
	public final static int LAND_VOUCHER_VENDOR = 3;
	public final static int LAND_VOUCHER_BREAKFAST = 4;
	public final static int LAND_VOUCHER_LUNCH = 5;
	public final static int LAND_VOUCHER_DINNER = 6;
	public final static int LAND_VOUCHER_CITY = 7;
	public final static int LAND_VOUCHER_COUNTRY = 8;
	
	
	public final static int HOTEL_VOUCHER_LIST_SIZE = 8;
	public final static int HOTEL_VOUCHER_CUSTOMER_ID = 0;
	public final static int HOTEL_VOUCHER_SERVICE_DATE = 1;
	public final static int HOTEL_VOUCHER_HOTEL = 2;
	public final static int HOTEL_VOUCHER_BREAKFAST = 3;
	public final static int HOTEL_VOUCHER_LUNCH = 4;
	public final static int HOTEL_VOUCHER_DINNER = 5;
	public final static int HOTEL_VOUCHER_CITY = 6;
	public final static int HOTEL_VOUCHER_COUNTRY = 7;
	
	private String customerId;
	private String searchBoxLastname;
	private String departureDateFrom;
	private String departureDateTo;
	
	private String getCustomerIdsButton;
	private String getItineraryButton;
	private String searchButton;
	private String getActivityButton;
	private String moreLandServiceButton;
	
	private String searchCountry;
	private String searchCity;
	
	private PassengerDao pdao;
	private NewpageDao npdao;
	private ItineraryDao idao;
	private HotelDao hdao;
	private VendorDao vdao;
	
	private ArrayList<Object> tourList;
	private ArrayList<ArrayList<String>> landVouchers;
	private ArrayList<ArrayList<String>> hotelVouchers;
	private ArrayList<String> dates;
	private boolean buttonClicked;
	
	private String day;
	private String activity;
	private String activityTemplates;
	
	private String landService;
	private String vendors;
	private String accommodations;
	private String roomType;
	

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
		searchButton = ItineraryDao.getItinerarySearchButton();
		getActivityButton = ItineraryDao.getItineraryGetActivityButton();
		moreLandServiceButton = ItineraryDao.getItineraryMoreLandServiceButton();
		
		// itinerary variables
		day = ItineraryDao.getItineraryDay();
		activity = ItineraryDao.getItineraryActivity();
		accommodations = ItineraryDao.getItineraryAccommodations();
		activityTemplates = ItineraryDao.getItineraryActivityTemplates();
		vendors = ItineraryDao.getItineraryVendors();
		landService = ItineraryDao.getItineraryLandService();
		roomType = ItineraryDao.getItineraryRoomType();
		
		// hotel search textfields
		searchCountry = ItineraryDao.getItineraryHotelCountry();
		searchCity = ItineraryDao.getItineraryHotelCity();
		
		buttonClicked = false;
		
		// two items, 1. departure date   2. return date
		dates = idao.getBookingDatesByCustomerId(customerId);
		
 		daysBetweenDates = 0;
 		if(dates != null && !dates.isEmpty())
 		{
			if(dates.size() == DATE_SIZE) 
				daysBetweenDates = idao.daysBetweenDates(dates.get(DEPARTURE_DATE), dates.get(RETURN_DATE));
 		}
 		
 		
 		
		// entered values 
		ArrayList<ArrayList<String>> enteredTourList = new ArrayList<>();
 		
		for(int i=0; i <= daysBetweenDates; i++)
		{
	 		ArrayList<String> enteredTour = new ArrayList<>();
	 		// create empty slots
	 		for(int j=0; j<Itinerary.TOUR_LIST_SIZE; j++)
	 		{
	 			enteredTour.add("");
	 		}

	 		enteredTour.set(Itinerary.TRAVEL_DATE, request.getParameter(day+i));
	 		enteredTour.set(Itinerary.ACTIVITY_TEMPLATE, request.getParameter(activityTemplates+i));
	 		enteredTour.set(Itinerary.ACTIVITY, request.getParameter(activity+i));
	 		enteredTour.set(Itinerary.CITY, request.getParameter(searchCity+i));
	 		enteredTour.set(Itinerary.COUNTRY, request.getParameter(searchCountry+i));

			enteredTourList.add(enteredTour);
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
		if(!buttonClicked && request.getParameter(getItineraryButton) != null)
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
				ArrayList<Object> returnTourList = new ArrayList<>();
				
				// get all the tours with the customerId given
				tourList = idao.getToursByCustomerId(customerId);
				
				// get land vouchers
				landVouchers = idao.getLandVouchers(customerId);
				
				// get hotel vouchers
				hotelVouchers = idao.getHotelVouchers(customerId);
			 		
		 		// starting day, first day, also the departure day
		 		LocalDate day = LocalDate.parse(dates.get(DEPARTURE_DATE));
		 		

		 		// loop total travel days times
		 		for(int i=0; i <= daysBetweenDates; i++) 
		 		{
		 			boolean found = false;
		 			ArrayList<String> emptyTour = new ArrayList<>();
		 			emptyTour.add(day.toString().trim());
		 			
		 			// loop through tourList
		 			for(int t=0; t < tourList.size(); t++)
		 			{
		 				ArrayList<Object> currentTour =  (ArrayList<Object>) tourList.get(t);
		 				
		 				String travelDate = (String) currentTour.get(TRAVEL_DATE);

		 				// merge if travel_date is matched
		 				if(day.equals(LocalDate.parse( travelDate )))
			 			{
		 					ArrayList<ArrayList<String>> temp = new ArrayList<>();
				 			// loop through land vouchers
				 			for(int l=0; l < landVouchers.size(); l++)
				 			{	 				
				 				ArrayList<String> currentLandVoucher = landVouchers.get(l);
					 			// merge if land service_date is matched
					 			if(day.equals(LocalDate.parse(currentLandVoucher.get(LAND_VOUCHER_SERVICE_DATE))))
					 			{
					 				temp.add(landVouchers.remove(l));
					 				l--;
					 			}
				 			}
				 			
				 			if(!temp.isEmpty()) currentTour.add(temp);	
		 					
		 					temp = new ArrayList<>();
				 			// loop through hotel vouchers
				 			for(int h=0; h < hotelVouchers.size(); h++)
				 			{
				 				ArrayList<String> currentHotelVoucher = hotelVouchers.get(h);
					 			// merge if hotel service_date is matched
					 			if(day.equals(LocalDate.parse(currentHotelVoucher.get(HOTEL_VOUCHER_SERVICE_DATE))))
					 			{
					 				temp.add(hotelVouchers.remove(h));
					 				h--;
					 			}
				 			}
				 			
				 			if(!temp.isEmpty()) currentTour.add(temp);		
			 				returnTourList.add(currentTour);
			 				found = true;
			 				break;
			 			}
		 				
		 			}
		 			if(!found) returnTourList.add(emptyTour);
		 			

		 			// increment day
		 			day = day.plusDays(1);
		 		}
	 			System.out.println(returnTourList);
		 			 		
		 		tourList = returnTourList;		
				request.setAttribute(ItineraryDao.TOUR_LIST, tourList);
			}
		}
		


		// check if tourList is empty
		if(!buttonClicked && tourList != null && !tourList.isEmpty())
		{
			String country = "";
			String city = "";
			// loop through all the tours
			for(int i=0; i<tourList.size(); i++)
			{
				
				// one of the search buttons clicked
				if(request.getParameter(searchButton+i) != null)
				{
					buttonClicked = true;
					
					country = request.getParameter(searchCountry+i);
					city = request.getParameter(searchCity+i);
					
					
					
					request.setAttribute(ItineraryDao.ROW_ID, "#row"+i);
					
					
					break;
				}
				
				// one of the get activity buttons clicked
				if(request.getParameter(getActivityButton+i) != null)
				{
					buttonClicked = true;
					
					country = request.getParameter(searchCountry+i);
					city = request.getParameter(searchCity+i);
					
					String activityTemplate = request.getParameter(activityTemplates+i);
					
					ArrayList<String> activityInfo = idao.getActivityCityAndCountryByActivityTemplate(activityTemplate);
					
					// update activity, city and country
					if(activityInfo.size() == 3)
					{
						enteredTourList.get(i).set(Itinerary.ACTIVITY, activityInfo.get(0));
						enteredTourList.get(i).set(Itinerary.CITY, activityInfo.get(1));
						enteredTourList.get(i).set(Itinerary.COUNTRY, activityInfo.get(2));
					}
					
					request.setAttribute(ItineraryDao.ROW_ID, "#row"+i);
					
					break;
				}
				
				// one of the MoreLandService buttons clicked
				if(request.getParameter(moreLandServiceButton) != null)
				{
					buttonClicked = true;
					
					
				}
				
			}
			
	 		
			if(buttonClicked)
			{
				request.setAttribute(ItineraryDao.ACTIVITY_TEMPLATE_LIST, idao.getActivityTemplates(country, city));
				request.setAttribute(ItineraryDao.TOUR_NAME_LIST, idao.getTourNames(country, city));
				request.setAttribute(ItineraryDao.VENDOR_LIST, vdao.getVendors(country, city));
				request.setAttribute(ItineraryDao.HOTEL_LIST, hdao.getHotels(country, city));
			}

			// entered tour list
			System.out.println(enteredTourList);
			request.setAttribute(ItineraryDao.TOUR_LIST, enteredTourList);
			
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






























