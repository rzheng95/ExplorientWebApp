<!-- 

  This file is part of Explorient Web App
  Copyright (C) 2016-2019 Richard R. Zheng
 
  https://github.com/rzheng95/ExplorientWebApp
  
  All Right Reserved.
 
 -->

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Explorient | Vendor</title>
		<link rel="stylesheet" href="CSS/Vendor.css" type="text/css">
	</head>
<body>
	
	<%@include file="Website Template.jsp"%>

		<%
		
		VendorDao vdao = new VendorDao(); 
		
		// vendor list
		ArrayList<String> vendors = (ArrayList<String>)request.getAttribute(VendorDao.VENDOR_LIST); 
		if(vendors != null && vendors.size() != 0)
			Collections.sort(vendors);
		
		// country list
		ArrayList<String> countries = vdao.getCountries();
		if(countries != null && countries.size() != 0)
			Collections.sort(countries);
		
		
		// city list
		ArrayList<String> cities = vdao.getCities();
		if(cities != null && cities.size() != 0)
			Collections.sort(cities);
		
	
		// search box
		String searchCountry = HotelDao.getHotelSearchCountry();
		String searchCity = HotelDao.getHotelSearchCity();
		
		// buttons
		String getVendorsButton = VendorDao.getVendorGetVendorsButton();
		String createButton = HotelDao.getHotelCreateButton();
		String updateButton = HotelDao.getHotelUpdateButton();
		String deleteButton = HotelDao.getHotelDeleteButton();
		String clearButton = HotelDao.getHotelClearButton();
		
		// vendor variables
		String vendor_vendor = HotelDao.getHotelVendor();
		String vendor_address = HotelDao.getHotelAddress();
		String vendor_city = HotelDao.getHotelCity();
		String vendor_state = HotelDao.getHotelState();
		String vendor_country = HotelDao.getHotelCountry();
		String vendor_zipcode = HotelDao.getHotelZipcode();
		String vendor_telephone1 = HotelDao.getHotelTelephone1();
		String vendor_telephone2 = HotelDao.getHotelTelephone2();
		String vendor_fax = HotelDao.getHotelFax();
		String vendor_email1 = HotelDao.getHotelEmail1();
		String vendor_email2 = HotelDao.getHotelEmail2();
		String vendor_website = HotelDao.getHotelWebsite();
		
		
		
		// failed
		String failed = (String)request.getAttribute(VendorDao.VENDOR_FAILED);
		
		// enteredValues
		String searchCountryValue = "", searchCityValue = "", vendorValue = "", addressValue = "", cityValue = "", stateValue = "", countryValue = "",
		zipcodeValue = "", telephone1Value = "", telephone2Value = "", faxValue = "", email1Value = "", email2Value = "", websiteValue = "";
		String enteredValues = (String)request.getAttribute(VendorDao.VENDOR);
		if(enteredValues != null)
		{
			String[] fragment = enteredValues.split(VendorDao.EQUAL, -1);
			if(fragment.length == VendorDao.VENDOR_ENTERED_VALUE_LENGTH)
			{
				vendorValue = fragment[VendorDao.VENDOR_INDEX];
				addressValue = fragment[VendorDao.ADDRESS_INDEX];
				cityValue = fragment[VendorDao.CITY_INDEX];
				stateValue = fragment[VendorDao.STATE_INDEX];
				countryValue = fragment[VendorDao.COUNTRY_INDEX];
				zipcodeValue = fragment[VendorDao.ZIPCODE_INDEX];
				telephone1Value = fragment[VendorDao.TELEPHONE1_INDEX];
				telephone2Value = fragment[VendorDao.TELEPHONE2_INDEX];
				faxValue = fragment[VendorDao.FAX_INDEX];
				email1Value = fragment[VendorDao.EMAIL1_INDEX];
				email2Value = fragment[VendorDao.EMAIL2_INDEX];
				websiteValue = fragment[VendorDao.WEBSITE_INDEX];	
				searchCountryValue = fragment[VendorDao.SEARCH_COUNTRY_INDEX];
				searchCityValue = fragment[VendorDao.SEARCH_CITY_INDEX];
			}
		}
		
		
		if(failed == null)
		{
			failed = " ";
		}
	
	
	%>


	<div class="content_wrap">
	<form action="Vendor" method="post">
		<div class="vendor_div">
			
				<div class="vendor_search_div">
					<select class="editable-select countries"  name="<%=searchCountry%>"  value="<%=searchCountryValue%>" placeholder="<%=vendor_country.substring(1)%>" title="<%=vendor_country.substring(1)%>">
						<% for(int i=0; i < countries.size(); i++) { %>
						<option class="selected"><%=countries.get(i) %></option>
						<% } %>
					</select>
					
					<select class="editable-select cities"  name="<%=searchCity%>"  value="<%=searchCityValue %>" placeholder="<%=vendor_city.substring(1)%>" title="<%=vendor_city.substring(1)%>">
						<% for(int i=0; i < cities.size(); i++) { %>
						<option class="selected"><%=cities.get(i) %></option>
						<% } %>
					</select>
					<input type="submit" class="vendor_buttons" id="getVendors" name="<%=getVendorsButton%>" value="<%=getVendorsButton%>">
				</div><br><br>
				
			
				
				<select class="editable-select vendor-info" id="vendor" name="<%=vendor_vendor%>"  value="<%=vendorValue %>" placeholder="<%=vendor_vendor%>" title="<%=vendor_vendor%>"></select>
				
				<select class="editable-select vendor-info" id="address" name="<%=vendor_address%>"  value="<%=addressValue %>" placeholder="<%=vendor_address%>" title="<%=vendor_address%>"></select>
				
				<select class="editable-select vendor-info" id="city" name="<%=vendor_city%>"  value="<%=cityValue %>" placeholder="<%=vendor_city%>" title="<%=vendor_city%>"></select>
				
				<select class="editable-select vendor-info" id="state" name="<%=vendor_state%>"  value="<%=stateValue %>" placeholder="<%=vendor_state%>" title="<%=vendor_state%>"></select>
				
				<select class="editable-select vendor-info" id="country" name="<%=vendor_country%>"  value="<%=countryValue %>" placeholder="<%=vendor_country%>" title="<%=vendor_country%>"></select>
				
				<select class="editable-select vendor-info" id="zipcode" name="<%=vendor_zipcode%>"  value="<%=zipcodeValue %>" placeholder="<%=vendor_zipcode%>" title="<%=vendor_zipcode%>"></select>
				
				<select class="editable-select vendor-info" id="telephone1" name="<%=vendor_telephone1%>"  value="<%=telephone1Value %>" placeholder="<%=vendor_telephone1%>" title="<%=vendor_telephone1%>"></select>
				
				<select class="editable-select vendor-info" id="telephone2" name="<%=vendor_telephone2%>"  value="<%=telephone2Value %>" placeholder="<%=vendor_telephone2%>" title="<%=vendor_telephone2%>"></select>
				
				<select class="editable-select vendor-info" id="fax" name="<%=vendor_fax%>"  value="<%=faxValue %>" placeholder="<%=vendor_fax%>" title="<%=vendor_fax%>"></select>
				
				<select class="editable-select vendor-info" id="email1" name="<%=vendor_email1%>"  value="<%=email1Value %>" placeholder="<%=vendor_email1%>" title="<%=vendor_email1%>"></select>
				
				<select class="editable-select vendor-info" id="email2" name="<%=vendor_email2%>"  value="<%=email2Value %>" placeholder="<%=vendor_email2%>" title="<%=vendor_email2%>"></select>
				
				<select class="editable-select vendor-info" id="website" name="<%=vendor_website%>"  value="<%=websiteValue %>" placeholder="<%=vendor_website%>" title="<%=vendor_website%>"></select>
				
	
				<p class="failedMessages" ><%=failed %></p>
				
				<br><br>
				
				<input type="submit" class="vendor_buttons" id="create" name="<%=createButton%>" value="<%=createButton%>">
				<input type="submit" class="vendor_buttons" id="update" name="<%=updateButton%>" value="<%=updateButton%>">
				<input type="submit" class="vendor_buttons" id="delete" name="<%=deleteButton%>" value="<%=deleteButton%>">
				<input type="submit" class="vendor_buttons" id="clear" name="<%=clearButton%>" value="<%=clearButton%>">
				
				
				
		
			
		</div>
		<div class="vendor_div">
			<div class="display_panel">
			<div id="display_panel_background"></div> 
				<h2 id="vendor_h3">Vendor List</h2>

						<%if(vendors != null && !vendors.isEmpty()) { %>
						<%	for(int i=0; i < vendors.size(); i++) { %>
							<input type="submit" class="vendors"  name="<%=VendorDao.VENDOR_BUTTONS %>" value="<%=vendors.get(i)%>"> 
							<% } %>
						<% } %>

			</div>
		</div>
	</form>
	
	
	
	
	</div>
	
	
	
</body>
</html>