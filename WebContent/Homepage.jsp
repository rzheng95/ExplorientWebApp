<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.login.LoginDao"%>
<%@ page import="com.login.Logout"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<link rel="stylesheet" href="CSS/Homepage.css" type="text/css">
		<title>Explorient Home Page</title>
	</head>
	
<body>
	<%	
	// prevents backing after logout
	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");

	// HTTP 1.0
	response.setHeader("Pragma", "no-cache");
	
	// Proxies
	response.setHeader("Expires", "0");

	LoginDao dao = new LoginDao();
	
	Cookie[] emailAndNonceCookies = request.getCookies();
	
	String nonce = dao.getNonceCookie(emailAndNonceCookies);	
	
	// Cookie || Database || Session
	if(request.getSession(false)!=null)
	{
		if(nonce.equals("") || !dao.checkNonce(nonce) || session.getAttribute(LoginDao.getSessionName())==null)
		{
			response.sendRedirect("Login.jsp");
			return;
		}
	}
	else
	{
		RequestDispatcher rd = request.getRequestDispatcher("Logout");
		rd.forward(request,response);
		
		response.sendRedirect("Login.jsp");
		return;
	}
	
	
	String email = LoginDao.getLoginEmail();
	String search = LoginDao.getHomepageNavigationSearch();
	String booking = LoginDao.getHomepageNavigationBooking();
	String logout = LoginDao.getHomepageNavigationLogout();
	
	
	String emailValue = "";
	String sessionValue = request.getSession(false).getAttribute(LoginDao.getSessionName()).toString();
	if(!sessionValue.isEmpty() && sessionValue.contains("="))
		emailValue = sessionValue.split("=")[0];
	
	
	
	%>
	
	<form action="Navigation" method="post">
		<ul>
		  <li><input class="nav_buttons" id="<%=email %>" type="submit" name="<%=email %>" value="<%=emailValue %>" /></li>
		  <li><input class="nav_buttons" type="submit" name="<%=search %>" value="<%=LoginDao.CapitalizeFirstLetter(search) %>" /></li>
		  <li><input class="nav_buttons" type="submit" name="<%=booking %>" value="<%=LoginDao.CapitalizeFirstLetter(booking) %>" /></li>
		  <li><input class="nav_buttons" type="submit" name="<%=logout %>" value="<%=LoginDao.CapitalizeFirstLetter(logout) %>" /></li>
		</ul>
	</form>
	
	
	<div style="margin-left:15%;padding:1px 16px;height:100%;">
	
		<jsp:include page="HTML/Header.html" />
	
		Welcome to homepage. You are logged in as <%=email %>
		
		
		
		<form action="Logout" method="post">
			<input type="submit" value="Log out">
		</form>
	
	</div>
</body>
</html>