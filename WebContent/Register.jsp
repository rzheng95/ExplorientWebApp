<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.login.LoginDao"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<%
	
        String email = LoginDao.getEmail();
        String password = LoginDao.getPassword();
        String register = LoginDao.getRegister();
        String firstname = LoginDao.getFirstname();
        String lastname = LoginDao.getLastname();
        String failed = (String)session.getAttribute(LoginDao.REGISTERFAILED);
		
        String emailValue = "";
        String firstnameValue = "";
        String lastnameValue = "";
        if(failed == null)
		{
			failed = " ";
		}
        else
        {
        	
			String emailCookie = "";
			Cookie[] registerCookie = request.getCookies();
			
			if(registerCookie != null)
			{
				for(Cookie tempCookie : registerCookie)
				{
					if(LoginDao.getRegisterCookieName().equals(tempCookie.getName()))	
					{
						String[] cookieFragment = tempCookie.getValue().split("=", -1);
						
						if(cookieFragment.length <= 3)
						{
							emailValue = cookieFragment[0];
							firstnameValue = cookieFragment[1];
							lastnameValue = cookieFragment[2];
						}
						
						break;
					}
				}
			}
			
        }
     
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
			
				<form action="Register" method="post">
						<fieldset>
						<legend id="legend" align= "center"><img id="legend_img" src="Image/Logo grey.png" style="width:3em;height:3em;"></legend>

						
						<br/>
						<input class="textfields" type="text" name=<%=email%> value="<%=emailValue%>" placeholder=<%=LoginDao.CapitalizeFirstLetter(email) %>>  <br/>
						
		
						<input class="textfields" type="password" name=<%=password%>  placeholder=<%=LoginDao.CapitalizeFirstLetter(password) %>>  <br/>

						<input class="textfields" type="password" name=<%="Confirm_"+password%>  placeholder=<%="Confirm_"+LoginDao.CapitalizeFirstLetter(password) %>>  <br/>
						
						<input class="textfields" type="text" name=<%=firstname%> value="<%=firstnameValue%>"  placeholder=<%=LoginDao.CapitalizeFirstLetter(firstname) %>> <br/>
						
						<input class="textfields" type="text" name=<%=lastname%> value="<%=lastnameValue%>" placeholder=<%=LoginDao.CapitalizeFirstLetter(lastname) %>> <br/>
						
						<p class="failedMessages"><%=failed %></p>
						
						<div id="register_div">
							<input class="buttons" id="register_button" type="submit" name=<%=register%> value=<%=LoginDao.CapitalizeFirstLetter(register) %>> <br/><br/>
							<a href="Login.jsp"><input class="buttons"  id="back_to_login" type="button" name="back_to_login" value="Back to login"></a>
						</div>
						
						<br/>
							
						
						
												
					</fieldset>		
				</form>
			</div>
		</div>

</body>
</html>