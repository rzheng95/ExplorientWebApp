/*
 * This file is part of Explorient Web App
 * Copyright (C) 2016-2019 Richard R. Zheng
 *
 * https://github.com/rzheng95/ExplorientWebApp
 * 
 * All Right Reserved.
 */

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
	public final static String GET_AGENTS_QUERY = "database.get.agents.query";
	public final static String GET_AGENT_QUERY = "database.get.agent.query";
	public final static String CHECK_AGENT_QUERY = "database.check.agent.query";
	public final static String ADD_BOOKING_QUERY = "database.add.booking.query";
	public final static String GET_AIR_QUERY = "database.get.air.query";
	public final static String GET_TOUR_PACKAGE_QUERY = "database.get.tour.package.query";
	public final static String CHECK_CUSTOMER_ID_QUERY = "database.check.customer.id.query";
	public final static String GET_BOOKING_INFO_BY_CUSTOMER_ID_QUERY = "database.get.booking.info.by.customer.id.query";
	public final static String UPDATE_BOOKING_BY_CUSTOMER_ID_QUERY = "database.update.booking.by.customer.id.query";

	
	
	// New Page
	public final static int NEW_ENTERED_VALUE_LENGTH = 12;
	public final static int AGENT_VALUE_INDEX = 0;
	public final static int CUSTOMER_ID_VALUE_INDEX = 1;
	public final static int DESTINATION_VALUE_INDEX = 2;
	public final static int Air_VALUE_INDEX = 3;
	public final static int DATE_OF_DEPARTURE_VALUE_INDEX = 4;
	public final static int DATE_OF_RETURN_VALUE_INDEX = 5;
	public final static int COUNTRY_INDEX = 6;
	public final static int TOUR_PACKAGE_VALUE_INDEX = 7;
	public final static int SEARCH_BOX_LAST_NAME_VALUE_INDEX = 8;
	public final static int SEARCH_BOX_DEPARTURE_DATE_FROM_VALUE_INDEX = 9;
	public final static int SEARCH_BOX_DEPARTURE_DATE_TO_VALUE_INDEX = 10;
	public final static int SEARCH_BOX_CUSTOMER_ID_VALUE_INDEX = 11;
	public final static String NEW = "New";
	public final static String NEWPAGE = "newpage";
	public final static String NEW_TOUR_PACKAGES = "tour packages";
	public final static String NEW_AGENT = "new.agent";
	public final static String NEW_CUSTOMER_ID = "new.customer.id";
	public final static String NEW_SEARCH_BOX_CUSTOMER_ID = "new.search.box.customer.id";
	public final static String NEW_DESTINATION = "new.destination";
	public final static String NEW_AIR = "new.air";
	public final static String NEW_DATE_OF_DEPARTURE = "new.date.of.departure";
	public final static String NEW_DATE_OF_RETURN = "new.date.of.return"; 
	public final static String NEW_COUNTRY = "new.country";
	public final static String NEW_GET_PACKAGES = "new.get.packages.button"; 
	public final static String NEW_GET_BOOKING_BUTTON = "new.get.booking.button"; 
	public final static String NEW_CREATE_BUTTON = "new.create.button"; 
	public final static String NEW_UPDATE_BUTTON = "new.update.button"; 
	public final static String NEW_CLEAR_BUTTON = "new.clear.button"; 
	
	
	public final static String NEW_TOUR_PACKAGE = "new.tour.package";
	public final static String COUNTRIES = "new.countries";
	
	
	// failed messages
	public final static String NEWPAGE_FAILED = "newpage.failed";
	public final static String AGENT_NOT_FOUND_FAILED = "new.agent.not.found.failed";
	public final static String EQUAL_SIGN_FAILED = "new.equal.sign.failed";
	public final static String EMPTY_FIELD_FAILED = "new.empty.field.failed";
	public final static String CUSTOMER_ID_EXISTS_FAILED = "new.customer.id.exists.failed";
	public final static String CUSTOMER_ID_DOES_NOT_EXIST_FAILED = "new.customer.id.does.not.exist.failed";
	public final static String CUSTOMER_ID_INVALID_FAILED = "new.customer.id.invalid.failed";
	public final static String DATE_FORMAT_INVALID_FAILED = "new.date.format.invalid.failed";
	
	public final static String BOOKING_UPDATED_MESSAGE = "new.booking.updated.message";
	
	
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
	private static String getAgentsQuery;
	private static String getAgentQuery;
	private static String checkAgentQuery;
	private static String addBookingQuery;
	private static String getAirQuery;
	private static String getTourPackageQuery;
	private static String checkCustomerIdQuery;
	private static String getBookingInfoByCustomerIdQuery;
	private static String updateBookingByCustomerIdQuery;
	
	// New Page
	private static String newAgent;
	private static String newCustomerId;
	private static String newSearchBoxCustomerId;
	private static String newDestination;
	private static String newAir;
	private static String newDateOfDeparture;
	private static String newDateOfReturn;
	private static String newCountry;
	private static String newGetPackages;
	private static String newTourPackage;
	private static String countries;
	private static String newGetBookingButton;
	private static String newCreateButton;
	private static String newUpdateButton;
	private static String newClearButton;

	
	// failed messages
	private static String agentNotFoundFailed;
	private static String equalSignFailed;
	private static String emptydFieldFailed;
	private static String customerIdExistsFailed;
	private static String customerIdDoesNotExistFailed;
	private static String customerIdInvalidFailed;
	private static String dateFormatInvalidFailed;
	
	private static String bookingUpdatedMessage;
	
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
			getAgentsQuery = sc.getInitParameter(GET_AGENTS_QUERY);
			getAgentQuery = sc.getInitParameter(GET_AGENT_QUERY);
			checkAgentQuery = sc.getInitParameter(CHECK_AGENT_QUERY);
			addBookingQuery = sc.getInitParameter(ADD_BOOKING_QUERY);
			getAirQuery = sc.getInitParameter(GET_AIR_QUERY);
			getTourPackageQuery = sc.getInitParameter(GET_TOUR_PACKAGE_QUERY);
			checkCustomerIdQuery = sc.getInitParameter(CHECK_CUSTOMER_ID_QUERY);
			getBookingInfoByCustomerIdQuery = sc.getInitParameter(GET_BOOKING_INFO_BY_CUSTOMER_ID_QUERY);
			updateBookingByCustomerIdQuery = sc.getInitParameter(UPDATE_BOOKING_BY_CUSTOMER_ID_QUERY);
						
			// New Page	
			newAgent = sc.getInitParameter(NEW_AGENT);
			newCustomerId = sc.getInitParameter(NEW_CUSTOMER_ID);
			newSearchBoxCustomerId = sc.getInitParameter(NEW_SEARCH_BOX_CUSTOMER_ID);
			newDestination = sc.getInitParameter(NEW_DESTINATION);
			newAir = sc.getInitParameter(NEW_AIR);
			newDateOfDeparture = sc.getInitParameter(NEW_DATE_OF_DEPARTURE);
			newDateOfReturn = sc.getInitParameter(NEW_DATE_OF_RETURN);
			newCountry = sc.getInitParameter(NEW_COUNTRY);
			newGetPackages = sc.getInitParameter(NEW_GET_PACKAGES);
			newTourPackage = sc.getInitParameter(NEW_TOUR_PACKAGE);
			countries = sc.getInitParameter(COUNTRIES);
			newGetBookingButton = sc.getInitParameter(NEW_GET_BOOKING_BUTTON);
			newCreateButton = sc.getInitParameter(NEW_CREATE_BUTTON);
			newUpdateButton = sc.getInitParameter(NEW_UPDATE_BUTTON);
			newClearButton = sc.getInitParameter(NEW_CLEAR_BUTTON);
			
			
			// failed messages
			agentNotFoundFailed = sc.getInitParameter(AGENT_NOT_FOUND_FAILED);
			equalSignFailed = sc.getInitParameter(EQUAL_SIGN_FAILED);
			emptydFieldFailed = sc.getInitParameter(EMPTY_FIELD_FAILED);
			customerIdExistsFailed = sc.getInitParameter(CUSTOMER_ID_EXISTS_FAILED);
			customerIdDoesNotExistFailed = sc.getInitParameter(CUSTOMER_ID_DOES_NOT_EXIST_FAILED);
			customerIdInvalidFailed = sc.getInitParameter(CUSTOMER_ID_INVALID_FAILED);
			dateFormatInvalidFailed = sc.getInitParameter(DATE_FORMAT_INVALID_FAILED);
			
			bookingUpdatedMessage = sc.getInitParameter(BOOKING_UPDATED_MESSAGE);
			
			
			// BookingCreated
			addPassenger = sc.getInitParameter(BC_ADD_PASSENGER);
			addShippingAddress = sc.getInitParameter(BC_ADD_SHIPPING_ADDRESS);
			updateBooking = sc.getInitParameter(BC_UPDATE_BOOKING);
		
			DriverManager.registerDriver(new Driver());
			
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	
	public ArrayList<String> getAgent(String agentName)
	{
		ArrayList<String> returnList = new ArrayList<>();
		try {				
			conn = DriverManager.getConnection(db_url, db_username, db_password);	
			
			pstmt = conn.prepareStatement(getAgentQuery);
			pstmt.setString(1, agentName);

			rs = pstmt.executeQuery();
			
			while(rs.next())
			{		
				returnList.add(rs.getString("Agent"));
				returnList.add(rs.getString("Lastname"));
				returnList.add(rs.getString("Firstname"));
				returnList.add(rs.getString("Address"));
				returnList.add(rs.getString("City"));
				returnList.add(rs.getString("State"));
				returnList.add(rs.getString("Country"));
				returnList.add(rs.getString("Telephone_1"));
				returnList.add(rs.getString("Telephone_2"));
				returnList.add(rs.getString("Fax"));
				returnList.add(rs.getString("Email_1"));
				returnList.add(rs.getString("Email_2"));
				returnList.add(rs.getString("Website"));
			}

			conn.close();
			pstmt.close();
			rs.close();
		} catch (Exception e) {
			System.err.println(e);
		}
		return returnList;
	}
	

	public void updateBooking(String agent, String destination, String tourPackage, String air, String dateOfDeparture, String dateOfReturn, String customerId)
	{
		if(agent.contains(DASH))
		{
			String[] arr = agent.split(DASH);
			if(arr.length == 2)
			{
				agent = arr[0].trim();
			}
		}	
		
		try {				
			conn = DriverManager.getConnection(db_url, db_username, db_password);	
			
			pstmt = conn.prepareStatement(updateBookingByCustomerIdQuery);
			pstmt.setString(1, agent);
			pstmt.setString(2, destination);
			pstmt.setString(3, tourPackage);
			pstmt.setString(4, air);
			pstmt.setString(5, dateOfDeparture);
			pstmt.setString(6, dateOfReturn);
			pstmt.setString(7, customerId);
			rs = pstmt.executeQuery();

			conn.close();
			pstmt.close();
			rs.close();
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	
	
	
	public ArrayList<String> getBookingInfoByCustomerId(String customerId)
	{
		ArrayList<String> returnList = new ArrayList<>();
		try {				
			conn = DriverManager.getConnection(db_url, db_username, db_password);	
			
			pstmt = conn.prepareStatement(getBookingInfoByCustomerIdQuery);
			pstmt.setString(1, customerId);

			rs = pstmt.executeQuery();

			while(rs.next())
			{			
				returnList.add(rs.getString("Agent"));
				returnList.add(rs.getString("Customer_Id"));
				returnList.add(rs.getString("Destination"));
				returnList.add(rs.getString("Tour_Package"));
				returnList.add(rs.getString("Air"));
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


	// MONTH-0 DAY-1 YEAR-2
	// mm/dd/yyyy to yyyy-mm-dd
	public String mmddyyyy_To_yyyymmdd(String date)
	{
		// month-0 day-1 year-2
		String[] temp = date.split(FORWARD_SLASH);
		if(temp.length == DATE_LENGTH)
			date = temp[YEAR]+DASH+temp[MONTH]+DASH+temp[DAY];

		return date;
	}
	
	// MONTH-0 DAY-1 YEAR-2
	// yyyy-mm-dd to mm/dd/yyyy
	public String yyyymmdd_To_mmddyyy(String date)
	{
		// year-0 month-1 day-2
		String[] temp = date.split(DASH);
		if(temp.length == DATE_LENGTH)
			date = temp[DAY]+FORWARD_SLASH+temp[YEAR]+FORWARD_SLASH+temp[MONTH];

		return date;
	}
	
	public boolean checkDateFormat(String date)
	{
		if(date != null && date.contains(FORWARD_SLASH))
		{
			String[] temp = date.split(FORWARD_SLASH);
			if(temp.length == DATE_LENGTH)
			{
				for(String i : temp)
					if(i.trim().isEmpty())
						return false;
				
				
				return true;
			}
		}
		return false;
	}
	
	
	// mm/dd/yyyy with mm/dd/yyy
	public boolean compareDates(String dop, String dor)
	{
		if(checkDateFormat(dop) && checkDateFormat(dor))
		{
			String[] tempDop = dop.split(FORWARD_SLASH);
			String[] tempDor = dor.split(FORWARD_SLASH);
			
			// 08/21/2017  // 09/01/2017
			if(tempDop.length == DATE_LENGTH && tempDor.length == DATE_LENGTH)
			{
				int dopYear = Integer.parseInt(tempDop[YEAR].trim());
				int dorYear = Integer.parseInt(tempDor[YEAR].trim());
				
				int dopMonth = Integer.parseInt(tempDop[MONTH].trim());
				int dorMonth = Integer.parseInt(tempDor[MONTH].trim());
				
				int dopDay = Integer.parseInt(tempDop[DAY].trim());
				int dorDay = Integer.parseInt(tempDor[DAY].trim());
				
				if(dopYear < dorYear) 
					return true;
				else if(dopYear > dorYear)
					return false;
				else // same year
				{
					if(dopMonth < dorMonth) 
						return true;
					else if(dopMonth > dorMonth)
						return false;
					else // same month
					{
						if(dopDay < dorDay) 
							return true;
						else if(dopDay > dorDay)
							return false;
						else // same day
						{
							return false;
						}
					}
				}
				
				
			}
		}
			
		return false;
	}
	
	
	public ArrayList<String> getCountries()
	{
		ArrayList<String> returnList = new ArrayList<>();
		if(countries.contains("-"))
		{
			String[] fragment = countries.split("-", -1);
			for(String i : fragment)
				returnList.add(i);		
		}
		return returnList;
	}
	
	
	public void addBooking(String agent, String customerId, String destination, String air, String dateOfDeparture, String dateOfReturn, String tourPackage)
	{
		if(agent.contains(DASH))
		{
			String[] arr = agent.split(DASH);
			if(arr.length == 2)
			{
				agent = arr[0].trim();
			}
		}	
		
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
	
	public ArrayList<String> getTourPackages(String text)
	{			
		ArrayList<String> returnList = new ArrayList<>();
		try {				
			conn = DriverManager.getConnection(db_url, db_username, db_password);			
			pstmt = conn.prepareStatement(getTourPackageQuery);
			pstmt.setString(1, text);
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
	
	public ArrayList<String> getAgents()
	{			
		ArrayList<String> returnList = new ArrayList<>();
		try {				
			conn = DriverManager.getConnection(db_url, db_username, db_password);	
			
			pstmt = conn.prepareStatement(getAgentsQuery);
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
	public static String getSearchBoxNewCustomerId() 
	{
		return newSearchBoxCustomerId;
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
	public static String getCountry() 
	{
		return newCountry;
	}
	public static String getGetPackages() 
	{
		return newGetPackages;
	}
	public static String getNewTourPackage() 
	{
		return newTourPackage;
	}
	public static String getNewGetBookingButton() 
	{
		return newGetBookingButton;
	}
	public static String getNewCreateButton() 
	{
		return newCreateButton;
	}
	public static String getNewUpdateButton() 
	{
		return newUpdateButton;
	}
	public static String getNewClearButton() 
	{
		return newClearButton;
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
	public static String getCustomerIdDoesNotExistFailed() 
	{
		return customerIdDoesNotExistFailed;
	}
	public static String getCustomerIdInvalidFailed() 
	{
		return customerIdInvalidFailed;
	}
	public static String getDateFormatInvalidFailed() 
	{
		return dateFormatInvalidFailed;
	}
	public static String getBookingUpdatedMessage() 
	{
		return bookingUpdatedMessage;
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















