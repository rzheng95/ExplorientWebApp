package com.itinerary;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
	
	// db queries
	
	
	// failed

	
	
	// message

	
	
	
	// db connection
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs;
	private static String db_username;
	private static String db_password;
	private static String db_url;
	
	
	// db queries

	
	// failed

	
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

			
			// failed

			
			// message

			
			
			
			
		
			DriverManager.registerDriver(new Driver());
			
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	
	

	
	
	// failed

	
	
	
	// message

	
}















