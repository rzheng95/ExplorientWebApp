<!-- 

  This file is part of Explorient Web App
  Copyright (C) 2016-2019 Richard R. Zheng
 
  https://github.com/rzheng95/ExplorientWebApp
  
  All Right Reserved.
 
 -->

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.rzheng.login.LoginDao"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<%
		// prevents backing after logout
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	
		// HTTP 1.0
		response.setHeader("Pragma", "no-cache");
		
		// Proxies
		response.setHeader("Expires", "0");
		
		if(session.getAttribute(LoginDao.getSessionName())!=null)
		{
			response.sendRedirect(LoginDao.HOMEPAGE);
			return;
		}

		
        String email = LoginDao.getLoginEmail();
        String password = LoginDao.getLoginPassword();
        String failed = (String)request.getAttribute(LoginDao.LOGIN_FAILED);

        if(failed == null)
		{
			failed = " ";
		}
        
        String emailValue = (String)request.getAttribute(LoginDao.LOGIN_EMAIL);
        
		LoginDao dao = new LoginDao();
		Cookie[] emailAndNonceCookies = request.getCookies();		
		if(emailValue != null && emailValue.equals(""))
			emailValue = "";
		if(emailValue == null)
			emailValue = dao.getEmailCookie(emailAndNonceCookies);
	%>



	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<link rel="stylesheet" href="CSS/Login.css" type="text/css">
		<title>Explorient | Login</title>
	</head>
<body>
		<div id="logo_div">
			<a href="http://www.explorient.com"> <img id="logo" src="Image/explorient logo1.png" alt="logo" /> </a>
		</div>
		<div id="content_wrap">
			<div id="login_div">
			
				<form action="Login" method="post">
						<fieldset id="fieldset">
							<fieldset id="fieldset_background">
								<legend id="legend" align= "center"><img id="legend_img" src="Image/Logo gray.png" style="width:3em;height:3em;"></legend> 
							</fieldset>
						<legend id="legend" align= "center"><img id="legend_img" src="Image/Logo gray.png" style="width:3em;height:3em;"></legend> <br/>
						
						<div id="login_inner_div">
							<input class="textfields" type="text" name="<%=email%>" value="<%=emailValue%>" placeholder="<%=LoginDao.CapitalizeFirstLetter(email) %>">  <br/>
			
							<input class="textfields" type="password" name="<%=password%>" placeholder="<%=LoginDao.CapitalizeFirstLetter(password) %>"> <br/><br/><br/>			
							<p class="failedMessages" ><%=failed %></p> 
							
							<input class="buttons" type="submit" name="sumbit" value="Log in"> <br/> <br/>
							
							
							<a class="links" id="register" href="Register">Register</a><br/><br/>
						</div>
						
					</fieldset>		
				</form>
			</div>
		</div>
</body>
</html>