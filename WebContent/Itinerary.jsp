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
	
		
		// hotel country list
		ArrayList<String> countries = hdao.getCountries();
		if(countries != null && countries.size() != 0)
			Collections.sort(countries);
		
		
		// hotel city list
		ArrayList<String> cities = hdao.getCities();
		if(cities != null && cities.size() != 0)
			Collections.sort(cities);
	
		// search box name variables
		String customer_id = NewpageDao.getNewCustomerId();
		String search_box_lastname = PassengerDao.getPassengerSearchBoxLastname();
		String departure_date_from = PassengerDao.getPassengerDepartureDateFrom();
		String departure_date_to = PassengerDao.getPassengerDepartureDateTo();
		String get_customer_ids = PassengerDao.getPassengerGetCustomerIds();
		String get_itinerary = ItineraryDao.getItineraryGetItineraryButton();
		String p_lastname = LoginDao.getRegisterLastname();
		
		
		// itinerary button
		String getHotelsButton = HotelDao.getHotelGetHotelsButton();
		
		// hotel search textfields
		String hotelCountry = ItineraryDao.getItineraryHotelCountry();
		String hotelCity = ItineraryDao.getItineraryHotelCity();
		
		// hotel list
		ArrayList<String> hotelList = (ArrayList<String>)request.getAttribute(ItineraryDao.HOTEL_LIST);
		
		// get customer id list
		ArrayList<String> customerIdList = (ArrayList<String>)request.getAttribute(PassengerDao.PASSENGER_CUSTOMER_IDS);
		
		// get tour list
		ArrayList<ArrayList<String>> tourList = (ArrayList<ArrayList<String>>)request.getAttribute(ItineraryDao.TOUR_LIST);
		

		
		// failed
		String failed = (String)request.getAttribute(PassengerDao.PASSENGER_FAILED);
		
		
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
		
	
		// get customer id if user just created a booking.
		HashMap<String, String> map = NewpageDao.getHashMap();	
		if(map.get(NewpageDao.getNewCustomerId()) != null)
			customerIdValue = map.remove(NewpageDao.getNewCustomerId());
	%>
	<script src="JS/autosize.min.js"></script>
	<script>
		
	$(document).ready(function() {
			
		autosize($('textarea'));
			
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
							<select class="editable-select" id="customer_id" name="<%=customer_id%>"  value="<%=customerIdValue%>" placeholder="Customer ID">
								<% if(customerIdList != null && !customerIdList.isEmpty()){ %>
									<% for(int i=0; i < customerIdList.size(); i++) { %>
										<option class="selected"><%=customerIdList.get(i) %></option>
								<% } }%>
							</select>
						
											
							<input type="submit" class="search_box_buttons itinerary_buttons" id="getPassengers" name="<%=get_itinerary%>" value="<%=get_itinerary%>">
	
						</div>
						
					</fieldset>
				</div>

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
								<% String day = tourList.get(i).get(Itinerary.TRAVEL_DATE); %>
								<% String activity = ""; %>
								<% String accommodation = ""; %>
								<% if(tourList.get(i).size() > 1) {%>
									<% activity = tourList.get(i).get(Itinerary.ACTIVITY); %>
									<% if(tourList.get(i).get(Itinerary.HOTEL) != null) { %>
										<% accommodation = tourList.get(i).get(Itinerary.HOTEL); }%>
								<% } %>
								

							  <tr>
							    <td rowspan="2">
									<p class="days" name="days"><%=day %> </p>   
								</td>
								
								
							    <td><textarea class="textfield" name="<%=+i %>"><%=activity %></textarea></td>
							  </tr>
							  
							  
							  
							  <tr>
							  	
							    <td>
							    	<div class="hotel_search_div">
								    	<select class="editable-select countries"  name="<%=hotelCountry+i %>"  value="" placeholder="Country">
									    	<% for(int h=0; h < countries.size(); h++) { %>
												<option class="selected"><%=countries.get(h) %></option>
											<% } %>	
								    	</select>
								    	<select class="editable-select cities"  name="<%=hotelCity+i %>"  value="" placeholder="City">
								    		<% for(int c=0; c < cities.size(); c++) { %>
												<option class="selected"><%=cities.get(c) %></option>
											<% } %>
								    	</select>
								    	<input type="submit" class="itinerary_buttons" id="getHotels" name="<%=getHotelsButton+i %>" value="<%=getHotelsButton%>">
							    	</div>
							    	
							    	<div class="accommodations_div">
								    	Accommodations: <select class="editable-select hotels"  name="<%=ItineraryDao.HOTELS+i %>" value="<%=accommodation %>" >
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
				
				</div>
				
				
				<p class="failedMessages" ><%=failed %></p>
				
				<div>	
					<input type="submit" class="itinerary_buttons" id="save" name="save" value="Save">									
				</div>
				
				
				
				

			</form>
		</div>
	
	
	
	
	
	
	
	
	
	
	
	</div>
</body>
</html>