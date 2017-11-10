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

<%@include file="Website Template.jsp"%>

		<%
		PassengerDao pdao = new PassengerDao();   
	
	
		// name variables
		String customer_id = NewpageDao.getNewCustomerId();
		String search_box_lastname = PassengerDao.getPassengerSearchBoxLastname();
		String departure_date_from = PassengerDao.getPassengerDepartureDateFrom();
		String departure_date_to = PassengerDao.getPassengerDepartureDateTo();
		String get_customer_ids = PassengerDao.getPassengerGetCustomerIds();
		String get_passengers = PassengerDao.getPassengerGetPassengers();
		String add = PassengerDao.getPassengerAdd();
		String delete = PassengerDao.getPassengerDelete();
		
		
		String p_lastname = LoginDao.getRegisterLastname();
		
		// get customer id list
		ArrayList<String> customerIdList = (ArrayList<String>)request.getAttribute(PassengerDao.PASSENGER_CUSTOMER_IDS);

		
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
		
	
		// populate existing customer ids from database
		//ArrayList<String> customerIdValues = pdao.getCustomerIds();
		ArrayList<String> customerIdValues = new ArrayList<String>();
	
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
					
										
						<input type="submit" class="search_box_buttons itinerary_buttons" id="getPassengers" name="<%=get_passengers%>" value="<%=get_passengers%>">

					</div>
					
				</fieldset>
				

				
				<br><br><br><br><br><br>
				
				<div id="itinerary_div">

					<table style="width:100%">
						 <colgroup>
						    <col style="width:10%">
						    <col style="width:90%">
						  </colgroup>  
					  <tr>
					    <th>Day:</th>
					    <th>Activity Summary:</th>
					  </tr>
					  
					  
					  <tr>
					    <td rowspan="2">Sep. 06</td>
					    <td><textarea class="activities" name="activities">Arrive Bangkok: Upon arrival in Bangkok, be met and transferred to Millennium Hilton Bangkok Hotel for your next 4 nights' accommodations</textarea></td>
					  </tr>
					  
					  <tr>
					    <td>
					    	<div>
						    	<select class="editable-select countries"  name="countries"  value="" placeholder="Country"></select>
						    	<select class="editable-select cities"  name="cities"  value="" placeholder="City"></select>
						    	<input type="submit" class="itinerary_buttons" id="getHotels" name="getHotels" value="Get Hotels">
					    	</div>
					    	<p>Accommodations:</p> 
					    	<select class="editable-select hotels"  name="hotels"  value="" placeholder="Hotel"></select>
					    	
					    </td>
					  </tr>
					   
					   <tr>
					    <td rowspan="2">Sep. 07</td>
					    <td><textarea class="activities" name="activities">Bangkok: This morning, travel outside the hustle and bustle of Bangkok to the world renowned Floating Market where for over a hundred years, villagers transport their agricultural products by canals to the center of trading at Damnoen Saduak. This incredible network is a labyrinth of almost two hundreds branched canals lined with ancient Thai wooden houses and colorfully decorated by small wooden boats that contain fresh fruits and vegetables. Afterwards, visit the interesting Train Market where local vendors and patrons line the train tracks, quickly disperse and reassemble as the train comes and goes. Return to your hotel and enjoy the balance of the afternoon at leisure. ***Note: as an alternative, you may visit to quiet / less touristy floating market at Tha Kha (opens on weekends only). Please consult with your guide based on your preference.
This evening, enjoy a night tour of Bangkok's night spots that include Chinatown, Flower Market and Khao San Road, also known as the center of the "backpacking universe" comprise</textarea></td>
					  </tr>
					  
					  <tr>
					    <td>Accommodations: Shangri-La Bangkok Hotel (Deluxe Room)</td>
					  </tr> 
					  
					  
					  
					  
					  
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