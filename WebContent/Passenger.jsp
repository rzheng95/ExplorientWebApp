<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Explorient | Passenger</title>
	<link rel="stylesheet" href="CSS/Passenger.css" type="text/css">
</head>

<body>
	<%@include file="Website Template.jsp"%>
	
	<script>
		$(document).ready(function() {
			
			// populate textfield values when passenger clicked
			 $("a.passengers").click(function(){
				var element = $(this).text().split(" ");
				if(element.length == 4)
				{
				   	$('#title').val( element[0] );
					$('#firstname').val( element[1] );
					$('#middlename').val( element[2] );
					$('#lastname').val( element[3] );
				}
				
			 });
			
		});
	
	
	</script>
	
	<%
		PassengerDao pdao = new PassengerDao();   
	
		ArrayList<String> passengerList = (ArrayList<String>)request.getAttribute(PassengerDao.PASSENGER_LIST);
	
		String customer_id = NewpageDao.getNewCustomerId();
		String p_title = PassengerDao.getPassengerTitle();
		String p_firstname = LoginDao.getRegisterFirstname();
		String p_middlename = PassengerDao.getPassengerMiddlename();
		String p_lastname = LoginDao.getRegisterLastname();
		
		// failed
		String failed = (String)request.getAttribute(PassengerDao.PASSENGER_FAILED);
		
		// enteredValues
		String customerIdValue = "", titleValue = "", firstnameValue = "", middlenameValue = "", lastnameValue = "";
		String enteredValues = (String)request.getAttribute(PassengerDao.PASSENGER);
		if(enteredValues != null)
		{
			String[] fragment = enteredValues.split("=", -1);
			if(fragment.length == PassengerDao.PASSENGER_ENTERED_VALUE_LENGTH)
			{
				customerIdValue = fragment[PassengerDao.CUSTOMER_ID_INDEX];
				titleValue = fragment[PassengerDao.TITLE_INDEX];
				firstnameValue = fragment[PassengerDao.FIRST_NAME_INDEX];
				middlenameValue = fragment[PassengerDao.MIDDLE_NAME_INDEX];
				lastnameValue = fragment[PassengerDao.LAST_NAME_INDEX];
			}
		}
		
		
		if(failed == null)
		{
			failed = " ";
		}
		
	
		// populate existing customer ids from database
		ArrayList<String> customerIdValues = pdao.getCustomerIds();
	
		// get customer id if user just created a booking.
		HashMap<String, String> map = NewpageDao.getHashMap();	
		if(map.get(NewpageDao.getNewCustomerId()) != null)
			customerIdValue = map.remove(NewpageDao.getNewCustomerId());
	%>
	
	<div class="content_wrap">
		<div class="passenger_div">
			<form action="Passenger" method="post">
				
				<div id="customer_id_div">
					<select class="editable-select" id="customer_id" name="<%=customer_id%>"  value="<%=customerIdValue%>" placeholder="Customer ID">
						<% for(int i=0; i < customerIdValues.size(); i++) { %>
							<option class="selected"><%=customerIdValues.get(i) %></option>
						<% } %>
					</select>
				</div>
									
				<div id="search_div">
					<input type="submit" class="passenger_buttons" id="getPassengers" name="getPassengers" value="Get Passengers">
				</div>
			
				
				
				<select class="editable-select no_background_img" id="title" name="<%=p_title %>"  value="<%=titleValue %>" placeholder="<%=LoginDao.CapitalizeFirstLetter(p_title) %>">
				</select>
				
				<select class="editable-select no_background_img" id="firstname" name="<%=p_firstname %>"  value="<%=firstnameValue %>" placeholder="<%=LoginDao.CapitalizeFirstLetter(p_firstname) %>">
				</select>
				
				<select class="editable-select no_background_img" id="middlename" name="<%=p_middlename %>"  value="<%=middlenameValue %>" placeholder="<%=LoginDao.CapitalizeFirstLetter(p_middlename) %>">
				</select>
				
				<select class="editable-select no_background_img" id="lastname" name="<%=p_lastname %>"  value="<%=lastnameValue %>" placeholder="<%=LoginDao.CapitalizeFirstLetter(p_lastname) %>">
				</select>
				
				<p class="failedMessages" ><%=failed %></p>
				
				<div>	
					<input type="submit" class="passenger_buttons addAndDelete" id="add" name="add" value="Add">									
					<input type="submit" class="passenger_buttons addAndDelete" id="delete" name="delete" value="Delete">
				</div>
				
				
				
				

			</form>
		</div>
		
		<div class="passenger_div">
			<div class="display_panel">
			<div id="display_panel_background"></div> 
				<h2 id="passenger_h3">Passenger List</h2>
				
					<%if(passengerList != null && !passengerList.isEmpty()) { %>
					<%	for(int i=0; i < passengerList.size(); i++) { %>
						<a class="passengers"><%=passengerList.get(i).replaceAll("=", " ") %></a>
						<% } %>
					<% } %>
				 
			</div>
		</div>
	</div>
	
</body>
</html>



















