<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.login.LoginDao"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<%
		// prevents backing after logout
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	
		// HTTP 1.0
		response.setHeader("Pragma", "no-cache");
		
		// Proxies
		response.setHeader("Expires", "0");
		
		
        String email = LoginDao.getEmail();
        String password = LoginDao.getPassword();
        String failed = (String)session.getAttribute(LoginDao.LOGIN_FAILED);

        if(failed == null)
		{
			failed = " ";
		}
        
		LoginDao dao = new LoginDao();
		Cookie[] emailAndNonceCookies = request.getCookies();			
		String emailCookie = dao.getEmailCookie(emailAndNonceCookies);
	%>



	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<link rel="stylesheet" href="CSS/Login.css" type="text/css">
		<title>Explorient Login</title>
	</head>
<body>
		<div id="logo_div">
			<a href="http://www.explorient.com"> <img id="logo" src="Image/explorient logo1.png" alt="logo" /> </a>
		</div>
		<div id="content_wrap">
			<div id="login_div">
			
				<form action="Login" method="post">
						<fieldset>
						<legend id="legend" align= "center"><img id="legend_img" src="Image/Logo grey.png" style="width:3em;height:3em;"></legend> <br/>
						
						
						<input class="textfields" type="text" name="<%=email%>" value="<%=emailCookie%>" placeholder="<%=LoginDao.CapitalizeFirstLetter(email) %>">  <br/>
		
						<input class="textfields" type="password" name="<%=password%>" placeholder="<%=LoginDao.CapitalizeFirstLetter(password) %>"> 
		
						<p class="failedMessages" ><%=failed %></p>
						
						<input class="buttons" type="submit" name="sumbit" value="Log in"> <br/> <br/>
							
						
						<a class="links" id="register" href="Register.jsp">Register</a>

						
					</fieldset>		
				</form>
			</div>
		</div>

</body>
</html>