<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.login.LoginDao"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<%
        String email = LoginDao.getEmail();
        String password = LoginDao.getPassword();
        String failed = (String)session.getAttribute(LoginDao.FAILED);

        if(failed == null)
		{
			failed = " ";
		}
	%>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<link rel="stylesheet" href="CSS/Login.css" type="text/css">
		<title>Insert title here</title>
	</head>
<body>
	<img id="logo" src="Image/explorient logo1.png" alt="logo" />  <br/><br/><br/><br/><br/>
	
	<div id="login_div">
		<form action="Login" method="post">
				<fieldset>
				<legend id="legend" align= "center"><img id="legend_img" src="Image/Logo grey.png" style="width:3em;height:3em;"></legend>
				<%
					String emailCookie = "";
					Cookie[] emailCookies = request.getCookies();
					
					if(emailCookies != null)
					{
						for(Cookie tempCookie : emailCookies)
						{
							if("Explorient.email".equals(tempCookie.getName()))	
							{
								emailCookie = tempCookie.getValue();
								break;
							}
						}
					}
				%>
				
				<br/>
				<input class="textfields" type="text" name=<%=email%> value="<%=emailCookie%>" placeholder="Email">  <br/>

				<input class="textfields" type="password" name=<%=password%> placeholder="Password"> <br/><br/>

				<input class="buttons" type="submit" name="sumbit" value="Log in">
				
				<p id="login_message"><%=failed %></p>
				

			</fieldset>		
		</form>
	</div>
</body>
</html>