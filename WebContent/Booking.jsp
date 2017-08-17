<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.login.LoginDao"%>
<%@ page import="com.homepage.HomepageDao"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="CSS/Website Template.css" type="text/css">
<title>Explorient Booking</title>
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
		response.sendRedirect("Login.jsp");
		return;
	}
	
	
	String email = LoginDao.getLoginEmail();
		String profile = HomepageDao.getHomepageNavigationProfile();
		String setting = HomepageDao.getHomepageNavigationSetting();
	String search = HomepageDao.getHomepageNavigationSearch();
		String hotel = HomepageDao.getHomepageNavigationHotel();
		String vendor = HomepageDao.getHomepageNavigationVendor();
		String agent = HomepageDao.getHomepageNavigationAgent();
	String booking = HomepageDao.getHomepageNavigationBooking();
		String new1 = HomepageDao.getHomepageNavigationNew();
		String itinerary = HomepageDao.getHomepageNavigationItinerary();
		String voucher = HomepageDao.getHomepageNavigationVoucher();
	String logout = HomepageDao.getHomepageNavigationLogout();
	

	
	
	String emailValue = "";
	String sessionValue = request.getSession(false).getAttribute(LoginDao.getSessionName()).toString();
	if(!sessionValue.isEmpty() && sessionValue.contains("="))
		emailValue = sessionValue.split("=")[0];
	
	String firstname = dao.getUserFirstNameByEmail(emailValue);
	String emailStyle = "", searchStyle = "", bookingStlye = "";
	
	String navigation = (String)session.getAttribute(HomepageDao.getHomepageNavigationSelected());

	if(navigation!= null)
	{
		if(navigation.equals(search) || navigation.equals(hotel) || navigation.equals(vendor) || navigation.equals(agent))
			searchStyle = HomepageDao.getCssStylingColorGold();
		else if(navigation.equals(booking) || navigation.equals(new1) || navigation.equals(itinerary) || navigation.equals(voucher))
			bookingStlye = HomepageDao.getCssStylingColorGold();
		else
			emailStyle = HomepageDao.getCssStylingColorGold();
	}
	else
		emailStyle = HomepageDao.getCssStylingColorGold();
	
	%>

	<div class="header">
		<a href="http://www.explorient.com"> <img id="logo" src="Image/explorient logo1.png" alt="logo" /> </a>
		<jsp:include page="HTML/Header.html" />
	</div>
	

	<form action="Navigation" method="post">
		<ul class="countries">
		  <li><input class="nav_buttons" id="<%=email %>" type="submit" name="<%=email %>" value="<%=emailValue %>" style="<%=emailStyle%>" />
			  <ul>
			      <li><input class="nav_small_buttons" type="submit" name="<%=profile %>" value="<%=LoginDao.CapitalizeFirstLetter(profile)%>"></li>
			      <li><input class="nav_small_buttons" type="submit" name="<%=setting %>" value="<%=LoginDao.CapitalizeFirstLetter(setting)%>"></li>
	
		      </ul>
		  </li>
		  <li><input class="nav_buttons" type="submit" name="<%=search %>" value="<%=LoginDao.CapitalizeFirstLetter(search) %>" style="<%=searchStyle%>" />
			  <ul>
			      <li><input class="nav_small_buttons" type="submit" name="<%=hotel %>" value="<%=LoginDao.CapitalizeFirstLetter(hotel) %>"></li>
			      <li><input class="nav_small_buttons" type="submit" name="<%=vendor %>" value="<%=LoginDao.CapitalizeFirstLetter(vendor) %>"></li>
			      <li><input class="nav_small_buttons" type="submit" name="<%=agent %>" value="<%=LoginDao.CapitalizeFirstLetter(agent) %>"></li>
		      </ul>
		  </li>
		  
		  <li><input class="nav_buttons" type="submit" name="<%=booking %>" value="<%=LoginDao.CapitalizeFirstLetter(booking) %>" style="<%=bookingStlye%>" />		  
			  <ul>
			      <li><input class="nav_small_buttons" type="submit" name="<%=new1 %>" value="<%=LoginDao.CapitalizeFirstLetter(new1) %>"></li>
			      <li><input class="nav_small_buttons" type="submit" name="<%=itinerary %>" value="<%=LoginDao.CapitalizeFirstLetter(itinerary) %>"></li>
			      <li><input class="nav_small_buttons" type="submit" name="<%=voucher %>" value="<%=LoginDao.CapitalizeFirstLetter(voucher) %>"></li>
		      </ul>  
		  </li>
		     
		  <li><input class="nav_buttons" type="submit" name="<%=logout %>" value="<%=LoginDao.CapitalizeFirstLetter(logout) %>" /></li>
		</ul>
	</form>
	
	
	<div style="margin-left:14.5%;padding:1px 16px;height:100%;">
	
		Welcome to booking page, <%=firstname %>. You are logged in as <%=emailValue %>
		
	
	
	</div>
</body>
</html>


















