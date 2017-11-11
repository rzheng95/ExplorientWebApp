package com.hotel;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.login.EmailChecker;
import com.login.LoginDao;
import com.passenger.PassengerDao;

@WebServlet("/Hotel")
public class Hotel extends HttpServlet {
	private HotelDao hdao;
	private LoginDao ldao;
	private ArrayList<String> required;
	private ArrayList<String> notRequired;
	
	// search box
	private String searchCountry;
	private String searchCity;
	
	// buttons
	private String getHotelsButton;
	private String createButton;
	private String updateButton;
	private String deleteButton;
	private String clearButton;
	
	// hotel variables
	private String hotel;
	private String vendor;
	private String address;
	private String city;
	private String state;
	private String country;
	private String zipcode;
	private String telephone1;
	private String telephone2;
	private String fax;
	private String email1;
	private String email2;
	private String website;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("Hotel.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		hdao = new HotelDao();
		ldao = new LoginDao();
		
		
		required = new ArrayList<>();
		notRequired = new ArrayList<>();
		
		
		// search box
		searchCountry = request.getParameter(HotelDao.getHotelSearchCountry()).trim();
		searchCity = request.getParameter(HotelDao.getHotelSearchCity()).trim();
		
		// buttons
		getHotelsButton = HotelDao.getHotelGetHotelsButton();
		createButton = HotelDao.getHotelCreateButton();
		updateButton = HotelDao.getHotelUpdateButton();
		deleteButton = HotelDao.getHotelDeleteButton();
		clearButton = HotelDao.getHotelClearButton();
		
		// hotel variables
		hotel = request.getParameter(HotelDao.getHotelHotel()).trim();
		vendor = request.getParameter(HotelDao.getHotelVendor()).trim();
		address = request.getParameter(HotelDao.getHotelAddress()).trim();
		city = request.getParameter(HotelDao.getHotelCity()).trim();
		state = request.getParameter(HotelDao.getHotelState()).trim();
		country = request.getParameter(HotelDao.getHotelCountry()).trim();
		zipcode = request.getParameter(HotelDao.getHotelZipcode()).trim();
		telephone1 = request.getParameter(HotelDao.getHotelTelephone1()).trim();
		telephone2 = request.getParameter(HotelDao.getHotelTelephone2()).trim();
		fax = request.getParameter(HotelDao.getHotelFax()).trim();
		email1 = request.getParameter(HotelDao.getHotelEmail1()).trim();
		email2 = request.getParameter(HotelDao.getHotelEmail2()).trim();
		website = request.getParameter(HotelDao.getHotelWebsite()).trim();
		
		notRequired.add(searchCountry); notRequired.add(searchCity); notRequired.add(state); notRequired.add(zipcode); notRequired.add(telephone2);
		notRequired.add(fax); notRequired.add(email1); notRequired.add(email2); notRequired.add(website); 
		
		required.add(hotel); required.add(vendor); required.add(address); required.add(city);  required.add(country); required.add(telephone1);

		
		// Get Hotels button pressed
		if(request.getParameter(getHotelsButton) != null)
		{
			//request.setAttribute(HotelDao.HOTEL_LIST, hdao.getHotels(searchCountry, searchCity));
		}
		// clear button pressed
		else if(request.getParameter(clearButton) != null)
		{
			clear();
		}
		// delete button pressed
		else if(request.getParameter(deleteButton) != null)
		{
			// if hotel exists
			if(hdao.checkHotel(hotel))
			{
				hdao.deleteHotel(hotel);
				clear();
				request.setAttribute(HotelDao.HOTEL_FAILED, HotelDao.getHotelDeletedMessage());
			}
			else // hotel doesn't exist
			{
				clear();		
				request.setAttribute(HotelDao.HOTEL_FAILED, HotelDao.getHotelHotelNotFoundFailed());
			}
			

		}
		// check valid email
		else if((!email1.equals("") && !EmailChecker.validate(email1)) || (!email2.equals("") && !EmailChecker.validate(email2)))
		{
			request.setAttribute(HotelDao.HOTEL_FAILED, LoginDao.getRegisterInvalidEmailMessage());
			email1 = ""; email2 = "";
		}
		// update button pressed
		else if(request.getParameter(updateButton) != null)
		{
			// if hotel exists
			if(hdao.checkHotel(hotel))
			{
				hdao.updateHotel(vendor, address, city, state, country, zipcode, telephone1, telephone2, fax, email1, email2, website, hotel);
				clear();
				request.setAttribute(HotelDao.HOTEL_FAILED, HotelDao.getHotelUpdatedMessage());
			}
			else // hotel doesn't exist
			{
				clear();
				request.setAttribute(HotelDao.HOTEL_FAILED, HotelDao.getHotelHotelNotFoundFailed());
			}
		}
		// one of the hotels is clicked
		else if(request.getParameter(HotelDao.HOTEL_BUTTONS) != null)
		{
			
			String clickedHotel = request.getParameter(HotelDao.HOTEL_BUTTONS);
			
			ArrayList<String> hotelInfo = hdao.getHotelByHotelName(clickedHotel);
			
			hotel = hotelInfo.get(HotelDao.HOTEL_INDEX);
			vendor = hotelInfo.get(HotelDao.VENDOR_INDEX);
			address = hotelInfo.get(HotelDao.ADDRESS_INDEX);
			city = hotelInfo.get(HotelDao.CITY_INDEX);
			state = hotelInfo.get(HotelDao.STATE_INDEX);
			country = hotelInfo.get(HotelDao.COUNTRY_INDEX);
			zipcode = hotelInfo.get(HotelDao.ZIPCODE_INDEX);
			telephone1 = hotelInfo.get(HotelDao.TELEPHONE1_INDEX);
			telephone2 = hotelInfo.get(HotelDao.TELEPHONE2_INDEX);
			fax = hotelInfo.get(HotelDao.FAX_INDEX);
			email1 = hotelInfo.get(HotelDao.EMAIL1_INDEX);
			email2 = hotelInfo.get(HotelDao.EMAIL2_INDEX);
			website = hotelInfo.get(HotelDao.WEBSITE_INDEX);
		}
		// some other buttons pressed
		else
		{
			Boolean failed = false;
			if(!failed)
			{
				// going though not required fileds
				for(int i=0; i<notRequired.size(); i++)
				{
					// check invaild symbols
					if(ldao.checkInvalidSymbols(notRequired.get(i)))
					{
						request.setAttribute(HotelDao.HOTEL_FAILED, LoginDao.getRegisterInvalidSymbolsLFailed());
						failed = true;
						clear();
						break;
					}
				}
			}
			
			if(!failed)
			{
				// going though required fileds
				for(int i=0; i<required.size(); i++)
				{
					// check invaild symbols
					if(ldao.checkInvalidSymbols(required.get(i)))
					{
						request.setAttribute(HotelDao.HOTEL_FAILED, LoginDao.getRegisterInvalidSymbolsLFailed());
						failed = true;
						clear();
						break;			
					}
				}
			}
			
			if(!failed)
			{
				// going though required fileds
				for(int i=0; i<required.size(); i++)
				{
					// check empty
					if(required.get(i).equals("") || required.get(i) == null)
					{
						request.setAttribute(HotelDao.HOTEL_FAILED, HotelDao.getHotelRequiredFieldFailed());
						failed = true;
						break;
					}
				}
			}
			
			if(failed)
			{
				
			}
			// check if vendor exists
			else if(!hdao.checkVendor(vendor))
			{
				request.setAttribute(HotelDao.HOTEL_FAILED, HotelDao.getHotelVendorNotFoundFailed());
				vendor = "";
			}
			// create button pressed
			else if(request.getParameter(createButton) != null)
			{
				// if hotel exists
				if(hdao.checkHotel(hotel))
				{
					clear();
					request.setAttribute(HotelDao.HOTEL_FAILED, HotelDao.getHotelHotelAlreadyExistsFailed());
				}
				else // create hotel if hotel doesn't exist
				{
					hdao.addHotel(hotel, vendor, address, city, state, country, zipcode, telephone1, telephone2, fax, email1, email2, website);
					request.setAttribute(HotelDao.HOTEL_FAILED, HotelDao.getHotelCreatedMessage());
					clear();
				}
			}
		}
		
		request.setAttribute(HotelDao.HOTEL_LIST, hdao.getHotels(searchCountry, searchCity));
		
		request.setAttribute(HotelDao.HOTEL, 
				 hotel+HotelDao.EQUAL+
				 vendor+HotelDao.EQUAL+
				 address+HotelDao.EQUAL+
				 city+HotelDao.EQUAL+
				 state+HotelDao.EQUAL+
				 country+HotelDao.EQUAL+
				 zipcode+HotelDao.EQUAL+
				 telephone1+HotelDao.EQUAL+
				 telephone2+HotelDao.EQUAL+
				 fax+HotelDao.EQUAL+
				 email1+HotelDao.EQUAL+
				 email2+HotelDao.EQUAL+
				 website+HotelDao.EQUAL+
				 searchCountry+HotelDao.EQUAL+
				 searchCity);

		request.getRequestDispatcher("Hotel.jsp").forward(request, response);
		
		
	}
	
	private void clear()
	{
		hotel = ""; vendor = ""; address = ""; city = ""; state = ""; country = ""; zipcode = ""; telephone1 = ""; telephone2 = ""; fax = ""; email1 = ""; email2 = ""; website = "";
	}

}


















