package com.vendor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import org.mariadb.jdbc.Driver;
import com.newpage.NewpageDao;

public class VendorDao extends HttpServlet
{			
	// db connection
	public final static String DB_URL = "database.url";
	public final static String DB_USERNAME = "database.username";
	public final static String DB_PASSWORD = "database.password";
	
	
	public final static String EQUAL = NewpageDao.EQUAL;
	
	// db queries
	public final static String GET_VENDORS_BY_COUNTRY_QUERY = "database.get.vendors.by.country.query";
	public final static String GET_VENDORS_BY_CITY_QUERY = "database.get.vendors.by.city.query";
	public final static String GET_VENDOR_COUNTRIES_QUERY = "database.get.vendor.countries.query";
	public final static String GET_VENDOR_CITIES_QUERY = "database.get.vendor.cities.query";
	public final static String UPDATE_VENDOR_QUERY = "database.update.vendor.query";
	public final static String DELETE_VENDOR_QUERY = "database.delete.vendor.query";
	public final static String ADD_VENDOR_QUERY = "database.add.vendor.query";
	public final static String GET_VENDOR_BY_VENDOR_NAME_QUERY = "database.get.vendor.by.vendor.name.query";
	
	
	// vendor variables
	public final static String VENDOR = "vendor";
	public final static String VENDOR_BUTTONS = "vendor.buttons";
	public final static String VENDOR_LIST = "vendor.list";
	
	
	
	// search box variables

	// buttons
	public final static String VENDOR_GET_VENDORS_BUTTON = "vendor.get.vendors.button";

	
	// failed
	public final static String VENDOR_FAILED = "vendor.failed";
	public final static String VENDOR_VENDOR_ALREADY_EXISTS_FAILED = "vendor.vendor.already.exists.failed";
	
	// messages
	public final static String VENDOR_CREATED_MESSAGE = "vendor.vendor.created.message";
	public final static String VENDOR_UPDATED_MESSAGE = "vendor.vendor.updated.message";
	public final static String VENDOR_DELETED_MESSAGE = "vendor.vendor.deleted.message";
	
	
	// entered values
	public final static int VENDOR_ENTERED_VALUE_LENGTH = 14;
	public final static int VENDOR_INDEX = 0;
	public final static int ADDRESS_INDEX = 1;
	public final static int CITY_INDEX = 2;
	public final static int STATE_INDEX = 3;
	public final static int COUNTRY_INDEX = 4;
	public final static int ZIPCODE_INDEX = 5;
	public final static int TELEPHONE1_INDEX = 6;
	public final static int TELEPHONE2_INDEX = 7;
	public final static int FAX_INDEX = 8;
	public final static int EMAIL1_INDEX = 9;
	public final static int EMAIL2_INDEX = 10;
	public final static int WEBSITE_INDEX = 11;
	public final static int SEARCH_COUNTRY_INDEX = 12;
	public final static int SEARCH_CITY_INDEX = 13;
	
	
	// db connection
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs;
	private static String db_username;
	private static String db_password;
	private static String db_url;
	
	
	// db queries
	private static String getVendorsByCountryQuery;
	private static String getVendorsByCityQuery;
	private static String getVendorCountriesQuery;
	private static String getVendorCitiesQuery;
	private static String updateVendorQuery;
	private static String deleteVendorQuery;
	private static String addVendorQuery;
	private static String getVendorByVendorNameQuery;
	
	
	// vendor variables

	
	// search box variables

	// buttons
	private static String vendorGetVendorsButton;
	
	// failed
	private static String vendorVendorAlreadyExistsFailed;
	
	// message
	private static String vendorCreatedMessage;
	private static String vendorUpdatedMessage;
	private static String vendorDeletedMessage;
	
	
	public void init()
	{
		
		try {
			ServletContext sc = this.getServletContext();

			// db connection
			db_username = sc.getInitParameter(DB_USERNAME);
			db_password = sc.getInitParameter(DB_PASSWORD);
			db_url = sc.getInitParameter(DB_URL);
			
			
			// db queries
			getVendorsByCountryQuery = sc.getInitParameter(GET_VENDORS_BY_COUNTRY_QUERY);
			getVendorsByCityQuery = sc.getInitParameter(GET_VENDORS_BY_CITY_QUERY);
			getVendorCountriesQuery = sc.getInitParameter(GET_VENDOR_COUNTRIES_QUERY);
			getVendorCitiesQuery = sc.getInitParameter(GET_VENDOR_CITIES_QUERY);
			updateVendorQuery = sc.getInitParameter(UPDATE_VENDOR_QUERY);
			deleteVendorQuery = sc.getInitParameter(DELETE_VENDOR_QUERY);
			addVendorQuery = sc.getInitParameter(ADD_VENDOR_QUERY);
			getVendorByVendorNameQuery = sc.getInitParameter(GET_VENDOR_BY_VENDOR_NAME_QUERY);
			
			// hotel variables

			// search box variables

			// buttons
			vendorGetVendorsButton = sc.getInitParameter(VENDOR_GET_VENDORS_BUTTON);
			
			
			// failed
			vendorVendorAlreadyExistsFailed = sc.getInitParameter(VENDOR_VENDOR_ALREADY_EXISTS_FAILED);
			
			
			// message
			vendorCreatedMessage = sc.getInitParameter(VENDOR_CREATED_MESSAGE);
			vendorUpdatedMessage = sc.getInitParameter(VENDOR_UPDATED_MESSAGE);
			vendorDeletedMessage = sc.getInitParameter(VENDOR_DELETED_MESSAGE);
			

			DriverManager.registerDriver(new Driver());
			
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	
	public ArrayList<String> getVendorByVendorName(String vendor)
	{		
		ArrayList<String> returnList = new ArrayList<>();

			
		try {				
			conn = DriverManager.getConnection(db_url, db_username, db_password);	
				
			pstmt = conn.prepareStatement(getVendorByVendorNameQuery);
			pstmt.setString(1, vendor);

			rs = pstmt.executeQuery();
			
			while(rs.next())
			{							  
				returnList.add(rs.getString("Vendor"));
				returnList.add(rs.getString("Address"));
				returnList.add(rs.getString("City"));
				returnList.add(rs.getString("State"));
				returnList.add(rs.getString("Country"));
				returnList.add(rs.getString("Zipcode"));
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
	
	
	public ArrayList<String> getVendors(String country, String city)
	{		
		ArrayList<String> returnList = new ArrayList<>();

			
		try {				
			conn = DriverManager.getConnection(db_url, db_username, db_password);	
			
			String query = "";
			
			
			
			if(city.equals("") && country.equals(""))
				return returnList;
			else if(!city.equals(""))
			{
				query = getVendorsByCityQuery;
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, city);
			}
			else
			{
				query = getVendorsByCountryQuery;
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, country);
			}
			
				
			rs = pstmt.executeQuery();
			
			while(rs.next())
			{							  
				returnList.add(rs.getString("Vendor"));
			}
			
			conn.close();
			pstmt.close();
			rs.close();
		} catch (Exception e) {
			System.err.println(e);
		}	
		
		return returnList;
	}
	
	public void addVendor(String vendor, String address, String city, String state, String country, String zipcode, 
			String telephone1, String telephone2, String fax, String email1, String email2, String website)
	{		
		// NOT NULL attributes in database
		if(vendor == null || address == null || city == null || country == null || telephone1 == null) return;
		
		try {				
			conn = DriverManager.getConnection(db_url, db_username, db_password);			
			pstmt = conn.prepareStatement(addVendorQuery);
			pstmt.setString(1, vendor);
			pstmt.setString(2, address);
			pstmt.setString(3, city);
			pstmt.setString(4, state);
			pstmt.setString(5, country);
			pstmt.setString(6, zipcode);
			pstmt.setString(7, telephone1);
			pstmt.setString(8, telephone2);
			pstmt.setString(9, fax);
			pstmt.setString(10, email1);
			pstmt.setString(11, email2);
			pstmt.setString(12, website);
			
			rs = pstmt.executeQuery();
			
			conn.close();
			pstmt.close();
			rs.close();
		} catch (Exception e) {
			System.err.println(e);
		}	
	}
	
	public void deleteVendor(String vendor)
	{
		
		try {				
			conn = DriverManager.getConnection(db_url, db_username, db_password);	
			
			pstmt = conn.prepareStatement(deleteVendorQuery);
			pstmt.setString(1, vendor);
			rs = pstmt.executeQuery();

			conn.close();
			pstmt.close();
			rs.close();
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	
	public void updateVendor(String address, String city, String state, String country, String zipcode, String telephone1, String telephone2, String fax, String email1, String email2, String website, String vendor)
	{

		try {				
			conn = DriverManager.getConnection(db_url, db_username, db_password);	
			
			pstmt = conn.prepareStatement(updateVendorQuery);
			pstmt.setString(1, address);
			pstmt.setString(2, city);
			pstmt.setString(3, state);
			pstmt.setString(4, country);
			pstmt.setString(5, zipcode);
			pstmt.setString(6, telephone1);
			pstmt.setString(7, telephone2);
			pstmt.setString(8, fax);
			pstmt.setString(9, email1);
			pstmt.setString(10, email2);
			pstmt.setString(11, website);
			pstmt.setString(12, vendor);
			
			rs = pstmt.executeQuery();

			conn.close();
			pstmt.close();
			rs.close();
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	
	public ArrayList<String> getCountries()
	{
		ArrayList<String> returnList = new ArrayList<>();

		try {				
			conn = DriverManager.getConnection(db_url, db_username, db_password);			
			pstmt = conn.prepareStatement(getVendorCountriesQuery);
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
			pstmt = conn.prepareStatement(getVendorCitiesQuery);
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
	
	
	
	
	
	
	
	
	// Vendor Variables
	
	
	// buttons
	public static String getVendorGetVendorsButton()
	{
		return vendorGetVendorsButton;
	}
	
	
	// failed
	public static String getVendorVendorAlreadyExistsFailed()
	{
		return vendorVendorAlreadyExistsFailed;
	}
	
	
	// message
	public static String getVendorCreatedMessage()
	{
		return vendorCreatedMessage;
	}
	public static String getVendorUpdatedMessage()
	{
		return vendorUpdatedMessage;
	}
	public static String getVendorDeletedMessage()
	{
		return vendorDeletedMessage;
	}
}















