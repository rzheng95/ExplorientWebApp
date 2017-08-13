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

	LoginDao dao = new LoginDao();
	
	Cookie[] emailAndNonceCookies = request.getCookies();
	
	String nonce = dao.getNonceCookie(emailAndNonceCookies);	
	
	// Cookie || Database || Session
	if(nonce.equals("") || !dao.checkNonce(nonce) || session.getAttribute(LoginDao.getSessionName())==null)
	{
		response.sendRedirect("Login.jsp");
	}
	
	
	String email = "";
	String sessionValue = request.getSession(false).getAttribute(LoginDao.getSessionName()).toString();
	if(!sessionValue.isEmpty() && sessionValue.contains("="))
		email = sessionValue.split("=")[0];
	
	
	%>
	
	<jsp:include page="HTML/Header.html" />
	
	Welcome to homepage. You are logged in as <%=email %>
	
	
	
	<form action="Logout" method="post">
		<input type="submit" value="Log out">
	</form>
	
	
</body>
</html>