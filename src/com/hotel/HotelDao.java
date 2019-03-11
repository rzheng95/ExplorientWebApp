/*
 * This file is part of Explorient Web App
 * Copyright (C) 2016-2019 Richard R. Zheng
 *
 * https://github.com/rzheng95/ExplorientWebApp
 * 
 * All Right Reserved.
 */

package com.hotel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import org.mariadb.jdbc.Driver;

import com.newpage.NewpageDao;

public class HotelDao extends HttpServlet
{			
	// db connection
	public final static String DB_URL = "database.url";
	public final static String DB_USERNAME = "database.username";
	public final static String DB_PASSWORD = "database.password";
	
	
	public final static String EQUAL = NewpageDao.EQUAL;
	
	// db queries
	public final static String GET_HOTELS_BY_COUNTRY_QUERY = "database.get.hotels.by.country.query";
	public final static String GET_HOTELS_BY_CITY_QUERY = "database.get.hotels.by.city.query";
	public final static String GET_HOTEL_BY_HOTEL_NAME_QUERY = "database.get.hotel.by.hotel.name.query";
	public final static String ADD_HOTEL_QUERY = "database.add.hotel.query";
	public final static String GET_VENDORS_QUERY = "database.get.vendors.query";
	public final static String CHECK_VENDOR_QUERY = "database.check.vendor.query";
	public final static String UPDATE_HOTEL_QUERY = "database.update.hotel.query";
	public final static String DELETE_HOTEL_QUERY = "database.delete.hotel.query";
	public final static String GET_HOTEL_COUNTRIES_QUERY = "database.get.hotel.countries.query";
	public final static String GET_HOTEL_CITIES_QUERY = "database.get.hotel.cities.query";
	
	
	// hotel variables
	public final static String HOTEL = "hotel";
	public final static String HOTEL_BUTTONS = "hotel.buttons";
	public final static String HOTEL_LIST = "hotel.list";
	public final static String HOTEL_HOTEL = "hotel.hotel";
	public final static String HOTEL_VENDOR = "hotel.vendor";
	public final static String HOTEL_ADDRESS = "hotel.address";
	public final static String HOTEL_CITY = "hotel.city";
	public final static String HOTEL_STATE = "hotel.state";
	public final static String HOTEL_COUNTRY = "hotel.country";
	public final static String HOTEL_ZIPCODE = "hotel.zipcode";
	public final static String HOTEL_TELEPHONE1 = "hotel.telephone1";
	public final static String HOTEL_TELEPHONE2 = "hotel.telephone2";
	public final static String HOTEL_FAX = "hotel.fax";
	public final static String HOTEL_EMAIL1 = "hotel.email1";
	public final static String HOTEL_EMAIL2 = "hotel.email2";
	public final static String HOTEL_WEBSITE = "hotel.website";
	
	// search box variables
	public final static String HOTEL_SEARCH_COUNTRY = "hotel.search.country";
	public final static String HOTEL_SEARCH_CITY = "hotel.search.city";	
	// buttons
	public final static String HOTEL_GET_HOTELS_BUTTON = "hotel.get.hotels.button";
	public final static String HOTEL_CREATE_BUTTON = "hotel.create.button";
	public final static String HOTEL_UPDATE_BUTTON = "hotel.update.button";
	public final static String HOTEL_DELETE_BUTTON = "hotel.delete.button";
	public final static String HOTEL_CLEAR_BUTTON = "hotel.clear.button";
	
	
	// failed
	public final static String HOTEL_FAILED = "hotel.failed";
	public final static String HOTEL_REQUIRED_FIELD_FAILED = "hotel.*.indicates.required.field.failed";
	public final static String HOTEL_VENDOR_NOT_FOUND_FAILED = "hotel.vendor.not.found.failed";
	public final static String HOTEL_HOTEL_NOT_FOUND_FAILED = "hotel.hotel.not.found.failed";
	public final static String HOTEL_HOTEL_ALREADY_EXISTS_FAILED = "hotel.hotel.already.exists.failed";
	
	// messages
	public final static String HOTEL_CREATED_MESSAGE = "hotel.hotel.created.message";
	public final static String HOTEL_UPDATED_MESSAGE = "hotel.hotel.updated.message";
	public final static String HOTEL_DELETED_MESSAGE = "hotel.hotel.deleted.message";
	
	
	// entered values
	public final static int HOTEL_ENTERED_VALUE_LENGTH = 15;
	public final static int HOTEL_INDEX = 0;
	public final static int VENDOR_INDEX = 1;
	public final static int ADDRESS_INDEX = 2;
	public final static int CITY_INDEX = 3;
	public final static int STATE_INDEX = 4;
	public final static int COUNTRY_INDEX = 5;
	public final static int ZIPCODE_INDEX = 6;
	public final static int TELEPHONE1_INDEX = 7;
	public final static int TELEPHONE2_INDEX = 8;
	public final static int FAX_INDEX = 9;
	public final static int EMAIL1_INDEX = 10;
	public final static int EMAIL2_INDEX = 11;
	public final static int WEBSITE_INDEX = 12;
	public final static int SEARCH_COUNTRY_INDEX = 13;
	public final static int SEARCH_CITY_INDEX = 14;
	
	
	// db connection
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs;
	private static String db_username;
	private static String db_password;
	private static String db_url;
	
	
	// db queries
	private static String getHotelsByCountryQuery;
	private static String getHotelsByCityQuery;
	private static String getHotelByHotelNameQuery;
	private static String addHotelQuery;
	private static String getVendorsQuery;
	private static String checkVendorQuery;
	private static String updateHotelQuery;
	private static String deleteHotelQuery;
	private static String getHotelCountriesQuery;
	private static String getHotelCitiesQuery;
	
	
	// hotel variables
	private static String hotelHotel;
	private static String hotelVendor;
	private static String hotelAddress;
	private static String hotelCity;
	private static String hotelState;
	private static String hotelCountry;
	private static String hotelZipcode;
	private static String hotelTelephone1;
	private static String hotelTelephone2;
	private static String hotelFax;
	private static String hotelEmail1;
	private static String hotelEmail2;
	private static String hotelWebsite;
	// search box variables
	private static String hotelSearchCountry;
	private static String hotelSearchCity;
	// buttons
	private static String hotelGetHotelsButton;
	private static String hotelCreateButton;
	private static String hotelUpdateButton;
	private static String hotelDeleteButton;
	private static String hotelClearButton;
	
	// failed
	private static String hotelRequiredFieldFailed;
	private static String hotelVendorNotFoundFailed;
	private static String hotelHotelNotFoundFailed;
	private static String hotelHotelAlreadyExistsFailed;
	
	// message
	private static String hotelCreatedMessage;
	private static String hotelUpdatedMessage;
	private static String hotelDeletedMessage;
	
	
	
	public void init()
	{
		
		try {
			ServletContext sc = this.getServletContext();

			// db connection
			db_username = sc.getInitParameter(DB_USERNAME);
			db_password = sc.getInitParameter(DB_PASSWORD);
			db_url = sc.getInitParameter(DB_URL);
			
			
			// db queries
			getHotelsByCountryQuery = sc.getInitParameter(GET_HOTELS_BY_COUNTRY_QUERY);
			getHotelsByCityQuery = sc.getInitParameter(GET_HOTELS_BY_CITY_QUERY);
			getHotelByHotelNameQuery = sc.getInitParameter(GET_HOTEL_BY_HOTEL_NAME_QUERY);
			addHotelQuery = sc.getInitParameter(ADD_HOTEL_QUERY);
			getVendorsQuery = sc.getInitParameter(GET_VENDORS_QUERY);
			checkVendorQuery = sc.getInitParameter(CHECK_VENDOR_QUERY);
			updateHotelQuery = sc.getInitParameter(UPDATE_HOTEL_QUERY);
			deleteHotelQuery = sc.getInitParameter(DELETE_HOTEL_QUERY);
			getHotelCountriesQuery = sc.getInitParameter(GET_HOTEL_COUNTRIES_QUERY);
			getHotelCitiesQuery = sc.getInitParameter(GET_HOTEL_CITIES_QUERY);
			
			
			// hotel variables
			hotelHotel = sc.getInitParameter(HOTEL_HOTEL);
			hotelVendor = sc.getInitParameter(HOTEL_VENDOR);
			hotelAddress = sc.getInitParameter(HOTEL_ADDRESS);
			hotelCity = sc.getInitParameter(HOTEL_CITY);
			hotelState = sc.getInitParameter(HOTEL_STATE);
			hotelCountry = sc.getInitParameter(HOTEL_COUNTRY);
			hotelZipcode = sc.getInitParameter(HOTEL_ZIPCODE);
			hotelTelephone1 = sc.getInitParameter(HOTEL_TELEPHONE1);
			hotelTelephone2 = sc.getInitParameter(HOTEL_TELEPHONE2);
			hotelFax = sc.getInitParameter(HOTEL_FAX);
			hotelEmail1 = sc.getInitParameter(HOTEL_EMAIL1);
			hotelEmail2 = sc.getInitParameter(HOTEL_EMAIL2);
			hotelWebsite = sc.getInitParameter(HOTEL_WEBSITE);
			// search box variables
			hotelSearchCountry = sc.getInitParameter(HOTEL_SEARCH_COUNTRY);
			hotelSearchCity = sc.getInitParameter(HOTEL_SEARCH_CITY);
			// buttons
			hotelGetHotelsButton = sc.getInitParameter(HOTEL_GET_HOTELS_BUTTON);
			hotelCreateButton = sc.getInitParameter(HOTEL_CREATE_BUTTON);
			hotelUpdateButton = sc.getInitParameter(HOTEL_UPDATE_BUTTON);
			hotelDeleteButton = sc.getInitParameter(HOTEL_DELETE_BUTTON);
			hotelClearButton = sc.getInitParameter(HOTEL_CLEAR_BUTTON);
			
			
			// failed
			hotelRequiredFieldFailed = sc.getInitParameter(HOTEL_REQUIRED_FIELD_FAILED);
			hotelVendorNotFoundFailed = sc.getInitParameter(HOTEL_VENDOR_NOT_FOUND_FAILED);
			hotelHotelNotFoundFailed = sc.getInitParameter(HOTEL_HOTEL_NOT_FOUND_FAILED);
			hotelHotelAlreadyExistsFailed = sc.getInitParameter(HOTEL_HOTEL_ALREADY_EXISTS_FAILED);
			
			
			// message
			hotelCreatedMessage = sc.getInitParameter(HOTEL_CREATED_MESSAGE);
			hotelUpdatedMessage = sc.getInitParameter(HOTEL_UPDATED_MESSAGE);
			hotelDeletedMessage = sc.getInitParameter(HOTEL_DELETED_MESSAGE);
			

			DriverManager.registerDriver(new Driver());
			
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	
	
	public ArrayList<String> getHotelCountries()
	{
		ArrayList<String> returnList = new ArrayList<>();

		try {				
			conn = DriverManager.getConnection(db_url, db_username, db_password);			
			pstmt = conn.prepareStatement(getHotelCountriesQuery);
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
	
	
	public ArrayList<String> getHotelCities()
	{
		ArrayList<String> returnList = new ArrayList<>();

		try {				
			conn = DriverManager.getConnection(db_url, db_username, db_password);			
			pstmt = conn.prepareStatement(getHotelCitiesQuery);
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
	
	
	
	public boolean checkHotel(String hotel)
	{
		try {				
			conn = DriverManager.getConnection(db_url, db_username, db_password);			
			pstmt = conn.prepareStatement(getHotelByHotelNameQuery);
			pstmt.setString(1, hotel);
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
	
	public void deleteHotel(String hotel)
	{
		
		try {				
			conn = DriverManager.getConnection(db_url, db_username, db_password);	
			
			pstmt = conn.prepareStatement(deleteHotelQuery);
			pstmt.setString(1, hotel);
			rs = pstmt.executeQuery();

			conn.close();
			pstmt.close();
			rs.close();
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	public void updateHotel(String vendor, String address, String city, String state, String country, String zipcode, String telephone1, String telephone2, String fax, String email1, String email2, String website, String hotel)
	{

		try {				
			conn = DriverManager.getConnection(db_url, db_username, db_password);	
			
			pstmt = conn.prepareStatement(updateHotelQuery);
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
			pstmt.setString(13, hotel);
			
			rs = pstmt.executeQuery();

			conn.close();
			pstmt.close();
			rs.close();
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	
	public boolean checkVendor(String vendor)
	{			
		try {				
			conn = DriverManager.getConnection(db_url, db_username, db_password);	
			
			pstmt = conn.prepareStatement(checkVendorQuery);
			pstmt.setString(1, vendor);
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
	
	
	public ArrayList<String> getVendors()
	{
		ArrayList<String> returnList = new ArrayList<>();

		try {				
			conn = DriverManager.getConnection(db_url, db_username, db_password);			
			pstmt = conn.prepareStatement(getVendorsQuery);
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
	
	
	public void addHotel(String hotel, String vendor, String address, String city, String state, String country, String zipcode, 
			String telephone1, String telephone2, String fax, String email1, String email2, String website)
	{		
		// NOT NULL attributes in database
		if(hotel == null || vendor == null || address == null || city == null || country == null || telephone1 == null) return;
		
		try {				
			conn = DriverManager.getConnection(db_url, db_username, db_password);			
			pstmt = conn.prepareStatement(addHotelQuery);
			pstmt.setString(1, hotel);
			pstmt.setString(2, vendor);
			pstmt.setString(3, address);
			pstmt.setString(4, city);
			pstmt.setString(5, state);
			pstmt.setString(6, country);
			pstmt.setString(7, zipcode);
			pstmt.setString(8, telephone1);
			pstmt.setString(9, telephone2);
			pstmt.setString(10, fax);
			pstmt.setString(11, email1);
			pstmt.setString(12, email2);
			pstmt.setString(13, website);
			
			rs = pstmt.executeQuery();
			
			conn.close();
			pstmt.close();
			rs.close();
		} catch (Exception e) {
			System.err.println(e);
		}	
	}
	
	
	public ArrayList<String> getHotelByHotelName(String hotel)
	{		
		ArrayList<String> returnList = new ArrayList<>();

			
		try {				
			conn = DriverManager.getConnection(db_url, db_username, db_password);	
				
			pstmt = conn.prepareStatement(getHotelByHotelNameQuery);
			pstmt.setString(1, hotel);

			rs = pstmt.executeQuery();
			
			while(rs.next())
			{							  
				returnList.add(rs.getString("Hotel"));
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
	
	
	
	
	public ArrayList<String> getHotels(String country, String city)
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
				query = getHotelsByCityQuery;
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, city);
			}
			else
			{
				query = getHotelsByCountryQuery;
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, country);
			}
			
				
			rs = pstmt.executeQuery();
			
			while(rs.next())
			{							  
				returnList.add(rs.getString("Hotel"));
			}
			
			conn.close();
			pstmt.close();
			rs.close();
		} catch (Exception e) {
			System.err.println(e);
		}	
		
		return returnList;
	}
	
	
	
	
	
	
	
	
	// Hotel Variables
	public static String getHotelHotel()
	{
		return hotelHotel;
	}
	public static String getHotelVendor()
	{
		return hotelVendor;
	}
	public static String getHotelAddress()
	{
		return hotelAddress;
	}
	public static String getHotelCity()
	{
		return hotelCity;
	}
	public static String getHotelState()
	{
		return hotelState;
	}
	public static String getHotelCountry()
	{
		return hotelCountry;
	}
	public static String getHotelZipcode()
	{
		return hotelZipcode;
	}
	public static String getHotelTelephone1()
	{
		return hotelTelephone1;
	}
	public static String getHotelTelephone2()
	{
		return hotelTelephone2;
	}
	public static String getHotelFax()
	{
		return hotelFax;
	}
	public static String getHotelEmail1()
	{
		return hotelEmail1;
	}
	public static String getHotelEmail2()
	{
		return hotelEmail2;
	}
	public static String getHotelWebsite()
	{
		return hotelWebsite;
	}
	// search box variables
	public static String getHotelSearchCountry()
	{
		return hotelSearchCountry;
	}
	public static String getHotelSearchCity()
	{
		return hotelSearchCity;
	}
	// buttons
	public static String getHotelGetHotelsButton()
	{
		return hotelGetHotelsButton;
	}
	public static String getHotelCreateButton()
	{
		return hotelCreateButton;
	}
	public static String getHotelUpdateButton()
	{
		return hotelUpdateButton;
	}
	public static String getHotelDeleteButton()
	{
		return hotelDeleteButton;
	}
	public static String getHotelClearButton()
	{
		return hotelClearButton;
	}
	
	
	// failed
	public static String getHotelRequiredFieldFailed()
	{
		return hotelRequiredFieldFailed;
	}
	public static String getHotelVendorNotFoundFailed()
	{
		return hotelVendorNotFoundFailed;
	}
	public static String getHotelHotelNotFoundFailed()
	{
		return hotelHotelNotFoundFailed;
	}
	public static String getHotelHotelAlreadyExistsFailed()
	{
		return hotelHotelAlreadyExistsFailed;
	}
	
	
	// message
	public static String getHotelCreatedMessage()
	{
		return hotelCreatedMessage;
	}
	public static String getHotelUpdatedMessage()
	{
		return hotelUpdatedMessage;
	}
	public static String getHotelDeletedMessage()
	{
		return hotelDeletedMessage;
	}
	
}















