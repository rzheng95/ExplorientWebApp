<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.login.LoginDao"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Insert title here</title>
	</head>
	
<body>
	<%
	// prevents backing after logout
	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");

	// HTTP 1.0
	response.setHeader("Pragma", "no-cache");
	
	// Proxies
	response.setHeader("Expires", "0");

	// check if user is logged in
	/*if(session.getAttribute("email")==null)
	{
		response.sendRedirect("Login.jsp");
	}*/
	LoginDao dao = new LoginDao();
	String sessionID = "";
	Cookie[] emailAndSessionIDCookies = request.getCookies();
	
	if(emailAndSessionIDCookies != null)
	{
		for(Cookie tempCookie : emailAndSessionIDCookies)
		{
			if(LoginDao.getLoginCookieName().equals(tempCookie.getName()))	
			{
				String[] emailAndSessionID = tempCookie.getValue().split("=");
				
				if(emailAndSessionID.length==2)
					sessionID = emailAndSessionID[1];
				
				break;
			}
		}
	}
	
	
	
	if(sessionID.equals("") || !dao.checkSessionID(sessionID) || session.getAttribute(LoginDao.getSessionID())==null)
	{
		response.sendRedirect("Login.jsp");
	}
	%>
	
	<jsp:include page="HTML/Header.html" />
	
	Welcome to homepage
	
	
	
	<form action="Logout" method="post">
		<input type="submit" value="Log out">
	</form>
	
	
</body>
</html>