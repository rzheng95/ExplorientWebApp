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
<title>Explorient | Booking Created</title>

	<style>
		#bc_h2{
			font-family: "Times New Roman", Times, serif;
			color: grey;
		}
		
		#customerID{
			color: #8b7116;
		}
		
		.bc_li{
			padding: 0em;
			color: grey;  
		}
		
		.bc_li:hover{
			color: #8b7116;
		}
		
		.bc_buttons{
			color: grey;
			border: none;
			outline: none;
			background: none;
			font: 1em;
			width: 100%;
			text-align: left;
			padding: 2em 0em 2em 0em;
			cursor: pointer;
		}
		
		.bc_buttons:hover{
			color: #8b7116;
		}
		
			
	</style>
</head>
<body>
	<%@include file="Website Template.jsp"%>
	<%
	
		String addPassenger = NewpageDao.getAddPassenger();
		String addShippingAddress = NewpageDao.getAddShippingAddress();
		String updateBooking = NewpageDao.getUpdateBooking();
	
		String customerID = "";
		HashMap<String, String> map = NewpageDao.getHashMap();	
		if(map.get(NewpageDao.getNewCustomerId()) != null)
			customerID = map.get(NewpageDao.getNewCustomerId());
		 
	%>
	<div class="content_wrap">
	<h2 id="bc_h2">Booking <span id="customerID"><%=customerID %></span> Created</h2>
	<form action="BookingCreated" method="post">
		<ul id="bc_ul">
			<li class="bc_li"><input class="bc_buttons" type="submit" name="<%=addPassenger %>" value="<%=addPassenger+" "+customerID%>"/></li>
			<li class="bc_li"><input class="bc_buttons" type="submit" name="<%=addShippingAddress %>" value="<%=addShippingAddress+" "+customerID%>" /></li>
			<li class="bc_li"><input class="bc_buttons" type="submit" name="<%=updateBooking %>" value="<%=updateBooking+" "+customerID%>" /></li>
		</ul>
		
		
	</form>
	
	
	
	
	</div>
</body>
</html>