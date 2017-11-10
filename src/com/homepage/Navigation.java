package com.homepage;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.login.LoginDao;
import com.newpage.NewpageDao;
import com.passenger.PassengerDao;

@WebServlet("/Navigation")
public class Navigation extends HttpServlet {
	
	private LoginDao dao;
	private HttpSession session;
	private String email;
	private String profile;
	private String setting;
	private String search;
	private String hotel;
	private String vendor;
	private String agent;
	private String passenger;
	private String booking;
	private String new1;
	private String itinerary;
	private String voucher;
	
	
	
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.getRequestDispatcher("Homepage.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		dao = new LoginDao();
		session = request.getSession();
		
		email = LoginDao.getLoginEmail();
			profile = HomepageDao.getHomepageNavigationProfile();
			setting = HomepageDao.getHomepageNavigationSetting();
		search = HomepageDao.getHomepageNavigationSearch();
			hotel = HomepageDao.getHomepageNavigationHotel();
			vendor = HomepageDao.getHomepageNavigationVendor();
			agent = HomepageDao.getHomepageNavigationAgent();
			passenger = HomepageDao.getHomepageNavigationPassenger();
		booking = HomepageDao.getHomepageNavigationBooking();
			new1 = HomepageDao.getHomepageNavigationNew();
			itinerary = HomepageDao.getHomepageNavigationItinerary();
			voucher = HomepageDao.getHomepageNavigationVoucher();
		
		
		if (request.getParameter(email) != null) 
		{
			session.setAttribute(HomepageDao.getHomepageNavigationSelected(), email);
			response.sendRedirect(LoginDao.HOMEPAGE);
        } 
			else if(request.getParameter(profile) != null)
			{
				session.setAttribute(HomepageDao.getHomepageNavigationSelected(), profile);
				response.sendRedirect(LoginDao.HOMEPAGE);
			}
			else if(request.getParameter(setting) != null)
			{
				session.setAttribute(HomepageDao.getHomepageNavigationSelected(), setting);
				response.sendRedirect(LoginDao.HOMEPAGE);
			}
		else if (request.getParameter(search) != null) 
        {
			session.setAttribute(HomepageDao.getHomepageNavigationSelected(), search);
			response.sendRedirect("Search.jsp");
        }
			else if(request.getParameter(hotel) != null)
			{
				session.setAttribute(HomepageDao.getHomepageNavigationSelected(), hotel);
				response.sendRedirect("Hotel");
			}
			else if(request.getParameter(vendor) != null)
			{
				session.setAttribute(HomepageDao.getHomepageNavigationSelected(), vendor);
				response.sendRedirect("Vendor");
			}
			else if(request.getParameter(agent) != null)
			{
				session.setAttribute(HomepageDao.getHomepageNavigationSelected(), agent);
				response.sendRedirect("Agent");
			}
			else if(request.getParameter(passenger) != null)
			{
				session.setAttribute(HomepageDao.getHomepageNavigationSelected(), passenger);
				response.sendRedirect(PassengerDao.PASSENGER);
			}
        else if (request.getParameter(booking) != null) 
        {
        	session.setAttribute(HomepageDao.getHomepageNavigationSelected(), booking);      	
        	response.sendRedirect("Booking.jsp");
        }
	        else if (request.getParameter(new1) != null) 
	        {
	        	session.setAttribute(HomepageDao.getHomepageNavigationSelected(), new1);      	
	        	response.sendRedirect(NewpageDao.NEW);
	        } 
	        else if (request.getParameter(itinerary) != null) 
	        {
	        	session.setAttribute(HomepageDao.getHomepageNavigationSelected(), itinerary);      	
	        	response.sendRedirect("Itinerary");
	        } 
			
	        else if (request.getParameter(voucher) != null) 
	        {
	        	session.setAttribute(HomepageDao.getHomepageNavigationSelected(), voucher);
	        	response.sendRedirect("Booking.jsp");
	        } 
		// Log out
        else 
        {
        	request.getRequestDispatcher(LoginDao.LOGOUT).forward(request, response);
        }
		
		//request.getRequestDispatcher("Homepage.jsp").forward(request, response);
	}

}
