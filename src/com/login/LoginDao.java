package com.login;

import java.sql.*;
import javax.servlet.*;  
import javax.servlet.http.*;  
import org.mariadb.jdbc.Driver;

public class LoginDao extends HttpServlet
{
	public final static String LOGIN_QUERY = "database.login.query";
	public final static String EMAIL_QUERY = "database.email.query";
	public final static String ADD_USER_QUERY = "database.add.user.query";
	public final static String SAVE_NONCE_QUERY = "database.save.nonce.query";
	public final static String DELETE_NONCE_QUERY = "database.delete.nonce.query";
	public final static String NONCE_QUERY = "database.nonce.query";
	
	public final static String SESSION_NAME = "session.name";
	public final static String DB_URL = "database.url";
	public final static String DB_USERNAME = "database.username";
	public final static String DB_PASSWORD = "database.password";
	public final static String EMAIL = "user.email";
	public final static String PASSWORD = "user.password";
	public final static String CONFIRM_PASSWORD = "register.confirm.password";
	public final static String LOGIN_FAILED = "login.failed";
	public final static String LOGIN_MAX_LENGTH_FAILED = "login.max.lenth.failed";
	public final static String MAX_LENGTH = "max.length";
	public final static String REGISTER = "register";
	public final static String FIRSTNAME = "register.firstname";
	public final static String LASTNAME = "register.lastname";
	public final static String LOGIN_COOKIE_NAME = "login.cookie.name";
	public final static String REGISTER_COOKIE_NAME = "register.cookie.name";
	public final static String REGISTER_EMPTY_FIELD = "register.empty.field.message";
	public final static String REGISTER_EMAIL_EXIST = "register.email.exist.message";
	public final static String REGISTER_INVALID_EMAIL = "register.invalid.email.message";
	public final static String REGISTER_UNMATCHED_PASSWORD = "register.unmatched.password.message";
	public final static String REGISTER_FAILED = "register.failed";
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs;
	private String hashedPassword;
	private static String loginQuery;
	private static String emailQuery;
	private static String addUserrQuery;
	private static String saveNonceQuery;
	private static String deleteNonceQuery;
	private static String nonceQuery;
	
	
	private static String sessionName;
	private static String db_username;
	private static String db_password;
	private static String db_url;
	private static String email;
	private static String password;
	private static String confirmPassword;
	private static String loginFailed;
	private static String loginMaxLengthFailed;
	private static int maxLength;
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
			
			
			loginQuery = sc.getInitParameter(LOGIN_QUERY);
			emailQuery = sc.getInitParameter(EMAIL_QUERY);
			addUserrQuery = sc.getInitParameter(ADD_USER_QUERY);
			saveNonceQuery = sc.getInitParameter(SAVE_NONCE_QUERY);
			deleteNonceQuery = sc.getInitParameter(DELETE_NONCE_QUERY);
			nonceQuery = sc.getInitParameter(NONCE_QUERY);
			
			sessionName = sc.getInitParameter(SESSION_NAME);
			db_username = sc.getInitParameter(DB_USERNAME);
			db_password = sc.getInitParameter(DB_PASSWORD);
			db_url = sc.getInitParameter(DB_URL);
			email = sc.getInitParameter(EMAIL);
			password = sc.getInitParameter(PASSWORD);
			confirmPassword = sc.getInitParameter(CONFIRM_PASSWORD);
			loginFailed = sc.getInitParameter(LOGIN_FAILED);
			loginMaxLengthFailed = sc.getInitParameter(LOGIN_MAX_LENGTH_FAILED);
			maxLength = Integer.parseInt(sc.getInitParameter(MAX_LENGTH));
			register = sc.getInitParameter(REGISTER);
			firstname = sc.getInitParameter(FIRSTNAME);
			lastname = sc.getInitParameter(LASTNAME);
			loginCookieName = sc.getInitParameter(LOGIN_COOKIE_NAME);
			registerCookieName = sc.getInitParameter(REGISTER_COOKIE_NAME);
			registerEmptyField = sc.getInitParameter(REGISTER_EMPTY_FIELD);
			registerEmailExist = sc.getInitParameter(REGISTER_EMAIL_EXIST);
			registerInvalidEmail = sc.getInitParameter(REGISTER_INVALID_EMAIL);
			registerUnmatchedPassword = sc.getInitParameter(REGISTER_UNMATCHED_PASSWORD);
			registerFailed = sc.getInitParameter(REGISTER_FAILED);

			
			DriverManager.registerDriver(new Driver());


		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	public boolean checkMaxLength(String text)
	{
		return (text.length() > maxLength);
	}

	public boolean checkEmailAndPassword(String email, String password)
	{			

		try {				
			hashedPassword = SHA512.hashText(password);
			conn = DriverManager.getConnection(db_url, db_username, db_password);	
			
			pstmt = conn.prepareStatement(loginQuery);
			pstmt.setString(1, email);
			pstmt.setString(2, hashedPassword);
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
	
	public boolean checkEmail(String email)
	{
		try {				
			conn = DriverManager.getConnection(db_url, db_username, db_password);	
			
			pstmt = conn.prepareStatement(emailQuery);
			pstmt.setString(1, email);
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
	
	public void addUser(String email, String password, String firstname, String lastname)
	{
		
		try {				
			hashedPassword = SHA512.hashText(password);
			conn = DriverManager.getConnection(db_url, db_username, db_password);	
			
			pstmt = conn.prepareStatement(addUserrQuery);
			pstmt.setString(1, email);
			pstmt.setString(2, hashedPassword);
			pstmt.setString(3, firstname);
			pstmt.setString(4, lastname);
			rs = pstmt.executeQuery();
			
						

			conn.close();
			pstmt.close();
			rs.close();
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	public void saveNonce(String nonce)
	{
		try {				
			conn = DriverManager.getConnection(db_url, db_username, db_password);	
			
			pstmt = conn.prepareStatement(saveNonceQuery);
			pstmt.setString(1, nonce);
			rs = pstmt.executeQuery();
			
						

			conn.close();
			pstmt.close();
			rs.close();
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	public void deleteNonce(String nonce)
	{
		try {				
			conn = DriverManager.getConnection(db_url, db_username, db_password);	
			
			pstmt = conn.prepareStatement(deleteNonceQuery);
			pstmt.setString(1, nonce);
			rs = pstmt.executeQuery();
			
						

			conn.close();
			pstmt.close();
			rs.close();
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	public boolean checkNonce(String nonce)
	{
		try {				
			conn = DriverManager.getConnection(db_url, db_username, db_password);	
			
			pstmt = conn.prepareStatement(nonceQuery);
			pstmt.setString(1, nonce);
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
	
	public String getEmailCookie(Cookie[] emailAndNonceCookies)
	{
		String email = "";
		
		if(emailAndNonceCookies != null)
		{
			for(Cookie tempCookie : emailAndNonceCookies)
			{
				if(LoginDao.getLoginCookieName().equals(tempCookie.getName()))	
				{
					String[] emailAndNonce = tempCookie.getValue().split("=");
					
					if(emailAndNonce.length==2)	
						email = emailAndNonce[0];
				
					break;
				}
			}
		}
		return email;
	}
	
	public String getNonceCookie(Cookie[] emailAndNonceCookies)
	{
		String nonce = "";
		
		if(emailAndNonceCookies != null)
		{
			for(Cookie tempCookie : emailAndNonceCookies)
			{
				if(LoginDao.getLoginCookieName().equals(tempCookie.getName()))	
				{
					String[] emailAndNonce = tempCookie.getValue().split("=");
					
					if(emailAndNonce.length==2)	
						nonce = emailAndNonce[1];
				
					break;
				}
			}
		}
		return nonce;
	}
	
	
	public static String getSessionName()
	{
		return sessionName;
	}
	public static String getEmail()
	{
		return email;
	}
	
	public static String getPassword()
	{
		return password;
	}
	public static String getConfirmPassword()
	{
		return confirmPassword;
	}

	public static String getLoginFailed()
	{
		return loginFailed;
	}
	
	public static String getLoginMaxLengthFailed()
	{
		return loginMaxLengthFailed;
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
	
	
	/*public static void main(String [] args) throws Exception
	{

		
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
		}

	}*/
}
