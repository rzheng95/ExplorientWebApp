<!-- 

  This file is part of Explorient Web App
  Copyright (C) 2016-2019 Richard R. Zheng
 
  https://github.com/rzheng95/ExplorientWebApp
  
  All Right Reserved.
 
 -->

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.login.LoginDao"%>
<%@ page import="com.homepage.HomepageDao"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Explorient | Home Page</title>
	</head>
	
<body>
	<%@include file="Website Template.jsp"%>
	
	<div style="margin-left:13.5%;padding:1px 16px;height:100%;">
	
		Welcome to home page, <%=firstname %>. You are logged in as <%=emailValue %>
		
		<div>
			<!--
			<TABLE>
				<% for(int row=1; row <= 5; row++) { %>
				    <TR>
				<%      for(int col=1; col<=10; col++) { %>
				        <TD> (<%=col%>, <%=row%>)
				        </TD>
				        <% } %>
				    </TR>
				<% } %>
			</TABLE>
		    -->
		</div>
	</div>
</body>
</html>