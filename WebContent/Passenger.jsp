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
			
			
			$('#customer_id')
		    .editableSelect()
		    .on('select.editable-select', function (e, li) {
		        $('#passenger_h3').html(
		        	'Passenger List for '+li.text()
		        );
		    });
			
	
		});
	
	
		window.onload = function() {
			var dropdowns = document.getElementsByClassName("es-input");
			for(var i=0; i<dropdowns.length; i++)
			{
				dropdowns[i].onfocus = function() {
					this.setSelectionRange(0, this.value.length);
				}
			}
		};
	
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
		
		if(failed == null)
		{
			failed = " ";
		}
		
	
		
		ArrayList<String> customerIdValues = pdao.getCustomerIds();
	
		String customerIdValue = "";
		HashMap<String, String> map = NewpageDao.getHashMap();	
		if(map.get(NewpageDao.getNewCustomerId()) != null)
			customerIdValue = map.remove(NewpageDao.getNewCustomerId());
	%>
	
	<div class="content_wrap">
		<div class="passenger_div">
			<form action="Passenger" method="post">
				
				<div id="customer_id_div">
					<select class="editable-select font_choice" id="customer_id" name="<%=customer_id%>"  value="<%=customerIdValue%>" placeholder="Customer ID">
						<% for(int i=0; i < customerIdValues.size(); i++) { %>
							<option class="selected"><%=customerIdValues.get(i) %></option>
						<% } %>
					</select>
				</div>
									
				<div id="search_div">
					<input type="submit" class="passenger_buttons" id="search" name="search" value="Search">
				</div>
			
				
				<div id="hidden_div">
					<select class="editable-select font_choice" id="title" name="<%=p_title %>"  value="" placeholder="<%=LoginDao.CapitalizeFirstLetter(p_title) %>">
					</select>
					
					<select class="editable-select font_choice" id="firstname" name="<%=p_firstname %>"  value="" placeholder="<%=LoginDao.CapitalizeFirstLetter(p_firstname) %>">
					</select>
					
					<select class="editable-select font_choice" id="middlename" name="<%=p_middlename %>"  value="" placeholder="<%=LoginDao.CapitalizeFirstLetter(p_middlename) %>">
					</select>
					
					<select class="editable-select font_choice" id="lastname" name="<%=p_lastname %>"  value="" placeholder="<%=LoginDao.CapitalizeFirstLetter(p_lastname) %>">
					</select>
					
					<div>					
						<input type="submit" class="passenger_buttons addAndUpdate" id="add" name="add" value="Add">
						<input type="submit" class="passenger_buttons addAndUpdate" id="update" name="update" value="Update">
					</div>
				</div>
				
				<p class="failedMessages" ><%=failed %></p>
				

			</form>
		</div>
		
		<div class="passenger_div">
			<div class="display_panel">
				<h3 id="passenger_h3">Passenger List</h3>
				
					<%if(passengerList != null && !passengerList.isEmpty()) { %>
					<%	for(int i=0; i < passengerList.size(); i++) { %>
						<p class="passengers"><%=passengerList.get(i) %></p>
						<% } %>
					<% } %>
				
			</div>
		</div>
		
		
		<!--  
		<div style="color: black;">Search Icons made by 
			<a style="text-decoration: none; color: black; " href="https://www.flaticon.com/authors/madebyoliver" title="Madebyoliver">Madebyoliver</a> from 
			<a style="text-decoration: none; color: black; "href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a> is licensed by 
			<a style="text-decoration: none; color: black; "href="http://creativecommons.org/licenses/by/3.0/" title="Creative Commons BY 3.0" target="_blank">CC 3.0 BY</a>
		</div>
		-->
	</div>
	
</body>
</html>



















