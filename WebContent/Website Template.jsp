 



<!-- Editable Dropdown -->
<script src="//code.jquery.com/jquery-1.12.4.min.js"></script>
<script src="//rawgithub.com/indrimuska/jquery-editable-select/master/dist/jquery-editable-select.min.js"></script>
<link href="//rawgithub.com/indrimuska/jquery-editable-select/master/dist/jquery-editable-select.min.css" rel="stylesheet">
<script src="JS/jquery-editable-select.js"></script>

<!-- Date Picker -->
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<script>
	$( window ).load(function() {
		  // Run code
		$(".datepicker" ).datepicker();
		$(".editable-select").editableSelect();
		
	});
			
</script>

 


<link rel="stylesheet" href="CSS/Website Template.css" type="text/css">
<%@ page import="com.login.LoginDao"%>
<%@ page import="com.homepage.HomepageDao"%>
<%@ page import="com.newpage.NewpageDao"%>
<%@ page import="com.passenger.PassengerDao"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.HashMap" %>

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
			
			if(session.getAttribute(LoginDao.getSessionName())!=null)
			{
				session.removeAttribute(LoginDao.getSessionName());
				session.invalidate();
			}
			response.sendRedirect("Login");
			return;
		}
	}
	else
	{
		response.sendRedirect("Login");
		return;
	}
	
	
	String email = LoginDao.getLoginEmail();
		String profile = HomepageDao.getHomepageNavigationProfile();
		String setting = HomepageDao.getHomepageNavigationSetting();
	String search = HomepageDao.getHomepageNavigationSearch();
		String hotel = HomepageDao.getHomepageNavigationHotel();
		String vendor = HomepageDao.getHomepageNavigationVendor();
		String agent = HomepageDao.getHomepageNavigationAgent();
		String passenger = HomepageDao.getHomepageNavigationPassenger();
	String booking = HomepageDao.getHomepageNavigationBooking();
		String new1 = HomepageDao.getHomepageNavigationNew();
		String itinerary = HomepageDao.getHomepageNavigationItinerary();
		String voucher = HomepageDao.getHomepageNavigationVoucher();
	String logout = HomepageDao.getHomepageNavigationLogout();
	

	
	
	String emailValue = "";
	String sessionValue = request.getSession(false).getAttribute(LoginDao.getSessionName()).toString();
	if(!sessionValue.isEmpty() && sessionValue.contains(NewpageDao.EQUAL))
		emailValue = sessionValue.split(NewpageDao.EQUAL)[0];
	
	String firstname = dao.getUserFirstNameByEmail(emailValue);
	String emailStyle = "", searchStyle = "", bookingStlye = "";
	
	String selected = (String)session.getAttribute(HomepageDao.getHomepageNavigationSelected());

	if(selected!= null)
	{
		if(selected.equals(search) || selected.equals(hotel) || selected.equals(vendor) || selected.equals(agent) || selected.equals(passenger))
			searchStyle = HomepageDao.getCssStylingColorGold();
		else if(selected.equals(booking) || selected.equals(new1) || selected.equals(itinerary) || selected.equals(voucher))
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
		<ul class="nav">
		  <li><input class="nav_buttons" type="submit" name="<%=email %>" value="<%=emailValue %>" style="<%=emailStyle%>" />
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
			      <li><input class="nav_small_buttons" type="submit" name="<%=passenger %>" value="<%=LoginDao.CapitalizeFirstLetter(passenger) %>"></li>
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
	
	
	
	
	
	
	
	
	
	
	
	
	