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
	<title>Explorient | Itinerary</title>
	<link rel="stylesheet" href="CSS/Itinerary.css" type="text/css">
</head>

<body>

<%@ page import="com.rzheng.itinerary.Itinerary"%>

<%@include file="Website Template.jsp"%>


		<%
		HotelDao hdao = new HotelDao();
		PassengerDao pdao = new PassengerDao(); 
		ItineraryDao idao = new ItineraryDao();
	
		
		
		
		// country list
		ArrayList<String> tourCountries = idao.getCountries();

		
		// city list
		ArrayList<String> tourCities = idao.getCities();
		
		
		
	
		// search box name variables & buttons
		String customer_id = NewpageDao.getNewCustomerId();
		String search_box_lastname = PassengerDao.getPassengerSearchBoxLastname();
		String departure_date_from = PassengerDao.getPassengerDepartureDateFrom();
		String departure_date_to = PassengerDao.getPassengerDepartureDateTo();
		String get_customer_ids = PassengerDao.getPassengerGetCustomerIds();
		String get_itinerary = ItineraryDao.getItineraryGetItineraryButton();
		String p_lastname = LoginDao.getRegisterLastname();
		
		
		// itinerary variables
		String day = ItineraryDao.getItineraryDay();
		String activity = ItineraryDao.getItineraryActivity();
		String activityTemplates = ItineraryDao.getItineraryActivityTemplates();
		
		String landService = ItineraryDao.getItineraryLandService();
		String vendors = ItineraryDao.getItineraryVendors();
		String accommodations = ItineraryDao.getItineraryAccommodations();
		String roomType = ItineraryDao.getItineraryRoomType();
		
		String landBreakfast = ItineraryDao.getItineraryLandBreakfast();
		String landLunch = ItineraryDao.getItineraryLandLunch();
		String landDinner = ItineraryDao.getItineraryLandDinner();
		
		String hotelBreakfast = ItineraryDao.getItineraryHotelBreakfast();
		String hotelLunch = ItineraryDao.getItineraryHotelLunch();
		String hotelDinner = ItineraryDao.getItineraryHotelDinner();
		
		
		// itinerary button
		String searchButton = ItineraryDao.getItinerarySearchButton();
		String getActivityButton = ItineraryDao.getItineraryGetActivityButton();
		String moreLandServiceButton = ItineraryDao.getItineraryMoreLandServiceButton();
		String moreHotelServiceButton = ItineraryDao.getItineraryMoreHotelServiceButton();
		
		// hotel search textfields
		String landServiceCountry = ItineraryDao.getItineraryHotelCountry();
		String landServiceCity = ItineraryDao.getItineraryHotelCity();
		String searchCountry = ItineraryDao.getItineraryHotelCountry();
		String searchCity = ItineraryDao.getItineraryHotelCity();
		
		
		// current row id
		String rowId = (String)request.getAttribute(ItineraryDao.ROW_ID);
		
		
		// get tour list
		ArrayList<Object> tourList = (ArrayList<Object>)request.getAttribute(ItineraryDao.TOUR_LIST);
		
		// get tour name list
		ArrayList<ArrayList<String>> tourNameList = (ArrayList<ArrayList<String>>)request.getAttribute(ItineraryDao.TOUR_NAME_LIST);
		
		// hotel list
		ArrayList<String> hotelList = (ArrayList<String>)request.getAttribute(ItineraryDao.HOTEL_LIST);
		
		// activity template list
		ArrayList<String> activityTemplateList = (ArrayList<String>)request.getAttribute(ItineraryDao.ACTIVITY_TEMPLATE_LIST);
		
		// vendor list
		ArrayList<String> vendorList = (ArrayList<String>)request.getAttribute(ItineraryDao.VENDOR_LIST);
		
		// get customer id list
		ArrayList<String> customerIdList = (ArrayList<String>)request.getAttribute(PassengerDao.PASSENGER_CUSTOMER_IDS);
		

		

		
		// failed
		String failed = (String)request.getAttribute(ItineraryDao.ITINERARY_FAILED);
		
		
		// enteredValues
		String searchBoxLastnameValue = "", departureDateFromValue = "", departureDateToValue = "", customerIdValue = "";
		
		String enteredValues = (String)request.getAttribute(ItineraryDao.ITINERARY);
		if(enteredValues != null)
		{
			String[] fragment = enteredValues.split("=", -1);
			
			searchBoxLastnameValue = fragment[0];
			departureDateFromValue = fragment[1];
			departureDateToValue = fragment[2];
			customerIdValue = fragment[3];
			
			/*if(fragment.length == PassengerDao.PASSENGER_ENTERED_VALUE_LENGTH)
			{
				customerIdValue = fragment[PassengerDao.CUSTOMER_ID_INDEX];
			}*/
		}
		
		
		if(failed == null)
		{
			failed = " ";
		}
		
	
	%>
	<script src="JS/autosize.min.js"></script>
	<script>
		
	$(document).ready(function() {
			
		autosize($('textarea'));
		
		// delay 10ms before click otherwise it will trigger the event before the handler is attached
		setTimeout(function() {
			$("#scroll-down").trigger( "click" );
	    },10);

		
			
	});
	

	$(function(){

		   $("#scroll-down").click(function(event) {

		       event.preventDefault(); //stops browser updating url

		       var id = $(this).attr("href");

		       var divPosition = $(id).offset().top - 200;

		       $("html, body").animate({scrollTop: divPosition});

		   });

		});
	
	
	
	</script>
	
	
	<div class="content_wrap">
	
	
	<div class="itinerary_div">
			<form action="Itinerary" method="post">
				
				<div id="search_box">
					<fieldset class="fieldset">
						<legend id="legend" align= "left">Get Customer IDs by last name or departure date range</legend> 
						
						<div id="customer_id_serach_div">
						
							<select class="editable-select no_background_img" id="search_box_lastname" name="<%=search_box_lastname%>"  value="<%=searchBoxLastnameValue%>" placeholder="<%=LoginDao.CapitalizeFirstLetter(p_lastname) %>" onkeyup="this.value=this.value.replace(/[^A-Za-z]/g,'');">
							</select>
							
							<input type="text" class="datepicker" id="search_box_From" name="<%=departure_date_from%>" value="<%=departureDateFromValue%>" placeholder="<%=departure_date_from%>">
							<input type="text" class="datepicker" id="search_box_To" name="<%=departure_date_to%>" value="<%=departureDateToValue%>" placeholder="<%=departure_date_to%>">
						
							<input type="submit" class="search_box_buttons itinerary_buttons" id="getCustomerIds" name="<%=get_customer_ids%>" value="<%=get_customer_ids%>">
							
						</div> 
						
						<div id="customer_id_div">
							<select class="editable-select" id="customer_id" name="<%=customer_id%>"  value="<%=customerIdValue%>" placeholder="<%=customer_id%>">
								<% if(customerIdList != null && !customerIdList.isEmpty()){ %>
									<% for(int i=0; i < customerIdList.size(); i++) { %>
										<option class="selected"><%=customerIdList.get(i) %></option>
								<% } }%>
							</select>
						
											
							<input type="submit" class="search_box_buttons itinerary_buttons" id="getPassengers" name="<%=get_itinerary%>" value="<%=get_itinerary%>">
	
						</div>
						
					</fieldset>
				</div>
				<p class="failedMessages" ><%=failed %></p>

				<br>
				<p>Package: </p>
				
				
				<div id="itinerary_div">

					<table style="width:100%">
						 <colgroup>
						    <col style="width:10%">
						    <col style="width:45%">
						    <col style="width:45%">
						  </colgroup>  
					  <tr>
					  	<th>Day</th>
					  	<th colspan="2">Activity Summary</th>
					  </tr>
					  
					  <%
					    
					    if(tourList != null && !tourList.isEmpty())
					  	{ 
							for(int i=0; i < tourList.size(); i++) 
							{
								ArrayList<Object> currentTour =  (ArrayList<Object>) tourList.get(i);
								
								ArrayList<ArrayList<String>> landVouchers = new ArrayList<>();
								ArrayList<ArrayList<String>> hotelVouchers = new ArrayList<>();
								
								String dayValue = currentTour.get(Itinerary.TRAVEL_DATE).toString();
								String activityTemplatesValue = "";
								String activityValue = "";	
								String searchCityValue = "";						
								String searchCountryValue = "";
								int landServiceSize = 1; 
								int hotelSize = 1;
								
	
								if(currentTour.size() > Itinerary.ACTIVITY_TEMPLATE) activityTemplatesValue = currentTour.get(Itinerary.ACTIVITY_TEMPLATE).toString();
								if(currentTour.size() > Itinerary.ACTIVITY) activityValue = currentTour.get(Itinerary.ACTIVITY).toString();
								if(currentTour.size() > Itinerary.CITY) searchCityValue = currentTour.get(Itinerary.CITY).toString();
								if(currentTour.size() > Itinerary.COUNTRY) searchCountryValue = currentTour.get(Itinerary.COUNTRY).toString();
								if(currentTour.size() > Itinerary.LAND_VOUCHERS && currentTour.get(Itinerary.LAND_VOUCHERS) instanceof ArrayList)
								{
									int tempSize = ((ArrayList<ArrayList<String>>) currentTour.get(Itinerary.LAND_VOUCHERS)).size();
									if(tempSize > landServiceSize) landServiceSize = tempSize;

									landVouchers = ((ArrayList<ArrayList<String>>) currentTour.get(Itinerary.LAND_VOUCHERS));
									
								}
								if(currentTour.size() > Itinerary.HOTEL_VOUCHERS && currentTour.get(Itinerary.HOTEL_VOUCHERS) instanceof ArrayList)
								{	
									int tempSize = ((ArrayList<ArrayList<String>>) currentTour.get(Itinerary.HOTEL_VOUCHERS)).size();
									if(tempSize > landServiceSize) hotelSize = tempSize;

									hotelVouchers = ((ArrayList<ArrayList<String>>) currentTour.get(Itinerary.HOTEL_VOUCHERS));
									
								}
			
						%>
			  
							  <tr>
								
							    <td rowspan="3" id="row<%=i%>">
									<textarea class="textfield days" name="<%=day+i%>" readonly><%=dayValue %> </textarea>   
								</td>
								
							   
								
							    <td colspan="2">						
								    	<div class="search_div full_width_div" >
								    		
									    	<select class="float_left editable-select countries" name="<%=searchCountry+i %>" value="<%=searchCountryValue%>" placeholder="Country">
										    	<% for(int t=0; t < tourCountries.size(); t++) { %>
													<option class="selected"><%=tourCountries.get(t) %></option>
												<% } %>	
									    	</select>
									    	<select class="float_left editable-select cities" name="<%=searchCity+i %>"  value="<%=searchCityValue %>" placeholder="City">
									    		<% for(int c=0; c < tourCities.size(); c++) { %>
													<option class="selected"><%=tourCities.get(c) %></option>
												<% } %>
									    	</select>
									    	<input type="submit" class="float_left itinerary_buttons search_buttons" id="search" name="<%=searchButton+i %>" value="<%=searchButton%>">
								    	</div>
								    	
								    	<div class="activity_template_div full_width_div">
									    	<div class="float_left text"><%=activityTemplates %>: </div>
									    	<select class="float_left editable-select activity_templates"  name="<%=activityTemplates+i %>" value="<%=activityTemplatesValue %>" >
												<%if(activityTemplateList != null && !activityTemplateList.isEmpty()) { %>
													<% for(int j=0; j < activityTemplateList.size(); j++) { %>
														<option class="selected"><%=activityTemplateList.get(j) %></option>
													<% } %>
												<% } %>
									    		
									    	</select>
									    	<input type="submit" class="float_left itinerary_buttons get_activity_buttons" id="getActivity" name="<%=getActivityButton+i %>" value="<%=getActivityButton%>">
								    	</div>
	    	

							    </td>
							  </tr>
							  
							  
					  
							  <tr>
							  	  <td colspan="2"><textarea class="textfield" name="<%=activity+i %>" placeholder="<%=activity+" for "+dayValue %>"><%=activityValue %></textarea></td>
							  </tr>
			
			
			
							  
							  <tr>		  	
							    <td>	  
							    
							    	<% 
							    	
							    	
							    	for(int l=0; l < landServiceSize; l++)  
							    	{ 
							    		
							    		String serviceValue = "";
							    		String vendorValue = "";
							    		String breakfast = "";
							    		String lunch = "";
							    		String dinner = "";

							    		
							    		if(landVouchers.size() > 0)
							    		{
							    			int landVoucherSize = landVouchers.get(l).size();
							    			
							    			if(landVoucherSize > Itinerary.LAND_VOUCHER_SERVICE) serviceValue = landVouchers.get(l).get(Itinerary.LAND_VOUCHER_SERVICE);
							    			if(landVoucherSize > Itinerary.LAND_VOUCHER_VENDOR) vendorValue = landVouchers.get(l).get(Itinerary.LAND_VOUCHER_VENDOR);
							    			if(landVoucherSize > Itinerary.LAND_VOUCHER_BREAKFAST) breakfast = landVouchers.get(l).get(Itinerary.LAND_VOUCHER_BREAKFAST);	
							    			if(landVoucherSize > Itinerary.LAND_VOUCHER_LUNCH) lunch = landVouchers.get(l).get(Itinerary.LAND_VOUCHER_LUNCH);
							    			if(landVoucherSize > Itinerary.LAND_VOUCHER_DINNER) dinner = landVouchers.get(l).get(Itinerary.LAND_VOUCHER_DINNER);

							    		}
							    	 
							    	 
							    	%>
							    		
								    	<div class="service_div full_width_div">
											
								    		<div class="float_left text larger_width_text"><%=landService+" "+(l+1) %>: </div>
								    		<select class="float_left editable-select land_services"  name="<%=l+landService+i %>" value="<%=serviceValue %>" >
												<%if(tourNameList != null && !tourNameList.isEmpty()) { %>
													<% for(int j=0; j < tourNameList.size(); j++) { %>
														<option class="selected"><%=tourNameList.get(j) %></option>
													<% } %>
												<% } %>
									    		
									    	</select>
									    	
									    	<input class="float_left meals" type="checkbox" name="<%=l+landBreakfast+i %>" value="<%=landBreakfast%>" <%= breakfast.equals("1") ? "checked='checked'" : "" %>><div class="float_left text meal_text">B</div>
									    	<input class="float_left meals" type="checkbox" name="<%=l+landLunch+i %>" value="<%=landLunch%>" <%= lunch.equals("1") ? "checked='checked'" : "" %>><div class="float_left text meal_text">L</div>
									    	<input class="float_left meals" type="checkbox" name="<%=l+landDinner+i %>" value="<%=landDinner%>" <%= dinner.equals("1") ? "checked='checked'" : "" %>><div class="float_left text meal_text">D</div>
								    	</div> 
								    	
								    	<div class="vendor_div full_width_div">
								    		<div class="float_left text larger_width_text"><%=vendors+" "+(l+1) %>: </div>
								    		<select class="float_left larger_width_textfield editable-select vendors"  name="<%=l+vendors+i %>" value="<%=vendorValue %>" >
												<%if(vendorList != null && !vendorList.isEmpty()) { %>
													<% for(int j=0; j < vendorList.size(); j++) { %>
														<option class="selected"><%=vendorList.get(j) %></option>
													<% } %>
												<% } %>
									    		
									    	</select>
										</div>		
									
									<% } %>	    	
								     	
								    <div class="full_width_div"><input type="submit" class="itinerary_buttons more_land_service_button" name="<%=moreLandServiceButton+i %>" value="<%=moreLandServiceButton %>"></div>
		    		
							    	<div class="accommodations_div full_width_div">
								    	<div class="float_left text larger_width_text"><%=accommodations %>: </div> 
								    	<select class="float_left editable-select hotels"  name="<%=accommodations+i %>" value="" >
											<%if(hotelList != null && !hotelList.isEmpty()) { %>
												<% for(int j=0; j < hotelList.size(); j++) { %>
													<option class="selected"><%=hotelList.get(j) %></option>
												<% } %>
											<% } %>
								    		
								    	</select>
								    	
								    	<input class="float_left meals" type="checkbox" name="<%=hotelBreakfast+i %>" value="<%=hotelBreakfast %>" checked><div class="float_left text meal_text">B</div>
									    <input class="float_left meals" type="checkbox" name="<%=hotelLunch+i %>" value="<%=hotelLunch %>"><div class="float_left text meal_text">L</div>
									    <input class="float_left meals" type="checkbox" name="<%=hotelDinner+i %>" value="<%=hotelDinner %>"><div class="float_left text meal_text">D</div>
							    	</div>
							    	<div class="full_width_div"><input type="submit" class="itinerary_buttons more_land_service_button" name="<%=moreHotelServiceButton+i %>" value="<%=moreHotelServiceButton %>"></div>
							    </td>
							    
							    
							    
							    <td>
							    
							    	 	<% 
							    	 	for(int l=0; l < landServiceSize; l++)  
							    	 	{ 
								    		String landCityValue = "";
								    		String landCountryValue = "";
								    		
								    		if(landVouchers.size() > 0)
								    		{
								    			if(landVouchers.get(l).size() > Itinerary.LAND_VOUCHER_CITY) landCityValue = landVouchers.get(l).get(Itinerary.LAND_VOUCHER_CITY);
								    			if(landVouchers.get(l).size() > Itinerary.LAND_VOUCHER_COUNTRY) landCountryValue = landVouchers.get(l).get(Itinerary.LAND_VOUCHER_COUNTRY);
								    			
								    		}
							    	 	
							    	 	%>
							    	 	
									    	<div class="land_country_div full_width_div">
										    	<div class="float_left text larger_width_text"><%=landServiceCity+" "+(l+1) %>: </div> 
										    	<select class="float_left larger_width_textfield editable-select land_service_city" name="<%=l+landServiceCity+i %>"  value="<%=landCityValue %>" >
											    		<% for(int c=0; c < tourCities.size(); c++) { %>
															<option class="selected"><%=tourCities.get(c) %></option>
														<% } %>
											    </select>
											    	
										    	
										    </div>
										    
										    <div class="land_city_div full_width_div">							    
										    	<div class="float_left text larger_width_text"><%=landServiceCountry+" "+(l+1) %>: </div> 
										    	<select class="float_left larger_width_textfield editable-select land_service_country" name="<%=l+landServiceCountry+i %>" value="<%=landCountryValue %>" >
											    	<% for(int t=0; t < tourCountries.size(); t++) { %>
														<option class="selected"><%=tourCountries.get(t) %></option>
													<% } %>	
										    	</select>						    
										    </div>
								    
								    <% } %>
								    
								    <div class="full_width_div"><input type="submit" class="itinerary_buttons more_land_service_button" name="<%=moreLandServiceButton+i %>" value="<%=moreLandServiceButton %>"></div>
								    
								    <div class="room_type_div full_width_div">
								    	<div class="float_left text larger_width_text"><%=roomType %>: </div> 
								    	
								    	<input class="float_left number_of_room" type="number" name="room_number" min="1" max="1000" value="1">
								    	
								    	<select class="float_left editable-select room_type"  name="<%=roomType+i %>" value="" >
											<%if(hotelList != null && !hotelList.isEmpty()) { %>
												<% for(int j=0; j < hotelList.size(); j++) { %>
													<option class="selected"><%=hotelList.get(j) %></option>
												<% } %>
											<% } %>
								    		
								    	</select>
								    	
								    	<select class="float_left editable-select room_category"  name="room_category" value="" >
											<%if(hotelList != null && !hotelList.isEmpty()) { %>
												<% for(int j=0; j < hotelList.size(); j++) { %>
													<option class="selected"><%=hotelList.get(j) %></option>
												<% } %>
											<% } %>
								    		
								    	</select>
								    	
								    
								    </div>
								    <div class="full_width_div"><input type="submit" class="itinerary_buttons more_land_service_button" name="<%=moreHotelServiceButton+i %>" value="<%=moreHotelServiceButton %>"></div>
							    </td>
							    
							  </tr>
							  
					  <% } }%>
  
					</table>
					
					
					<p class="failedMessages" ><%=failed %></p>
					
					<div>	
						<input type="submit" class="itinerary_buttons" id="save" name="save" value="Save">									
					</div>
					
					<a id ="scroll-down" href="<%=rowId %>"></a>
					
					
					
				</div>
				

			</form>
		</div>
	
	
	
	
	
	
	
	
	
	
	
	</div>
</body>
</html>