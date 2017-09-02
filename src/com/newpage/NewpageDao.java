package com.newpage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import org.mariadb.jdbc.Driver;


public class NewpageDao extends HttpServlet
{	
	public final static int DATE_LENGTH = 3;
	public final static int YEAR = 2;
	public final static int DAY = 1;
	public final static int MONTH = 0;
	public final static String FORWARD_SLASH = "/";
	public final static String EQUAL = "=";
	public final static String COMMA = ",";
	public final static String DASH = "-";
	
	
	// db connection
	public final static String DB_URL = "database.url";
	public final static String DB_USERNAME = "database.username";
	public final static String DB_PASSWORD = "database.password";
	
	// db queries
	public final static String GET_AGENT_QUERY = "database.get.agent.query";
	public final static String CHECK_AGENT_QUERY = "database.check.agent.query";
	public final static String ADD_BOOKING_QUERY = "database.add.booking.query";
	public final static String GET_DESTINATION_QUERY = "database.get.destination.query";
	public final static String GET_AIR_QUERY = "database.get.air.query";
	public final static String GET_TOUR_PACKAGE_QUERY = "database.get.tour.package.query";
	public final static String CHECK_CUSTOMER_ID_QUERY = "database.check.customer.id.query";
	
	
	// New Page
	public final static int NEW_ENTERED_VALUE_LENGTH = 7;
	public final static int AGENT_VALUE_INDEX = 0;
	public final static int CUSTOMER_ID_VALUE_INDEX = 1;
	public final static int DESTINATION_VALUE_INDEX = 2;
	public final static int Air_VALUE_INDEX = 3;
	public final static int DATE_OF_DEPARTURE_VALUE_INDEX = 4;
	public final static int DATE_OF_RETURN_VALUE_INDEX = 5;
	public final static int TOUR_PACKAGE_VALUE_INDEX = 6;
	public final static String NEWPAGE = "newpage";
	public final static String NEW_AGENT = "new.agent";
	public final static String NEW_CUSTOMER_ID = "new.customer.id";
	public final static String NEW_DESTINATION = "new.destination";
	public final static String NEW_AIR = "new.air";
	public final static String NEW_DATE_OF_DEPARTURE = "new.date.of.departure";
	public final static String NEW_DATE_OF_RETURN = "new.date.of.return"; 
	public final static String NEW_TOUR_PACKAGE = "new.tour.package";
	
	// failed messages
	public final static String NEWPAGE_FAILED = "newpage.failed";
	public final static String AGENT_NOT_FOUND_FAILED = "new.agent.not.found.failed";
	public final static String EQUAL_SIGN_FAILED = "new.equal.sign.failed";
	public final static String EMPTY_FIELD_FAILED = "new.empty.field.failed";
	public final static String CUSTOMER_ID_EXISTS_FAILED = "new.customer.id.exists.failed";
	public final static String CUSTOMER_ID_INVALID_FAILED = "new.customer.id.invalid.failed";
	public final static String DATE_FORMAT_INVALID_FAILED = "new.date.format.invalid.failed";
	
	
	// BookingCreated 
	public final static String BC_ADD_PASSENGER = "BookingCreated.add.passenger";
	public final static String BC_ADD_SHIPPING_ADDRESS = "BookingCreated.add.shipping.address";
	public final static String BC_UPDATE_BOOKING = "BookingCreated.update.booking";
	
	
	// db connection
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs;
	private static String db_username;
	private static String db_password;
	private static String db_url;
	
	// db queries
	private static String getAgentQuery;
	private static String checkAgentQuery;
	private static String addBookingQuery;
	private static String getDestinationQuery;
	private static String getAirQuery;
	private static String getTourPackageQuery;
	private static String checkCustomerIdQuery;
	
	// New Page
	private static String newAgent;
	private static String newCustomerId;
	private static String newDestination;
	private static String newAir;
	private static String newDateOfDeparture;
	private static String newDateOfReturn;
	private static String newTourPackage;
	
	// failed messages
	private static String agentNotFoundFailed;
	private static String equalSignFailed;
	private static String emptydFieldFailed;
	private static String customerIdExistsFailed;
	private static String customerIdInvalidFailed;
	private static String dateFormatInvalidFailed;
	
	// BookingCreated
	private static String addPassenger;
	private static String addShippingAddress;
	private static String updateBooking;
	private static HashMap<String, String> map;
	
	public void init()
	{
	
		try {
			ServletContext sc = this.getServletContext();
			
			map = new HashMap<String, String>();
			
			// db connection
			db_username = sc.getInitParameter(DB_USERNAME);
			db_password = sc.getInitParameter(DB_PASSWORD);
			db_url = sc.getInitParameter(DB_URL);
			
			
			// db queries
			getAgentQuery = sc.getInitParameter(GET_AGENT_QUERY);
			checkAgentQuery = sc.getInitParameter(CHECK_AGENT_QUERY);
			addBookingQuery = sc.getInitParameter(ADD_BOOKING_QUERY);
			getDestinationQuery = sc.getInitParameter(GET_DESTINATION_QUERY);
			getAirQuery = sc.getInitParameter(GET_AIR_QUERY);
			getTourPackageQuery = sc.getInitParameter(GET_TOUR_PACKAGE_QUERY);
			checkCustomerIdQuery = sc.getInitParameter(CHECK_CUSTOMER_ID_QUERY);
			
						
			// New Page	
			newAgent = sc.getInitParameter(NEW_AGENT);
			newCustomerId = sc.getInitParameter(NEW_CUSTOMER_ID);
			newDestination = sc.getInitParameter(NEW_DESTINATION);
			newAir = sc.getInitParameter(NEW_AIR);
			newDateOfDeparture = sc.getInitParameter(NEW_DATE_OF_DEPARTURE);
			newDateOfReturn = sc.getInitParameter(NEW_DATE_OF_RETURN);
			newTourPackage = sc.getInitParameter(NEW_TOUR_PACKAGE);
			
			
			// failed messages
			agentNotFoundFailed = sc.getInitParameter(AGENT_NOT_FOUND_FAILED);
			equalSignFailed = sc.getInitParameter(EQUAL_SIGN_FAILED);
			emptydFieldFailed = sc.getInitParameter(EMPTY_FIELD_FAILED);
			customerIdExistsFailed = sc.getInitParameter(CUSTOMER_ID_EXISTS_FAILED);
			customerIdInvalidFailed = sc.getInitParameter(CUSTOMER_ID_INVALID_FAILED);
			dateFormatInvalidFailed = sc.getInitParameter(DATE_FORMAT_INVALID_FAILED);
			
			
			// BookingCreated
			addPassenger = sc.getInitParameter(BC_ADD_PASSENGER);
			addShippingAddress = sc.getInitParameter(BC_ADD_SHIPPING_ADDRESS);
			updateBooking = sc.getInitParameter(BC_UPDATE_BOOKING);
		
			DriverManager.registerDriver(new Driver());
			
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	public void addBooking(String agent, String customerId, String destination, String air, String dateOfDeparture, String dateOfReturn, String tourPackage)
	{
		try {				
			conn = DriverManager.getConnection(db_url, db_username, db_password);	
			
			pstmt = conn.prepareStatement(addBookingQuery);
			pstmt.setString(1, agent);
			pstmt.setString(2, customerId);
			pstmt.setString(3, destination);
			pstmt.setString(4, air);
			pstmt.setString(5, dateOfDeparture);
			pstmt.setString(6, dateOfReturn);
			pstmt.setString(7, tourPackage);
			rs = pstmt.executeQuery();

			conn.close();
			pstmt.close();
			rs.close();
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	
	public boolean checkCustomerId(String customerId)
	{			
		if(customerId == null) return false;
		try {				
			conn = DriverManager.getConnection(db_url, db_username, db_password);	
			
			pstmt = conn.prepareStatement(checkCustomerIdQuery);
			pstmt.setString(1, customerId);

			rs = pstmt.executeQuery();
			
			if(rs.next()) return true;

			conn.close();
			pstmt.close();
			rs.close();
		} catch (Exception e) {
			System.err.println(e);
		}
		return false;
	}
	
	
	public boolean checkAgent(String text)
	{			
		String agent = "", firstname = "", lastname = "";
		
		String[] arr = text.split(DASH);
		if(arr.length == 2)
		{
			agent = arr[0].trim();
			String[] arr2 = arr[1].split(COMMA);
			if(arr2.length == 2)
			{
				lastname = arr2[0].trim();
				firstname = arr2[1].trim();
			}
		}
		
		try {				
			conn = DriverManager.getConnection(db_url, db_username, db_password);	
			
			pstmt = conn.prepareStatement(checkAgentQuery);
			pstmt.setString(1, agent);
			pstmt.setString(2, firstname);
			pstmt.setString(3, lastname);
			rs = pstmt.executeQuery();
			
			if(rs.next()) return true;

			conn.close();
			pstmt.close();
			rs.close();
		} catch (Exception e) {
			System.err.println(e);
		}
		return false;
	}
	
	public ArrayList<String> getDestination()
	{			
		ArrayList<String> returnList = new ArrayList<>();
		try {				
			conn = DriverManager.getConnection(db_url, db_username, db_password);			
			pstmt = conn.prepareStatement(getDestinationQuery);
			rs = pstmt.executeQuery();
			
			while(rs.next())
			{			
				returnList.add(rs.getString("Destination"));
			}
			conn.close();
			pstmt.close();
			rs.close();
		} catch (Exception e) {
			System.err.println(e);
		}	
		return returnList;
	}
	
	public ArrayList<String> getAir()
	{			
		ArrayList<String> returnList = new ArrayList<>();
		try {				
			conn = DriverManager.getConnection(db_url, db_username, db_password);			
			pstmt = conn.prepareStatement(getAirQuery);
			rs = pstmt.executeQuery();
			
			while(rs.next())
			{			
				returnList.add(rs.getString("Air"));
			}
			conn.close();
			pstmt.close();
			rs.close();
		} catch (Exception e) {
			System.err.println(e);
		}	
		return returnList;
	}
	
	public ArrayList<String> getTourPackage()
	{			
		ArrayList<String> returnList = new ArrayList<>();
		try {				
			conn = DriverManager.getConnection(db_url, db_username, db_password);			
			pstmt = conn.prepareStatement(getTourPackageQuery);
			rs = pstmt.executeQuery();
			
			while(rs.next())
			{			
				returnList.add(rs.getString("Tour_Package"));
			}
			conn.close();
			pstmt.close();
			rs.close();
		} catch (Exception e) {
			System.err.println(e);
		}	
		return returnList;
	}
	
	public ArrayList<String> getAgent()
	{			
		ArrayList<String> returnList = new ArrayList<>();
		try {				
			conn = DriverManager.getConnection(db_url, db_username, db_password);	
			
			pstmt = conn.prepareStatement(getAgentQuery);
			rs = pstmt.executeQuery();
			
			while(rs.next())
			{
				String temp = "";
				temp += rs.getString("Agent");
				temp += " - ";
				temp += rs.getString("Lastname");
				temp += ", ";
				temp += rs.getString("Firstname");
				
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
	
	public static HashMap<String, String> getHashMap()
	{
		return map;
	}
	
	// New Page
	public static String getNewAgent() 
	{
		return newAgent;
	}
	public static String getNewCustomerId() 
	{
		return newCustomerId;
	}
	public static String getNewDestination() 
	{
		return newDestination;
	}
	public static String getNewAir() 
	{
		return newAir;
	}
	public static String getNewDateOfDeparture() 
	{
		return newDateOfDeparture;
	}
	public static String getNewDateOfReturn() 
	{
		return newDateOfReturn;
	}
	public static String getNewTourPackage() 
	{
		return newTourPackage;
	}
	
	
	// failed messages
	public static String getAgentNotFoundFailed() 
	{
		return agentNotFoundFailed;
	}
	public static String getEqualSignFailed() 
	{
		return equalSignFailed;
	}
	public static String getEmptydFieldFailed() 
	{
		return emptydFieldFailed;
	}
	public static String getCustomerIdExistsFailed() 
	{
		return customerIdExistsFailed;
	}
	public static String getCustomerIdInvalidFailed() 
	{
		return customerIdInvalidFailed;
	}
	public static String getDateFormatInvalidFailed() 
	{
		return dateFormatInvalidFailed;
	}
	
	
	// BookingCreated
	public static String getAddPassenger() 
	{
		return addPassenger;
	}
	public static String getAddShippingAddress() 
	{
		return addShippingAddress;
	}
	public static String getUpdateBooking() 
	{
		return updateBooking;
	}
	
}















