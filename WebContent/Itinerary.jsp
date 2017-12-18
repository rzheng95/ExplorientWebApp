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

<%@ page import="com.itinerary.Itinerary"%>

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
		String accommodations = ItineraryDao.getItineraryAccommodations();
		String activityTemplates = ItineraryDao.getItineraryActivityTemplates();
		String vendors = ItineraryDao.getItineraryVendors();
		
		// itinerary button
		String searchButton = ItineraryDao.getItinerarySearchButton();
		String getActivityButton = ItineraryDao.getItineraryGetActivityButton();
		
		// hotel search textfields
		String hotelCountry = ItineraryDao.getItineraryHotelCountry();
		String hotelCity = ItineraryDao.getItineraryHotelCity();
		String searchCountry = ItineraryDao.getItineraryHotelCountry();
		String searchCity = ItineraryDao.getItineraryHotelCity();
		
		
		// current row id
		String rowId = (String)request.getAttribute(ItineraryDao.ROW_ID);
		
		
		// get tour list
		ArrayList<ArrayList<String>> tourList = (ArrayList<ArrayList<String>>)request.getAttribute(ItineraryDao.TOUR_LIST);
		
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
						    <col style="width:90%">
						  </colgroup>  
					  <tr>
					    <th>Day</th>
					    <th>Activity Summary</th>
					  </tr>
					  
					  <% if(tourList != null && !tourList.isEmpty()){ %> 
							<% for(int i=0; i < tourList.size(); i++) { %>
								<% String dayValue = tourList.get(i).get(Itinerary.TRAVEL_DATE); %>
								<% String activityTemplatesValue = ""; %>
								<% String activityValue = ""; %>	
								<% String searchCityValue = ""; %>						
								<% String searchCountryValue = ""; %>
								

								<% if(tourList.get(i).size() == Itinerary.TOUR_LIST_SIZE) { %>
									<% activityTemplatesValue = tourList.get(i).get(Itinerary.ACTIVITY_TEMPLATE); %>
									<% activityValue = tourList.get(i).get(Itinerary.ACTIVITY); %>
									<% searchCityValue = tourList.get(i).get(Itinerary.CITY); %>
									<% searchCountryValue = tourList.get(i).get(Itinerary.COUNTRY); %>
									
									
								<%}%>
								
 							  
							  <tr>
								
							    <td rowspan="3" id="row<%=i%>">
									<textarea class="textfield days" name="<%=day+i%>" readonly><%=dayValue %> </textarea>   
								</td>
								
							   
								
							    <td>						
								    	<div class="div_width search_div" >
								    		
									    	<select class="editable-select countries" name="<%=searchCountry+i %>" value="<%=searchCountryValue%>" placeholder="Country">
										    	<% for(int t=0; t < tourCountries.size(); t++) { %>
													<option class="selected"><%=tourCountries.get(t) %></option>
												<% } %>	
									    	</select>
									    	<select class="editable-select cities" name="<%=searchCity+i %>"  value="<%=searchCityValue %>" placeholder="City">
									    		<% for(int c=0; c < tourCities.size(); c++) { %>
													<option class="selected"><%=tourCities.get(c) %></option>
												<% } %>
									    	</select>
									    	<input type="submit" class="itinerary_buttons" id="search" name="<%=searchButton+i %>" value="<%=searchButton%>">
								    	</div>
								    	
								    	<div class="div_width activity_template_div">
									    	<span class="text"><%=activityTemplates %>: </span><select class="editable-select activity_templates"  name="<%=activityTemplates+i %>" value="<%=activityTemplatesValue %>" >
												<%if(activityTemplateList != null && !activityTemplateList.isEmpty()) { %>
													<% for(int j=0; j < activityTemplateList.size(); j++) { %>
														<option class="selected"><%=activityTemplateList.get(j) %></option>
													<% } %>
												<% } %>
									    		
									    	</select>
									    	<input type="submit" class="itinerary_buttons" id="getActivity" name="<%=getActivityButton+i %>" value="<%=getActivityButton%>">
								    	</div>
	    	

							    </td>
							  </tr>
							  
							  
							  
							  
							  
							  
							  
							  
							  <tr>
							  	  <td><textarea class="textfield" name="<%=activity+i %>" placeholder="<%=activity+" for "+dayValue %>"><%=activityValue %></textarea></td>
							  </tr>
							  
							  <tr>		  	
							    <td>	  
							    	<div class="div_width service_and_vendor_div">
								    		<span class="text">Land Service 1: </span><select class="editable-select tourName"  name="tourName1" value="" >
												<%if(tourNameList != null && !tourNameList.isEmpty()) { %>
													<% for(int j=0; j < tourNameList.size(); j++) { %>
														<option class="selected"><%=tourNameList.get(j) %></option>
													<% } %>
												<% } %>
									    		
									    	</select>
								    		
								    		<span class="text">Vendor 1: </span><select class="editable-select vendors"  name="<%=vendors+i %>" value="" >
												<%if(vendorList != null && !vendorList.isEmpty()) { %>
													<% for(int j=0; j < vendorList.size(); j++) { %>
														<option class="selected"><%=vendorList.get(j) %></option>
													<% } %>
												<% } %>
									    		
									    	</select>
									    	
									    	
									    	<select class="editable-select countries" name="<%=searchCountry+i %>" value="<%=searchCountryValue%>" placeholder="Country">
										    	<% for(int t=0; t < tourCountries.size(); t++) { %>
													<option class="selected"><%=tourCountries.get(t) %></option>
												<% } %>	
									    	</select>
									    	<select class="editable-select cities" name="<%=searchCity+i %>"  value="<%=searchCityValue %>" placeholder="City">
									    		<% for(int c=0; c < tourCities.size(); c++) { %>
													<option class="selected"><%=tourCities.get(c) %></option>
												<% } %>
									    	</select>
									    	
									    	
								    	</div>  	
							    	<div class="accommodations_div">
								    	<span class="text">Accommodations: </span> <select class="editable-select hotels"  name="<%=accommodations+i %>" value="" >
											<%if(hotelList != null && !hotelList.isEmpty()) { %>
												<% for(int j=0; j < hotelList.size(); j++) { %>
													<option class="selected"><%=hotelList.get(j) %></option>
												<% } %>
											<% } %>
								    		
								    	</select>
								    	
								    	<span class="text">Room type: </span> <select class="editable-select room_type"  name="<%=accommodations+i %>" value="" >
											<%if(hotelList != null && !hotelList.isEmpty()) { %>
												<% for(int j=0; j < hotelList.size(); j++) { %>
													<option class="selected"><%=hotelList.get(j) %></option>
												<% } %>
											<% } %>
								    		
								    	</select>
							    	</div>
							    	
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