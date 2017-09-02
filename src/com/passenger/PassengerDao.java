package com.passenger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import org.mariadb.jdbc.Driver;
import com.newpage.NewpageDao;

public class PassengerDao extends HttpServlet
{			
	// db connection
	public final static String DB_URL = "database.url";
	public final static String DB_USERNAME = "database.username";
	public final static String DB_PASSWORD = "database.password";
	
	// db queries
	public final static String GET_CUSTOMER_ID_QUERY = "database.get.customer.id.query";
	public final static String ADD_PASSENGER_QUERY = "database.add.passenger.query";
	public final static String GET_PASSENGER_QUERY = "database.get.passenger.query";
	public final static String CHECK_DUPLICATE_PASSENGER_QUERY = "database.check.duplicate.passenger.query";
	public final static String DELETE_PASSENGER_QUERY = "database.delete.passenger.query";
	
	// failed
	public final static String PASSENGER_FAILED = "passenger.failed";
	public final static String PASSENGER_CUSTOMER_ID_INVALID_FAILED = "passenger.customer.id.invalid.failed";
	public final static String PASSENGER_EMPTY_FIELD_FAILED = "passenger.empty.field.failed";
	public final static String PASSENGER_ALREADY_EXISTS_FAILED = "passenger.already.exists.failed";
	public final static String PASSENGER_DOES_NOT_EXIST_FAILED = "passenger.does.not.exist.failed";
	
	
	// message
	public final static String PASSENGER_ADDED = "passenger.added";
	public final static String PASSENGER_DELETED = "passenger.deleted";
	
	
	
	public final static String PASSENGER = "passenger";
	public final static String PASSENGER_LIST = "passengerList";
	public final static String PASSENGER_TITLE = "passenger.title";
	public final static String PASSENGER_MIDDLENAME = "passenger.middlename";
	public final static int PASSENGER_ENTERED_VALUE_LENGTH = 5;
	public final static int CUSTOMER_ID_INDEX = 0;
	public final static int TITLE_INDEX = 1;
	public final static int FIRST_NAME_INDEX = 2;
	public final static int MIDDLE_NAME_INDEX = 3;
	public final static int LAST_NAME_INDEX = 4;
	public final static String EQUAL = NewpageDao.EQUAL;
	
	
	
	
	// db connection
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs;
	private static String db_username;
	private static String db_password;
	private static String db_url;
	
	private NewpageDao npdao;
	
	// db queries
	private static String getCustomerIdQuery;
	private static String addPassengerQuery;
	private static String getPassengerQuery;
	private static String checkDuplicatePassengerQuery;
	private static String deletePassengerQuery;
	
	// failed
	private static String passengerCustomerIdInvalidFailed;
	private static String passengerEmptyFieldFailed;
	private static String passengerAlreadyExistsFailed;
	private static String passengerDoesNotExistFailed;
	
	// message
	private static String passengerAdded;
	private static String passengerDeleted;
	
	
	
	private static String passengerTitle;
	private static String passengerMiddlename;
	
	
	public void init()
	{
		
		try {
			ServletContext sc = this.getServletContext();
			npdao = new NewpageDao();
			
			// db connection
			db_username = sc.getInitParameter(DB_USERNAME);
			db_password = sc.getInitParameter(DB_PASSWORD);
			db_url = sc.getInitParameter(DB_URL);
			
			
			// db queries
			getCustomerIdQuery = sc.getInitParameter(GET_CUSTOMER_ID_QUERY);
			addPassengerQuery = sc.getInitParameter(ADD_PASSENGER_QUERY);
			getPassengerQuery = sc.getInitParameter(GET_PASSENGER_QUERY);
			checkDuplicatePassengerQuery  = sc.getInitParameter(CHECK_DUPLICATE_PASSENGER_QUERY);
			deletePassengerQuery = sc.getInitParameter(DELETE_PASSENGER_QUERY);
			
			// failed
			passengerCustomerIdInvalidFailed = sc.getInitParameter(PASSENGER_CUSTOMER_ID_INVALID_FAILED);
			passengerEmptyFieldFailed = sc.getInitParameter(PASSENGER_EMPTY_FIELD_FAILED);
			passengerAlreadyExistsFailed = sc.getInitParameter(PASSENGER_ALREADY_EXISTS_FAILED);
			passengerDoesNotExistFailed = sc.getInitParameter(PASSENGER_DOES_NOT_EXIST_FAILED);
			
			// message
			passengerAdded  = sc.getInitParameter(PASSENGER_ADDED);
			passengerDeleted = sc.getInitParameter(PASSENGER_DELETED);
			
			
			passengerTitle = sc.getInitParameter(PASSENGER_TITLE);
			passengerMiddlename = sc.getInitParameter(PASSENGER_MIDDLENAME);
			
			
			
		
			DriverManager.registerDriver(new Driver());
			
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	
	public void deletePassenger(String customerId, String title, String firstname, String middlename, String lastname)
	{		
		if(customerId == null && !npdao.checkCustomerId(customerId)) return;
			
		try {				
			conn = DriverManager.getConnection(db_url, db_username, db_password);			
			pstmt = conn.prepareStatement(deletePassengerQuery);
			pstmt.setString(1, customerId);
			pstmt.setString(2, title);
			pstmt.setString(3, firstname);
			pstmt.setString(4, middlename);
			pstmt.setString(5, lastname);
			rs = pstmt.executeQuery();
			
			conn.close();
			pstmt.close();
			rs.close();
		} catch (Exception e) {
			System.err.println(e);
		}	
	}
	
	public boolean checkDuplicatePassenger(String customerId, String title, String firstname, String middlename, String lastname)
	{
		try {				
			conn = DriverManager.getConnection(db_url, db_username, db_password);			
			pstmt = conn.prepareStatement(checkDuplicatePassengerQuery);
			pstmt.setString(1, customerId);
			pstmt.setString(2, title);
			pstmt.setString(3, firstname);
			pstmt.setString(4, middlename);
			pstmt.setString(5, lastname);
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
	
	public void addPassenger(String customerId, String title, String firstname, String middlename, String lastname)
	{		
		if(customerId == null && !npdao.checkCustomerId(customerId)) return;
			
		try {				
			conn = DriverManager.getConnection(db_url, db_username, db_password);			
			pstmt = conn.prepareStatement(addPassengerQuery);
			pstmt.setString(1, customerId);
			pstmt.setString(2, title);
			pstmt.setString(3, firstname);
			pstmt.setString(4, middlename);
			pstmt.setString(5, lastname);
			rs = pstmt.executeQuery();
			
			conn.close();
			pstmt.close();
			rs.close();
		} catch (Exception e) {
			System.err.println(e);
		}	
	}
	
	
	public ArrayList<String> getPassenger(String customerId)
	{			
		ArrayList<String> returnList = new ArrayList<>();
		if(customerId == null || customerId.trim().isEmpty()) return returnList;
		try {				
			conn = DriverManager.getConnection(db_url, db_username, db_password);			
			pstmt = conn.prepareStatement(getPassengerQuery);
			pstmt.setString(1, customerId);
			rs = pstmt.executeQuery();
			
			while(rs.next())
			{			
				String temp = rs.getString("Title")+NewpageDao.EQUAL+
							  rs.getString("Firstname")+NewpageDao.EQUAL+
							  rs.getString("Middlename")+NewpageDao.EQUAL+
							  rs.getString("Lastname");
							  
							  
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
	
	public ArrayList<String> getCustomerIds()
	{			
		ArrayList<String> returnList = new ArrayList<>();
		try {				
			conn = DriverManager.getConnection(db_url, db_username, db_password);			
			pstmt = conn.prepareStatement(getCustomerIdQuery);
			rs = pstmt.executeQuery();
			
			while(rs.next())
			{			
				returnList.add(rs.getString("Customer_Id"));
			}
			conn.close();
			pstmt.close();
			rs.close();
		} catch (Exception e) {
			System.err.println(e);
		}	
		return returnList;
	}
	
	public static String getPassengerTitle()
	{
		return passengerTitle;
	}
	
	public static String getPassengerMiddlename()
	{
		return passengerMiddlename;
	}
	
	
	// failed
	public static String getPassengerCustomerIdInvalidFailed()
	{
		return passengerCustomerIdInvalidFailed;
	}
	public static String getPassengerEmptyFieldFailed()
	{
		return passengerEmptyFieldFailed;
	}
	public static String getPassengerAlreadyExistsFailed()
	{
		return passengerAlreadyExistsFailed;
	}
	public static String getPassengerDoesNotExistFailed()
	{
		return passengerDoesNotExistFailed;
	}
	
	
	
	// message
	public static String getPassengerAddedMessage()
	{
		return passengerAdded;
	}
	public static String getPassengerDeletedMessage()
	{
		return passengerDeleted;
	}
	
}















