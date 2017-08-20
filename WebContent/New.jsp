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
		
	<%
		ArrayList<String> agents = hpdao.getAgent(); 
	%>

	<div class="content_wrap">
	
		<div class="new_booking">
			<form action="New" method="post">	
			
			<div class="new_div">
				<div class="para_div"> <p>Agent:</p> </div>
				<select class="editable-select" name="editable-select">
					<% for(int i=0; i < agents.size(); i++) { %>
						<option class="agents"><%=agents.get(i) %></option>
					<% } %>
				</select>			
			</div>
			<hr>
							
			<div class="new_div">
				<div class="para_div"> <p>Customer ID:</p> </div>
				<input class="new_textfields" type="text" name="Customer id"/> 
			</div>
			<hr>
			
			<div class="new_div">
				<div class="para_div"> <p>Destination:</p> </div>
				<select class="editable-select" name="editable-select">
					<% for(int i=0; i < agents.size(); i++) { %>
						<option class="agents"><%=agents.get(i) %></option>
					<% } %>
				</select>			
			</div>
			<hr>
			
			<div class="new_div">
				<div class="para_div"> <p>Tour Pacakge:</p> </div>
				<select class="editable-select" name="editable-select">
					<% for(int i=0; i < agents.size(); i++) { %>
						<option class="agents"><%=agents.get(i) %></option>
					<% } %>
				</select>			
			</div>
			<hr>
			
			<div class="new_div">
				<div class="para_div"> <p>Air:</p> </div>
				<select class="editable-select" name="editable-select">
					<% for(int i=0; i < agents.size(); i++) { %>
						<option class="agents"><%=agents.get(i) %></option>
					<% } %>
				</select>			
			</div>
			<hr>
			
			
			
			<div class="new_div">
				<div class="para_div"> <p>Date of Departure:</p> </div>
				 <input type="text" class="datepicker" >

			</div>
			<hr>
			
			
			<div class="new_div">
				<div class="para_div"> <p>Date of Return:</p> </div>
				<input type="text" class="datepicker">
			</div>
			<hr>
				
				<input type="submit" value="Create"/>
			</form>
			
			
		</div>
		

		
	</div>

	
</body>
</html>