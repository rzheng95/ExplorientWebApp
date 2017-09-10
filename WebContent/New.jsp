<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Explorient | New</title>
	<link rel="stylesheet" href="CSS/New.css" type="text/css">
</head>
<body>

	<%@include file="Website Template.jsp"%>

	<script>
	$(document).ready(function() {
		
		/* drop down items listeners */
		
		$('#agent')
	    .editableSelect()
	    .on('select.editable-select', function (e, li) {
	        $('#agent_text').html(
	        	'AGT: '+li.text()
	        );
	    });
		
		$('#destination')
	    .editableSelect()
	    .on('select.editable-select', function (e, li) {
	        $('#destination_text').html(
	        	'DES: '+li.text()
	        );
	    });
		
		$('#air')
	    .editableSelect()
	    .on('select.editable-select', function (e, li) {
	        $('#air_text').html(
	        	'AIR: '+li.text()
	        );
	    });
		
		$('#tour_package')
	    .editableSelect()
	    .on('select.editable-select', function (e, li) {
	        $('#tour_package_text').html(
	        	'PKG: '+li.text()
	        );
	    });
		

	});
		

		// select all text when focus
	window.onload = function() {	
		var dropdowns = document.getElementsByClassName("es-input");
		for(var i=0; i<dropdowns.length; i++)
		{
			dropdowns[i].onfocus = function() {
				this.setSelectionRange(0, this.value.length);
			}
		}
		
		/* on key up listeners */
		
		// customer id
		var customer_id = document.getElementById("customer_id");
		var customer_id_text = document.getElementById("customer_id_text");

		customer_id.onkeyup = function() {
			customer_id_text.innerHTML = 'CID: '+this.value;
		}		
		
		// destination
		var destination = document.getElementById("destination");
		var destination_text = document.getElementById("destination_text");

		destination.onkeyup = function() {
			destination_text.innerHTML = 'DES: '+this.value;
		}
		
		// air
		var air = document.getElementById("air");
		var air_text = document.getElementById("air_text");

		air.onkeyup = function() {
			air_text.innerHTML = 'AIR: '+this.value;
		}
		
		// tour package
		var tour_package = document.getElementById("tour_package");
		var tour_package_text = document.getElementById("tour_package_text");

		tour_package.onkeyup = function() {
			tour_package_text.innerHTML = 'PKG: '+this.value;
		}
		
		// date of departure
		var date_of_departure = document.getElementById("date_of_departure");
		var date_of_departure_text = document.getElementById("date_of_departure_text");

		date_of_departure.onchange = function() {
			date_of_departure_text.innerHTML = 'DOP: '+this.value;
		}
		
		// date of return
		var date_of_return = document.getElementById("date_of_return");
		var date_of_return_text = document.getElementById("date_of_return_text");

		date_of_return.onchange = function() {
			date_of_return_text.innerHTML = 'DOR: '+this.value;
		}
		
	};
	
	</script>

	<%
		NewpageDao npdao = new NewpageDao();
	
		// database variables
		ArrayList<String> agents = npdao.getAgent(); 
		ArrayList<String> airs = npdao.getAir();
		ArrayList<String> tourPackages = (ArrayList<String>) request.getAttribute("tourPackages");
	
		ArrayList<String> countries = npdao.getCountries();
	 
		// name variables
		String new_agent = NewpageDao.getNewAgent(); 
		String customer_id = NewpageDao.getNewCustomerId();
		String destination = NewpageDao.getNewDestination();
		String air = NewpageDao.getNewAir();
		String date_of_departure = NewpageDao.getNewDateOfDeparture();
		String date_of_return = NewpageDao.getNewDateOfReturn();
		String country = NewpageDao.getCountry();
		String getPackages = NewpageDao.getGetPackages();
		String tour_package = NewpageDao.getNewTourPackage(); 
		
		// failed
		String failed = (String)request.getAttribute(NewpageDao.NEWPAGE_FAILED);
		
		// previous entered values
		String agentValue = "", customerIdValue = "", destinationValue = "", airValue = "", dateOfDepartureValue = "", dateOfReturnValue = "", countryValue = "", tourPackageValue = "";
		if(failed == null)
		{
			failed = " ";
		}

       	String enteredValues = (String)request.getAttribute(NewpageDao.NEWPAGE);
       	if(enteredValues != null && !enteredValues.isEmpty())
       	{
	       	String[] fragment = enteredValues.split("=", -1);
	       	if(fragment.length == NewpageDao.NEW_ENTERED_VALUE_LENGTH)
			{
	       		agentValue = fragment[NewpageDao.AGENT_VALUE_INDEX];
	       		customerIdValue = fragment[NewpageDao.CUSTOMER_ID_VALUE_INDEX];
	       		destinationValue = fragment[NewpageDao.DESTINATION_VALUE_INDEX];
	       		airValue = fragment[NewpageDao.Air_VALUE_INDEX];
	       		dateOfDepartureValue = fragment[NewpageDao.DATE_OF_DEPARTURE_VALUE_INDEX];
	       		dateOfReturnValue = fragment[NewpageDao.DATE_OF_RETURN_VALUE_INDEX];
	       		countryValue = fragment[NewpageDao.COUNTRY_INDEX];
	       		tourPackageValue = fragment[NewpageDao.TOUR_PACKAGE_VALUE_INDEX];
			}
       	}
		
	%>

	<div class="content_wrap">
		<div class="new_booking">
			<form action="New" method="post">	
			
				<div class="section_wrap">
					<div class="left_div">
						<p class="new_tags" id="test">Agent:</p>
						<select class="editable-select font_choice" id="agent" name="<%=new_agent%>"  value="<%=agentValue%>">
							<% for(int i=0; i < agents.size(); i++) { %>
								<option class="selected"><%=agents.get(i) %></option>
							<% } %>
						</select>						
					</div>
									
					<div class="right_div">
						<p class="new_tags">Customer ID:</p>
						<select class="editable-select font_choice" id="customer_id" name="<%=customer_id%>"  value="<%=customerIdValue%>">
						</select>						
					</div>
				</div>
				
				
				
				<div class="section_wrap">
				
					<div class="left_div">
						<p class="new_tags">Destination: (separate with " - " if more than one country. Ex. China-Japan)</p>
						<select class="editable-select font_choice" id="destination" name="<%=destination%>" value="<%=destinationValue%>">
						</select>			
					</div>
					
					
					<div class="right_div">
						<p class="new_tags">Air:</p> 
						<select class="editable-select font_choice" id="air" name="<%=air%>" value="<%=airValue%>">
							<% for(int i=0; i < airs.size(); i++) { %>
								<option><%=airs.get(i) %></option>
							<% } %>
						</select>		
					</div>	
				</div>
				
					
				<div class="section_wrap">
					<div class="left_div">
						<p class="new_tags">Date of Departure:</p> 
						 <input type="text" class="datepicker font_choice" id="date_of_departure" name="<%=date_of_departure%>" value="<%=dateOfDepartureValue%>">
					</div>
					
					
					
					<div class="right_div">
						<p class="new_tags">Date of Return:</p> 
						<input type="text" class="datepicker font_choice" id="date_of_return" name="<%=date_of_return%>" value="<%=dateOfReturnValue%>">
					</div>				
				</div>
				
				
				<div class="section_wrap">
					
					<div class="right_div country_div">
						<p class="new_tags">Country:</p> 
						<select class="editable-select font_choice" id="<%=country%>" name="<%=country%>" value="<%=countryValue%>">
							<% for(int i=0; i < countries.size(); i++) { %>
								<option><%=countries.get(i) %></option>
							<% } %>
						</select>		
						<input type="submit" id="getPackages" name="<%=getPackages%>" value="<%=getPackages%>"> 
					</div>

					

				
					<div class="left_div">
						<p class="new_tags">Tour Pacakge:</p> 
						<select class="editable-select font_choice" id="tour_package" name="<%=tour_package%>" value="<%=tourPackageValue%>">
							<% if(tourPackages != null && !tourPackages.isEmpty()) { %>
							<% for(int i=0; i < tourPackages.size(); i++) { %>
								<option><%=tourPackages.get(i) %></option>
							<% } }%>
						</select>		
					</div>
				</div>
				
				<p class="failedMessages" ><%=failed %></p>
				
				<input class="new_buttons" type="submit" value="Create"/>
			</form>
	
		</div>
			
		<div class="new_booking">

			<div class="display_panel">
				<div id="display_panel_background"></div> 
				<h2>Booking Information</h2>
				<p class="booking_information" id="agent_text"><%=agentValue %></p>
				<p class="booking_information" id="customer_id_text"><%=customerIdValue %></p>
				<br><br>
				<p class="booking_information" id="destination_text"><%=destinationValue %></p>
				<p class="booking_information" id="air_text"><%=airValue %></p>
				<br><br>
				<p class="booking_information" id="date_of_departure_text"><%=dateOfDepartureValue %></p>
				<p class="booking_information" id="date_of_return_text"><%=dateOfReturnValue %></p>
				<br><br>
				<p class="booking_information" id="tour_package_text"><%=tourPackageValue %></p>
			</div>
		</div>


		
	</div>

	
</body>
</html>