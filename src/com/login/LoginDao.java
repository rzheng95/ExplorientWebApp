package com.login;

import java.sql.*;
import javax.servlet.*;  
import javax.servlet.http.*;  
import org.mariadb.jdbc.Driver;

public class LoginDao extends HttpServlet
{
	public final static String DB_URL = "database.url";
	public final static String DB_USERNAME = "database.username";
	public final static String DB_PASSWORD = "database.password";
	public final static String LOGIN_QUERY = "database.login";
	public final static String EMAIL = "user.email";
	public final static String PASSWORD = "user.password";
	public final static String LOGINFAILED = "login.failed";
	public final static String REGISTER = "register";
	public final static String FIRSTNAME = "register.firstname";
	public final static String LASTNAME = "register.lastname";
	public final static String LOGINCOOKIENAME = "login.cookie.name";
	public final static String REGISTERCOOKIENAME = "register.cookie.name";
	public final static String REGISTEREMPTYFIELD = "register.empty.field.message";
	public final static String REGISTEREMAILEXIST = "register.email.exist.message";
	public final static String REGISTERINVALIDEMAIL = "register.invalid.email.message";
	public final static String REGISTERUNMATCHEDPASSWORD = "register.unmatched.password.message";
	public final static String REGISTERFAILED = "register.failed";
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs;
	private static String db_username;
	private static String db_password;
	private static String db_url;
	private static String query;
	private static String email;
	private static String password;
	private static String loginFailed;
	private static String register;
	private static String firstname;
	private static String lastname;
	private static String loginCookieName;
	private static String registerCookieName;
	private static String registerEmptyField;
	private static String registerEmailExist;
	private static String registerInvalidEmail;
	private static String registerUnmatchedPassword;
	private static String registerFailed;
	
	public void init()
	{

		try {
			ServletContext sc = this.getServletContext();
			db_username = sc.getInitParameter(DB_USERNAME);
			db_password = sc.getInitParameter(DB_PASSWORD);
			db_url = sc.getInitParameter(DB_URL);
			email = sc.getInitParameter(EMAIL);
			password = sc.getInitParameter(PASSWORD);
			loginFailed = sc.getInitParameter(LOGINFAILED);
			query = sc.getInitParameter(LOGIN_QUERY);
			register = sc.getInitParameter(REGISTER);
			firstname = sc.getInitParameter(FIRSTNAME);
			lastname = sc.getInitParameter(LASTNAME);
			loginCookieName = sc.getInitParameter(LOGINCOOKIENAME);
			registerCookieName = sc.getInitParameter(REGISTERCOOKIENAME);
			registerEmptyField = sc.getInitParameter(REGISTEREMPTYFIELD);
			registerEmailExist = sc.getInitParameter(REGISTEREMAILEXIST);
			registerInvalidEmail = sc.getInitParameter(REGISTERINVALIDEMAIL);
			registerUnmatchedPassword = sc.getInitParameter(REGISTERUNMATCHEDPASSWORD);
			registerFailed = sc.getInitParameter(REGISTERFAILED);
			
			
			DriverManager.registerDriver(new Driver());


		} catch (Exception e) {
			System.err.println(e);
		}
	}
	

	public boolean check(String email, String password)
	{
		try {				
//			DriverManager.registerDriver(new Driver());
			conn = DriverManager.getConnection(db_url, db_username, db_password);	
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			rs = pstmt.executeQuery();
			
			if(rs.next()) return true;

			conn.close();
			pstmt.close();
			rs.close();
		} catch (Exception e) {
			System.err.println(e);
		}
		
		return false;
	}
	
	
	public static String getEmail()
	{
		return email;
	}
	
	public static String getPassword()
	{
		return password;
	}

	public static String getLoginFailed()
	{
		return loginFailed;
	}
	
	public static String getRegister()
	{
		return register;
	}
	
	public static String getFirstname()
	{
		return firstname;
	}
	
	public static String getLastname()
	{
		return lastname;
	}
	public static String getLoginCookieName()
	{
		return loginCookieName;
	}
	public static String getRegisterCookieName()
	{
		return registerCookieName;
	}
	public static String getRegisterEmptyFieldMessage()
	{
		return registerEmptyField;
	}
	public static String getRegisterEmailExistMessage()
	{
		return registerEmailExist;
	}
	public static String getRegisterInvalidEmailMessage()
	{
		return registerInvalidEmail;
	}
	public static String getRegisterUnmatchedPasswordMessage()
	{
		return registerUnmatchedPassword;
	}
	public static String getRegisterFailed()
	{
		return registerFailed;
	}
	
	
    public static String CapitalizeFirstLetter(String text)
    {
    	return text.substring(0, 1).toUpperCase() + text.substring(1);
    }
	
	
	public static void main(String [] args) throws Exception
	{
			
		/*
		try {
			DriverManager.registerDriver(new Driver());
			conn = DriverManager.getConnection(url, DBUsername, DBPassword);
			String query = "select * from test";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next())
			{
				System.out.println("Username: " + rs.getString("username")
				+"\tPassword: "+rs.getString("password"));
			}
			
			conn.close();
			stmt.close();
			rs.close();
		} catch (Exception e) {
			System.err.println(e);
		}*/

	}
}
