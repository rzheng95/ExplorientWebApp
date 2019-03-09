package com.rzheng.homepage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;

import org.mariadb.jdbc.Driver;

import com.rzheng.login.SHA512;

public class HomepageDao extends HttpServlet
{	
	// db connection
	public final static String DB_URL = "database.url";
	public final static String DB_USERNAME = "database.username";
	public final static String DB_PASSWORD = "database.password";
	
	// Home page
	public final static String CSS_STYLING_COLOR_GOLD = "css.styling.color.gold";
	public final static String HOMEPAGE_NAVIGATION_SEARCH = "homepage.navigation.search";
	public final static String HOMEPAGE_NAVIGATION_BOOKING = "homepage.navigation.booking";
	public final static String HOMEPAGE_NAVIGATION_LOGOUT = "homepage.navigation.logout";
	public final static String HOMEPAGE_NAVIGATION_SELECTED = "homepage.navigation.selected";
	
	public final static String HOMEPAGE_NAVIGATION_PROFILE = "homepage.navigation.profile";
	public final static String HOMEPAGE_NAVIGATION_SETTING = "homepage.navigation.setting";
	public final static String HOMEPAGE_NAVIGATION_HOTEL = "homepage.navigation.hotel";
	public final static String HOMEPAGE_NAVIGATION_VENDOR = "homepage.navigation.vendor";
	public final static String HOMEPAGE_NAVIGATION_AGENT = "homepage.navigation.agent";
	public final static String HOMEPAGE_NAVIGATION_PASSENGER = "homepage.navigation.passenger";
	public final static String HOMEPAGE_NAVIGATION_NEW = "homepage.navigation.new";
	public final static String HOMEPAGE_NAVIGATION_ITINERARY = "homepage.navigation.itinerary";
	public final static String HOMEPAGE_NAVIGATION_VOUCHER = "homepage.navigation.voucher";
		
	// Home page
	private static String homepageNavigationSearch;
	private static String homepageNavigationBooking;
	private static String homepageNavigationLogout;
	private static String homepageNavigationSelected;
	private static String cssStylingColorGold;
	
	private static String homepageNavigationProfile;
	private static String homepageNavigationSetting;
	private static String homepageNavigationHotel;
	private static String homepageNavigationVendor;
	private static String homepageNavigationAgent;
	private static String homepageNavigationPassenger;
	private static String homepageNavigationNew;
	private static String homepageNavigationItinerary;
	private static String homepageNavigationVoucher;
	

	public void init()
	{
		
		
		try {
			ServletContext sc = this.getServletContext();
			
			homepageNavigationSearch = sc.getInitParameter(HOMEPAGE_NAVIGATION_SEARCH);
			homepageNavigationBooking = sc.getInitParameter(HOMEPAGE_NAVIGATION_BOOKING);
			homepageNavigationLogout = sc.getInitParameter(HOMEPAGE_NAVIGATION_LOGOUT);
			homepageNavigationSelected = sc.getInitParameter(HOMEPAGE_NAVIGATION_SELECTED) ;
			cssStylingColorGold = sc.getInitParameter(CSS_STYLING_COLOR_GOLD);
			
			
			homepageNavigationProfile = sc.getInitParameter(HOMEPAGE_NAVIGATION_PROFILE);
			homepageNavigationSetting = sc.getInitParameter(HOMEPAGE_NAVIGATION_SETTING);
			homepageNavigationHotel = sc.getInitParameter(HOMEPAGE_NAVIGATION_HOTEL);
			homepageNavigationVendor = sc.getInitParameter(HOMEPAGE_NAVIGATION_VENDOR);
			homepageNavigationAgent = sc.getInitParameter(HOMEPAGE_NAVIGATION_AGENT);
			homepageNavigationPassenger = sc.getInitParameter(HOMEPAGE_NAVIGATION_PASSENGER);
			homepageNavigationNew = sc.getInitParameter(HOMEPAGE_NAVIGATION_NEW);
			homepageNavigationItinerary = sc.getInitParameter(HOMEPAGE_NAVIGATION_ITINERARY);
			homepageNavigationVoucher = sc.getInitParameter(HOMEPAGE_NAVIGATION_VOUCHER);
					
			DriverManager.registerDriver(new Driver());
			
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	
	// Home page
	public static String getHomepageNavigationSearch()
	{
		return homepageNavigationSearch;
	}
	
	public static String getHomepageNavigationBooking()
	{
		return homepageNavigationBooking;
	}
	public static String getHomepageNavigationLogout()
	{
		return homepageNavigationLogout;
	}
	
	public static String getHomepageNavigationSelected() 
	{
		return homepageNavigationSelected;
	}

	public static String getCssStylingColorGold() 
	{
		return cssStylingColorGold;
	}
	
	public static String getHomepageNavigationProfile() 
	{
		return homepageNavigationProfile;
	}
	public static String getHomepageNavigationSetting() 
	{
		return homepageNavigationSetting;
	}
	public static String getHomepageNavigationHotel() 
	{
		return homepageNavigationHotel;
	}
	public static String getHomepageNavigationVendor() 
	{
		return homepageNavigationVendor;
	}
	public static String getHomepageNavigationAgent() 
	{
		return homepageNavigationAgent;
	}
	public static String getHomepageNavigationPassenger() 
	{
		return homepageNavigationPassenger;
	}
	public static String getHomepageNavigationNew() 
	{
		return homepageNavigationNew;
	}
	public static String getHomepageNavigationItinerary() 
	{
		return homepageNavigationItinerary;
	}
	public static String getHomepageNavigationVoucher() 
	{
		return homepageNavigationVoucher;
	}
	
}















