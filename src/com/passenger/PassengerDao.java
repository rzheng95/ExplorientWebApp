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
	
	// failed
	public final static String PASSENGER_FAILED = "passenger.failed";
	public final static String PASSENGER_CUSTOMER_ID_INVALID_FAILED = "passenger.customer.id.invalid.failed";
	public final static String PASSENGER_EMPTY_FIELD_FAILED = "passenger.empty.field.fail";
	
	
	
	public final static String PASSENGER = "passenger";
	public final static String PASSENGER_LIST = "passengerList";
	public final static String PASSENGER_TITLE = "passenger.title";
	public final static String PASSENGER_MIDDLENAME = "passenger.middlename";
	
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
	
	// failed
	private static String passengerCustomerIdInvalidFailed;
	private static String passengerEmptyFieldFailed;
	
	
	
	
	
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
			
			// failed
			passengerCustomerIdInvalidFailed = sc.getInitParameter(PASSENGER_CUSTOMER_ID_INVALID_FAILED);
			passengerEmptyFieldFailed = sc.getInitParameter(PASSENGER_EMPTY_FIELD_FAILED);
			
			
			passengerTitle = sc.getInitParameter(PASSENGER_TITLE);
			passengerMiddlename = sc.getInitParameter(PASSENGER_MIDDLENAME);
			
			
			
		
			DriverManager.registerDriver(new Driver());
			
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	public void addPassenger(String customerId, String title, String firstname, String middle, String lastname)
	{		
		if(!npdao.checkCustomerId(customerId))return;
			
		try {				
			conn = DriverManager.getConnection(db_url, db_username, db_password);			
			pstmt = conn.prepareStatement(addPassengerQuery);
			pstmt.setString(1, customerId);
			pstmt.setString(2, title);
			pstmt.setString(3, firstname);
			pstmt.setString(4, middle);
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
	
}















