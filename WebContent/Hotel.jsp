<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Explorient | Hotel</title>
		<link rel="stylesheet" href="CSS/Hotel.css" type="text/css">
	</head>
<body>

	<%@include file="Website Template.jsp"%>

	<%
		HotelDao hdao = new HotelDao();
	
		// hotel list
		ArrayList<String> hotels = (ArrayList<String>)request.getAttribute(HotelDao.HOTEL_LIST);
		if(hotels != null && hotels.size() != 0)
			Collections.sort(hotels);
		
		// vendor list
		ArrayList<String> vendors = hdao.getVendors();
		if(vendors != null && vendors.size() != 0)
			Collections.sort(vendors);
		
		// country list
		ArrayList<String> countries = hdao.getCountries();
		if(countries != null && countries.size() != 0)
			Collections.sort(countries);
		
		
		// city list
		ArrayList<String> cities = hdao.getCities();
		if(cities != null && cities.size() != 0)
			Collections.sort(cities);
		
	
		// search box
		String searchCountry = HotelDao.getHotelSearchCountry();
		String searchCity = HotelDao.getHotelSearchCity();
		// buttons
		String getHotelsButton = HotelDao.getHotelGetHotelsButton();
		String createButton = HotelDao.getHotelCreateButton();
		String updateButton = HotelDao.getHotelUpdateButton();
		String deleteButton = HotelDao.getHotelDeleteButton();
		String clearButton = HotelDao.getHotelClearButton();
		
		// hotel variables
		String hotel_hotel = HotelDao.getHotelHotel();
		String hotel_vendor = HotelDao.getHotelVendor();
		String hotel_address = HotelDao.getHotelAddress();
		String hotel_city = HotelDao.getHotelCity();
		String hotel_state = HotelDao.getHotelState();
		String hotel_country = HotelDao.getHotelCountry();
		String hotel_zipcode = HotelDao.getHotelZipcode();
		String hotel_telephone1 = HotelDao.getHotelTelephone1();
		String hotel_telephone2 = HotelDao.getHotelTelephone2();
		String hotel_fax = HotelDao.getHotelFax();
		String hotel_email1 = HotelDao.getHotelEmail1();
		String hotel_email2 = HotelDao.getHotelEmail2();
		String hotel_website = HotelDao.getHotelWebsite();
		
		
		
		// failed
		String failed = (String)request.getAttribute(HotelDao.HOTEL_FAILED);
		
		// enteredValues
		String searchCountryValue = "", searchCityValue = "", hotelValue = "", vendorValue = "", addressValue = "", cityValue = "", stateValue = "", countryValue = "",
		zipcodeValue = "", telephone1Value = "", telephone2Value = "", faxValue = "", email1Value = "", email2Value = "", websiteValue = "";
		String enteredValues = (String)request.getAttribute(HotelDao.HOTEL);
		if(enteredValues != null)
		{
			String[] fragment = enteredValues.split(HotelDao.EQUAL, -1);
			if(fragment.length == HotelDao.HOTEL_ENTERED_VALUE_LENGTH)
			{
				hotelValue = fragment[HotelDao.HOTEL_INDEX];
				vendorValue = fragment[HotelDao.VENDOR_INDEX];
				addressValue = fragment[HotelDao.ADDRESS_INDEX];
				cityValue = fragment[HotelDao.CITY_INDEX];
				stateValue = fragment[HotelDao.STATE_INDEX];
				countryValue = fragment[HotelDao.COUNTRY_INDEX];
				zipcodeValue = fragment[HotelDao.ZIPCODE_INDEX];
				telephone1Value = fragment[HotelDao.TELEPHONE1_INDEX];
				telephone2Value = fragment[HotelDao.TELEPHONE2_INDEX];
				faxValue = fragment[HotelDao.FAX_INDEX];
				email1Value = fragment[HotelDao.EMAIL1_INDEX];
				email2Value = fragment[HotelDao.EMAIL2_INDEX];
				websiteValue = fragment[HotelDao.WEBSITE_INDEX];	
				searchCountryValue = fragment[HotelDao.SEARCH_COUNTRY_INDEX];
				searchCityValue = fragment[HotelDao.SEARCH_CITY_INDEX];
			}
		}
		
		
		if(failed == null)
		{
			failed = " ";
		}
	
	
	%>


	<div class="content_wrap">
	<form action="Hotel" method="post">
		<div class="hotel_div">
			
				<div class="hotel_search_div">
					<select class="editable-select countries"  name="<%=searchCountry%>"  value="<%=searchCountryValue%>" placeholder="<%=hotel_country.substring(1)%>">
						<% for(int i=0; i < countries.size(); i++) { %>
						<option class="selected"><%=countries.get(i) %></option>
						<% } %>
					</select>
					
					<select class="editable-select cities"  name="<%=searchCity%>"  value="<%=searchCityValue %>" placeholder="<%=hotel_city.substring(1)%>">
						<% for(int i=0; i < cities.size(); i++) { %>
						<option class="selected"><%=cities.get(i) %></option>
						<% } %>
					</select>
					<input type="submit" class="hotel_buttons" id="getHotels" name="<%=getHotelsButton%>" value="<%=getHotelsButton%>">
				</div><br><br>
				
			
			
				<select class="editable-select hotel-info" id="hotel" name="<%=hotel_hotel%>"  value="<%=hotelValue %>" placeholder="<%=hotel_hotel%>" title="<%=hotel_hotel%>"></select>
				
				<select class="editable-select" id="vendor" name="<%=hotel_vendor%>"  value="<%=vendorValue %>" placeholder="<%=hotel_vendor%>" title="<%=hotel_vendor%>">
					<% for(int i=0; i < vendors.size(); i++) { %>
						<option class="selected"><%=vendors.get(i) %></option>
					<% } %>
				</select>
				
				<select class="editable-select hotel-info" id="address" name="<%=hotel_address%>"  value="<%=addressValue %>" placeholder="<%=hotel_address%>" title="<%=hotel_address%>"></select>
				
				<select class="editable-select hotel-info" id="city" name="<%=hotel_city%>"  value="<%=cityValue %>" placeholder="<%=hotel_city%>" title="<%=hotel_city%>"></select>
				
				<select class="editable-select hotel-info" id="state" name="<%=hotel_state%>"  value="<%=stateValue %>" placeholder="<%=hotel_state%>" title="<%=hotel_state%>"></select>
				
				<select class="editable-select hotel-info" id="country" name="<%=hotel_country%>"  value="<%=countryValue %>" placeholder="<%=hotel_country%>" title="<%=hotel_country%>"></select>
				
				<select class="editable-select hotel-info" id="zipcode" name="<%=hotel_zipcode%>"  value="<%=zipcodeValue %>" placeholder="<%=hotel_zipcode%>" title="<%=hotel_zipcode%>"></select>
				
				<select class="editable-select hotel-info" id="telephone1" name="<%=hotel_telephone1%>"  value="<%=telephone1Value %>" placeholder="<%=hotel_telephone1%>" title="<%=hotel_telephone1%>"></select>
				
				<select class="editable-select hotel-info" id="telephone2" name="<%=hotel_telephone2%>"  value="<%=telephone2Value %>" placeholder="<%=hotel_telephone2%>" title="<%=hotel_telephone2%>"></select>
				
				<select class="editable-select hotel-info" id="fax" name="<%=hotel_fax%>"  value="<%=faxValue %>" placeholder="<%=hotel_fax%>" title="<%=hotel_fax%>"></select>
				
				<select class="editable-select hotel-info" id="email1" name="<%=hotel_email1%>"  value="<%=email1Value %>" placeholder="<%=hotel_email1%>" title="<%=hotel_email1%>"></select>
				
				<select class="editable-select hotel-info" id="email2" name="<%=hotel_email2%>"  value="<%=email2Value %>" placeholder="<%=hotel_email2%>" title="<%=hotel_email2%>"></select>
				
				<select class="editable-select hotel-info" id="website" name="<%=hotel_website%>"  value="<%=websiteValue %>" placeholder="<%=hotel_website%>" title="<%=hotel_website%>"></select>
				
	
				<p class="failedMessages" ><%=failed %></p>
				
				<br><br>
				
				<input type="submit" class="hotel_buttons" id="create" name="<%=createButton%>" value="<%=createButton%>">
				<input type="submit" class="hotel_buttons" id="update" name="<%=updateButton%>" value="<%=updateButton%>">
				<input type="submit" class="hotel_buttons" id="delete" name="<%=deleteButton%>" value="<%=deleteButton%>">
				<input type="submit" class="hotel_buttons" id="clear" name="<%=clearButton%>" value="<%=clearButton%>">
				
				
				
		
			
		</div>
		<div class="hotel_div">
			<div class="display_panel">
			<div id="display_panel_background"></div> 
				<h2 id="hotel_h3">Hotel List</h2>

						<%if(hotels != null && !hotels.isEmpty()) { %>
						<%	for(int i=0; i < hotels.size(); i++) { %>
							<input type="submit" class="hotels"  name="<%=HotelDao.HOTEL_BUTTONS %>" value="<%=hotels.get(i)%>">
							<% } %>
						<% } %>

			</div>
		</div>
	</form>
	
	
	
	
	</div>






</body>
</html>