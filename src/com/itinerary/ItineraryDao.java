package com.itinerary;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import org.mariadb.jdbc.Driver;
import com.newpage.NewpageDao;

public class ItineraryDao extends HttpServlet
{			
	// db connection
	public final static String DB_URL = "database.url";
	public final static String DB_USERNAME = "database.username";
	public final static String DB_PASSWORD = "database.password";
	public final static String EQUAL = NewpageDao.EQUAL;
	
	public final static String ITINERARY = "itinerary";
	public final static String ROW_ID = "ROW_ID";
	
	public final static String TOUR_LIST = "tour list";
	public final static String TOUR_NAME_LIST = "tour name list";
	public final static String HOTEL_LIST = "hotel list";
	public final static String ACTIVITY_TEMPLATE_LIST = "activity template list";
	public final static String VENDOR_LIST = "vendor list";
	
	
	// db queries
	public final static String GET_TOURS_BY_CUSTOMER_ID_QUERY = "database.get.tours.by.customer.id.query";
	public final static String GET_BOOKING_DATES_BY_CUSTOMER_ID_QUERY = "database.get.booking.dates.by.customer.id.query";
	
	public final static String GET_COUNTRIES_FROM_TOURS_TOURTEMPLATES_HOTELS_AND_VENDORS_QUERY = "database.get.countries.from.Tours.Tour_Templates.Hotels.and.Vendors.query";
	public final static String GET_CITIES_FROM_TOURS_TOURTEMPLATES_HOTELS_AND_VENDORS_QUERY = "database.get.cities.from.Tours.Tour_Templates.Hotels.and.Vendors.query";
	
	public final static String GET_ACTIVITY_TEMPLATES_BY_CITY_QUERY = "database.get.activity.templates.by.city.query";
	public final static String GET_ACTIVITY_TEMPLATES_BY_COUNTRRY_QUERY = "database.get.activity.templates.by.country.query";
	
	public final static String GET_TOURS_FROM_TOURS_AND_TOUR_TEMPLATES_BY_COUNTRY_QUERY = "database.get.tours.from.Tours.and.Tour_Templates.by.country.query";
	public final static String GET_TOURS_FROM_TOURS_AND_TOUR_TEMPLATES_BY_CITY_QUERY = "database.get.tours.from.Tours.and.Tour_Templates.by.city.query";
	
	public final static String GET_ACTIVITY_CITY_AND_COUNTRY_BY_TOURNAME_QUERY = "database.get.activity.city.and.country.by.tourName.query";
	
	// failed
	
	// itinerary variables
	public final static String DATES = "dates";
	public final static String ITINERARY_HOTEL_COUNTRY = "itinerary.hotel.country";
	public final static String ITINERARY_HOTEL_CITY = "itinerary.hotel.city";
	public final static String ITINERARY_DAY = "itinerary.day";
	public final static String ITINERARY_ACTIVITY = "itinerary.activity";
	public final static String ITINERARY_ACCOMMODATIONS = "itinerary.accommodations";
	public final static String ITINERARY_ACTIVITY_TEMPLATES = "itinerary.activity.templates";
	public final static String ITINERARY_VENDORS = "itinerary.vendors";
	
	// button
	public final static String ITINERARY_GET_ITINERARY_BUTTON = "itinerary.get.itinerary.button";
	public final static String ITINERARY_SEARCH_BUTTON = "itinerary.search.button";
	public final static String ITINERARY_GET_ACTIVITY_BUTTON = "itinerary.get.activity.button";
	
	// message

	// hotel variables
	
	
	
	
	// db connection
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs;
	private static String db_username;
	private static String db_password;
	private static String db_url;
	
	
	// db queries
	private static String getToursByCustomerIdQuery;
	private static String getBookingDatesByCustomerIdQuery;
	
	private static String getCountriesFromToursTourtemplatesHotelsAndVendorsQuery;
	private static String getCitiesFromToursTourtemplatesHotelsAndVendorsQuery;
	
	private static String getActivityTemplatesByCityQuery;
	private static String getActivityTemplatesByCountryQuery;
	
	private static String getToursFromToursAndTourtemplatesByCountryQuery;
	private static String getToursFromToursAndTourtemplatesByCityQuery;
	
	private static String getActivityCityAndCountryByTournameQuery;
	
	// failed
	public final static String ITINERARY_FAILED = "itinerary.failed";
	
	// itinerary varibales
	private static String itineraryHotelCountry;
	private static String itineraryHotelCity;
	private static String itineraryDay;
	private static String itineraryActivity;
	private static String itineraryAccommodations;
	private static String itineraryActivityTemplates;
	private static String itineraryVendors;

	// button
	private static String itineraryGetItineraryButton;
	private static String itinerarySearchButton;
	private static String itineraryGetActivityButton;
	
	// message

	
	
	
	
	public void init()
	{
		
		try {
			ServletContext sc = this.getServletContext();

			// db connection
			db_username = sc.getInitParameter(DB_USERNAME);
			db_password = sc.getInitParameter(DB_PASSWORD);
			db_url = sc.getInitParameter(DB_URL);
			
			
			// db queries
			getToursByCustomerIdQuery = sc.getInitParameter(GET_TOURS_BY_CUSTOMER_ID_QUERY);
			getBookingDatesByCustomerIdQuery = sc.getInitParameter(GET_BOOKING_DATES_BY_CUSTOMER_ID_QUERY);
			getCountriesFromToursTourtemplatesHotelsAndVendorsQuery = sc.getInitParameter(GET_COUNTRIES_FROM_TOURS_TOURTEMPLATES_HOTELS_AND_VENDORS_QUERY);
			getCitiesFromToursTourtemplatesHotelsAndVendorsQuery = sc.getInitParameter(GET_CITIES_FROM_TOURS_TOURTEMPLATES_HOTELS_AND_VENDORS_QUERY);
			
			getActivityTemplatesByCountryQuery = sc.getInitParameter(GET_ACTIVITY_TEMPLATES_BY_COUNTRRY_QUERY);
			getActivityTemplatesByCityQuery = sc.getInitParameter(GET_ACTIVITY_TEMPLATES_BY_CITY_QUERY);
			
			getToursFromToursAndTourtemplatesByCountryQuery = sc.getInitParameter(GET_TOURS_FROM_TOURS_AND_TOUR_TEMPLATES_BY_COUNTRY_QUERY);
			getToursFromToursAndTourtemplatesByCityQuery = sc.getInitParameter(GET_TOURS_FROM_TOURS_AND_TOUR_TEMPLATES_BY_CITY_QUERY);
			
			getActivityCityAndCountryByTournameQuery = sc.getInitParameter(GET_ACTIVITY_CITY_AND_COUNTRY_BY_TOURNAME_QUERY);
			
			// failed
			
			// itinerary variables
			itineraryHotelCountry = sc.getInitParameter(ITINERARY_HOTEL_COUNTRY);
			itineraryHotelCity = sc.getInitParameter(ITINERARY_HOTEL_CITY);
			itineraryDay = sc.getInitParameter(ITINERARY_DAY);
			itineraryActivity = sc.getInitParameter(ITINERARY_ACTIVITY);
			itineraryAccommodations = sc.getInitParameter(ITINERARY_ACCOMMODATIONS);
			itineraryActivityTemplates = sc.getInitParameter(ITINERARY_ACTIVITY_TEMPLATES);
			itineraryVendors = sc.getInitParameter(ITINERARY_VENDORS);
			
			// button
			itineraryGetItineraryButton = sc.getInitParameter(ITINERARY_GET_ITINERARY_BUTTON);
			itinerarySearchButton = sc.getInitParameter(ITINERARY_SEARCH_BUTTON);
			itineraryGetActivityButton = sc.getInitParameter(ITINERARY_GET_ACTIVITY_BUTTON);
			 
			// message

			
			
			
			
		
			DriverManager.registerDriver(new Driver());
			
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	
	public ArrayList<String> getActivityTemplates(String country, String city)
	{		
		ArrayList<String> returnList = new ArrayList<>();
		
		country = country.trim();
		city = city.trim();
		
		try {				
			conn = DriverManager.getConnection(db_url, db_username, db_password);	
			
			String query = "";

			
			if(city.equals("") && country.equals(""))
				return returnList;
			else if(!city.equals(""))
			{
				query = getActivityTemplatesByCityQuery;
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, city);
				
			}
			else
			{
				query = getActivityTemplatesByCountryQuery;
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, country);
			}
			
				
			rs = pstmt.executeQuery();
			
			while(rs.next())
			{							  
				returnList.add(rs.getString("Tour"));
			}
			
			conn.close();
			pstmt.close();
			rs.close();
		} catch (Exception e) {
			System.err.println(e);
		}	
		
		return returnList;
	}
	
	
	public ArrayList<String> getTourNames(String country, String city)
	{
		ArrayList<String> returnList = new ArrayList<>();
		
		country = country.trim();
		city = city.trim();
		
		try {				
			conn = DriverManager.getConnection(db_url, db_username, db_password);	
			
			String query = "";

			
			if(city.equals("") && country.equals(""))
				return returnList;
			else if(!city.equals(""))
			{
				query = getToursFromToursAndTourtemplatesByCityQuery;
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, city);
				pstmt.setString(2, city);
			}
			else
			{
				query = getToursFromToursAndTourtemplatesByCountryQuery;
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, country);
				pstmt.setString(2, country);
			}
			
				
			rs = pstmt.executeQuery();
			
			while(rs.next())
			{							  
				returnList.add(rs.getString("Tour"));
			}
			
			conn.close();
			pstmt.close();
			rs.close();
		} catch (Exception e) {
			System.err.println(e);
		}	
		
		return returnList;
	}
	
	
	public ArrayList<String> getCountries()
	{
		ArrayList<String> returnList = new ArrayList<>();

		try {				
			conn = DriverManager.getConnection(db_url, db_username, db_password);			
			pstmt = conn.prepareStatement(getCountriesFromToursTourtemplatesHotelsAndVendorsQuery);
			rs = pstmt.executeQuery();
			
			while(rs.next())
			{								  
				returnList.add(rs.getString("Country"));
			}
			conn.close();
			pstmt.close();
			rs.close();
		} catch (Exception e) {
			System.err.println(e);
		}	
		return returnList;
	}
	
	public ArrayList<String> getCities()
	{
		ArrayList<String> returnList = new ArrayList<>();

		try {				
			conn = DriverManager.getConnection(db_url, db_username, db_password);			
			pstmt = conn.prepareStatement(getCitiesFromToursTourtemplatesHotelsAndVendorsQuery);
			rs = pstmt.executeQuery();
			
			while(rs.next())
			{								  
				returnList.add(rs.getString("City"));
			}
			conn.close();
			pstmt.close();
			rs.close();
		} catch (Exception e) {
			System.err.println(e);
		}	
		return returnList;
	}
	
	
	
	
	public ArrayList<String> getActivityCityAndCountryByActivityTemplate(String activityTemplate)
	{
		ArrayList<String> returnList = new ArrayList<>();

		try {				
			conn = DriverManager.getConnection(db_url, db_username, db_password);
			pstmt = conn.prepareStatement(getActivityCityAndCountryByTournameQuery);
			pstmt.setString(1, activityTemplate);
			rs = pstmt.executeQuery();
			
			if(rs.next())
			{								  
				returnList.add(rs.getString("Activity"));
				returnList.add(rs.getString("City"));
				returnList.add(rs.getString("Country"));
			}
			conn.close();
			pstmt.close();
			rs.close();
		} catch (Exception e) {
			System.err.println(e);
		}	
		return returnList;
	}
	
	
	public ArrayList<String> getBookingDatesByCustomerId(String customerId)
	{
		ArrayList<String> returnList = new ArrayList<>();

		try {				
			conn = DriverManager.getConnection(db_url, db_username, db_password);
			pstmt = conn.prepareStatement(getBookingDatesByCustomerIdQuery);
			pstmt.setString(1, customerId);
			rs = pstmt.executeQuery();
			
			if(rs.next())
			{								  
				returnList.add(rs.getString("Departure_Date"));
				returnList.add(rs.getString("Return_Date"));
			}
			conn.close();
			pstmt.close();
			rs.close();
		} catch (Exception e) {
			System.err.println(e);
		}	
		return returnList;
	}
	
	
	// yyyy-mm-dd
	public int daysBetweenDates(String startDate, String endDate)
	{
		LocalDate dateBefore = LocalDate.parse(startDate);
		LocalDate dateAfter = LocalDate.parse(endDate);
		
		long noOfDaysBetween = ChronoUnit.DAYS.between(dateBefore, dateAfter);
		
		return (int)noOfDaysBetween;	
	}
	
	
	public ArrayList<ArrayList<String>> getToursByCustomerId(String customerId)
	{
		ArrayList<ArrayList<String>> returnList = new ArrayList<>();

		try {				
			conn = DriverManager.getConnection(db_url, db_username, db_password);			
			pstmt = conn.prepareStatement(getToursByCustomerIdQuery);
			pstmt.setString(1, customerId);
			rs = pstmt.executeQuery();
			
			while(rs.next())
			{				
				ArrayList<String> temp = new ArrayList<>();
				temp.add(rs.getString("Travel_Date"));
				temp.add(rs.getString("Tour"));
				temp.add(rs.getString("Activity"));
				temp.add(rs.getString("City"));
				temp.add(rs.getString("Country"));
				returnList.add(temp);
			}
			conn.close();
			pstmt.close();
			rs.close();
		} catch (Exception e) {
			System.err.println(e);
		}	
		return returnList;
	}
	

	
	
	// failed
	
	
	
	
	// itinerary variables
	public static String getItineraryHotelCountry()
	{
		return itineraryHotelCountry;
	}
	public static String getItineraryHotelCity()
	{
		return itineraryHotelCity;
	}
	public static String getItineraryDay()
	{
		return itineraryDay;
	}
	public static String getItineraryActivity()
	{
		return itineraryActivity;
	}
	public static String getItineraryAccommodations()
	{
		return itineraryAccommodations;
	}
	public static String getItineraryActivityTemplates()
	{
		return itineraryActivityTemplates;
	}
	public static String getItineraryVendors()
	{
		return itineraryVendors;
	}
	// button
	public static String getItineraryGetItineraryButton()
	{
		return itineraryGetItineraryButton;
	}
	
	public static String getItinerarySearchButton()
	{
		return itinerarySearchButton;
	}
	public static String getItineraryGetActivityButton()
	{
		return itineraryGetActivityButton;
	}
	
	// message

	
}















