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
	public final static String TOUR_LIST = "tour list";
	public final static String HOTEL_LIST = "hotel list";
	
	// db queries
	public final static String GET_TOURS_BY_CUSTOMER_ID_QUERY = "database.get.tours.by.customer.id.query";
	public final static String GET_BOOKING_DATES_BY_CUSTOMER_ID_QUERY = "database.get.booking.dates.by.customer.id.query";
	
	
	// failed
	
	// itinerary variables
	public final static String DATES = "dates";
	public final static String ITINERARY_HOTEL_COUNTRY = "itinerary.hotel.country";
	public final static String ITINERARY_HOTEL_CITY = "itinerary.hotel.city";

	
	// button
	public final static String ITINERARY_GET_ITINERARY_BUTTON = "itinerary.get.itinerary.button";
	
	// message

	// hotel variables
	public final static String HOTELS = "Hotels";
	
	
	
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
	
	// failed
	
	// itinerary varibales
	private static String itineraryHotelCountry;
	private static String itineraryHotelCity;

	// button
	private static String itineraryGetItineraryButton;
	
	
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
			
			// failed
			
			// itinerary variables
			itineraryHotelCountry = sc.getInitParameter(ITINERARY_HOTEL_COUNTRY);
			itineraryHotelCity  = sc.getInitParameter(ITINERARY_HOTEL_CITY);
			
			
			// button
			itineraryGetItineraryButton = sc.getInitParameter(ITINERARY_GET_ITINERARY_BUTTON);

			
			// message

			
			
			
			
		
			DriverManager.registerDriver(new Driver());
			
		} catch (Exception e) {
			System.err.println(e);
		}
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
				temp.add(rs.getString("Vendor"));
				temp.add(rs.getString("Hotel"));
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
	// button
	public static String getItineraryHotelCity()
	{
		return itineraryHotelCity;
	}

	
	// button
	public static String getItineraryGetItineraryButton()
	{
		return itineraryGetItineraryButton;
	}
	
	// message

	
}















