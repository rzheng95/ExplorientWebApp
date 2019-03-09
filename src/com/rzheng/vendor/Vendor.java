package com.rzheng.vendor;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rzheng.hotel.HotelDao;
import com.rzheng.login.EmailChecker;
import com.rzheng.login.LoginDao;


@WebServlet("/Vendor")
public class Vendor extends HttpServlet {
	
	private HotelDao hdao;
	private LoginDao ldao;
	private VendorDao vdao;
	private ArrayList<String> required;
	private ArrayList<String> notRequired;
	
	// search box
	private String searchCountry;
	private String searchCity;
	
	// buttons
	private String getVendorsButton;
	private String createButton;
	private String updateButton;
	private String deleteButton;
	private String clearButton;
	
	// hotel variables
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
		request.getRequestDispatcher("Vendor.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		hdao = new HotelDao();
		ldao = new LoginDao();
		vdao = new VendorDao();
		
		
		required = new ArrayList<>();
		notRequired = new ArrayList<>();
		
		
		// search box
		searchCountry = request.getParameter(HotelDao.getHotelSearchCountry()).trim();
		searchCity = request.getParameter(HotelDao.getHotelSearchCity()).trim();
		
		// buttons
		getVendorsButton = VendorDao.getVendorGetVendorsButton();
		createButton = HotelDao.getHotelCreateButton();
		updateButton = HotelDao.getHotelUpdateButton();
		deleteButton = HotelDao.getHotelDeleteButton();
		clearButton = HotelDao.getHotelClearButton();
		
		// vendor variables
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
		
		required.add(vendor); required.add(address); required.add(city);  required.add(country); required.add(telephone1);

		
		// Get Vendors button pressed
		if(request.getParameter(getVendorsButton) != null)
		{
			// will get vendors at the end
		}
		// clear button pressed
		else if(request.getParameter(clearButton) != null)
		{
			clear();
		}
		// delete button pressed
		else if(request.getParameter(deleteButton) != null)
		{
			// if vendor exists
			if(hdao.checkVendor(vendor))
			{
				vdao.deleteVendor(vendor);
				clear();
				request.setAttribute(VendorDao.VENDOR_FAILED, VendorDao.getVendorDeletedMessage());
			}
			else // vendor doesn't exist
			{
				clear();		
				request.setAttribute(VendorDao.VENDOR_FAILED, HotelDao.getHotelVendorNotFoundFailed());
			}
			

		}
		// check valid email
		else if((!email1.equals("") && !EmailChecker.validate(email1)) || (!email2.equals("") && !EmailChecker.validate(email2)))
		{
			request.setAttribute(VendorDao.VENDOR_FAILED, LoginDao.getRegisterInvalidEmailMessage());
			email1 = ""; email2 = "";
		}
		// update button pressed
		else if(request.getParameter(updateButton) != null)
		{
			// if vendor exists
			if(hdao.checkVendor(vendor)) 
			{
				vdao.updateVendor(address, city, state, country, zipcode, telephone1, telephone2, fax, email1, email2, website, vendor);
				clear();
				request.setAttribute(VendorDao.VENDOR_FAILED, VendorDao.getVendorUpdatedMessage());
			}
			else // vendor doesn't exist
			{
				clear();
				request.setAttribute(VendorDao.VENDOR_FAILED, HotelDao.getHotelVendorNotFoundFailed());
			}
		}
		// one of the vendors is clicked
		else if(request.getParameter(VendorDao.VENDOR_BUTTONS) != null)
		{
			
			String clickedVendor = request.getParameter(VendorDao.VENDOR_BUTTONS);
			ArrayList<String> vendorInfo = vdao.getVendorByVendorName(clickedVendor);

			
			vendor = vendorInfo.get(VendorDao.VENDOR_INDEX);
			address = vendorInfo.get(VendorDao.ADDRESS_INDEX);
			city = vendorInfo.get(VendorDao.CITY_INDEX);
			state = vendorInfo.get(VendorDao.STATE_INDEX);
			country = vendorInfo.get(VendorDao.COUNTRY_INDEX);
			zipcode = vendorInfo.get(VendorDao.ZIPCODE_INDEX);
			telephone1 = vendorInfo.get(VendorDao.TELEPHONE1_INDEX);
			telephone2 = vendorInfo.get(VendorDao.TELEPHONE2_INDEX);
			fax = vendorInfo.get(VendorDao.FAX_INDEX);
			email1 = vendorInfo.get(VendorDao.EMAIL1_INDEX);
			email2 = vendorInfo.get(VendorDao.EMAIL2_INDEX);
			website = vendorInfo.get(VendorDao.WEBSITE_INDEX);
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
						request.setAttribute(VendorDao.VENDOR_FAILED, LoginDao.getRegisterInvalidSymbolsLFailed());
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
						request.setAttribute(VendorDao.VENDOR_FAILED, LoginDao.getRegisterInvalidSymbolsLFailed());
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
						request.setAttribute(VendorDao.VENDOR_FAILED, HotelDao.getHotelRequiredFieldFailed());
						failed = true;
						break;
					}
				}
			}
			
			if(failed)
			{
				
			}
			// create button pressed
			else if(request.getParameter(createButton) != null)
			{
				// if vendor exists
				if(hdao.checkVendor(vendor)) 
				{
					clear();
					request.setAttribute(VendorDao.VENDOR_FAILED, VendorDao.getVendorVendorAlreadyExistsFailed());
				}
				else // create vendor if vendor doesn't exist
				{
					vdao.addVendor(vendor, address, city, state, country, zipcode, telephone1, telephone2, fax, email1, email2, website);
					request.setAttribute(VendorDao.VENDOR_FAILED, VendorDao.getVendorCreatedMessage());
					clear();
				}
			}
		}
		
		request.setAttribute(VendorDao.VENDOR_LIST, vdao.getVendors(searchCountry, searchCity));
		
		request.setAttribute(VendorDao.VENDOR, 
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

		request.getRequestDispatcher("Vendor.jsp").forward(request, response);
		
		
	}
	
	private void clear()
	{
		vendor = ""; address = ""; city = ""; state = ""; country = ""; zipcode = ""; telephone1 = ""; telephone2 = ""; fax = ""; email1 = ""; email2 = ""; website = "";
	}

}
