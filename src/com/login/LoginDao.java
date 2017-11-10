package com.login;

import java.sql.*;
import javax.servlet.*;  
import javax.servlet.http.*;  
import org.mariadb.jdbc.Driver;

public class LoginDao extends HttpServlet
{
	// Database query
	public final static String LOGIN_QUERY = "database.get.email.and.password.query";
	public final static String EMAIL_QUERY = "database.get.email.query";
	public final static String GET_SALT_BY_EMAIL_QUERY = "database.get.salt.by.email";
	public final static String ADD_USER_QUERY = "database.add.user.query";
	public final static String SAVE_NONCE_QUERY = "database.save.nonce.query";
	public final static String DELETE_NONCE_QUERY = "database.delete.nonce.query";
	public final static String NONCE_QUERY = "database.nonce.query";
	public final static String EMAIL_IN_NONCE_TABLE_QUERY = "database.get.email.in.nonce.table.query";
	public final static String EMAIL_AND_NONCE_QUERY = "database.email.and.nonce.query";
	public final static String UPDATE_NONCE_BY_EMAIL_QUERY = "database.update.nonce.by.email.query";
	public final static String USER_BY_EMAIL_QUERY = "database.get.user.by.email.query";
	
	public final static String SESSION_NAME = "session.name";
	public final static String DB_URL = "database.url";
	public final static String DB_USERNAME = "database.username";
	public final static String DB_PASSWORD = "database.password";
	
	// Log in
	public final static String LOGIN_EMAIL = "user.login.email";
	public final static String LOGIN_PASSWORD = "user.login.password";
	public final static String REGISTER_CONFIRM_PASSWORD = "register.confirm.password";
	public final static String LOGIN_FAILED = "login.failed";
	public final static String LOGIN_MAX_LENGTH_FAILED = "login.max.lenth.failed";
	public final static String MAX_LENGTH = "max.length";
	public final static String LOGIN_COOKIE_NAME = "login.cookie.name";
	public final static String MAX_INACTIVE_INTERVAL = "max.inactive.interval";
	public final static String MAX_LOGIN_COOKIE_AGE = "max.login.cookie.age";
	
	// Register
	public final static String REGISTER_FIRSTNAME = "register.firstname";
	public final static String REGISTER_LASTNAME = "register.lastname";
	public final static String REGISTER_COOKIE_NAME = "register.cookie.name";
	public final static String REGISTER_EMPTY_FIELD = "register.empty.field.message";
	public final static String REGISTER_EMAIL_EXIST = "register.email.exist.message";
	public final static String REGISTER_INVALID_EMAIL = "register.invalid.email.message";
	public final static String REGISTER_UNMATCHED_PASSWORD = "register.unmatched.password.message";
	public final static String REGISTER_FAILED = "register.failed";
	public final static String REGISTER_MAX_LENGTH_FAILED = "register.max.lenth.failed.message";
	public final static String REGISTER_INVALID_SYMBOLS_FAILED = "register.invalid.symbols.failed.message";
	
	public final static String REGISTER = "Register";
	public final static String HOMEPAGE = "/";
	public final static String LOGIN = "Login";
	public final static String LOGOUT = "Logout";
	public final static String LESSTHANSIGN = "<";
	public final static String GREATERTHANSIGN = ">";
	public final static String ATSIGN = "@";
	public final static String EQUALSIGN = "=";
	//public final static String ANDSIGN = "&";
	
	
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs;
	private String hashedPassword;
	
	// Database query
	private static String loginQuery;
	private static String emailQuery;
	private static String getSaltByEmailQuery;
	private static String addUserrQuery;
	private static String saveNonceQuery;
	private static String deleteNonceQuery;
	private static String nonceQuery;
	private static String emailInNonceTableQuery;
	private static String emailAndNonceQuery;
	private static String updateNonceByEmailQuery;
	private static String userByEmailQuery;
	
	
	
	private static String sessionName;
	private static String db_username;
	private static String db_password;
	private static String db_url;
	
	// Login
	private static String loginEmail;
	private static String loginPassword;
	private static String registerConfirmPassword;
	private static String loginFailed;
	private static String loginMaxLengthFailed;
	private static int maxLength;
	private static int maxInactiveInterval;
	private static int maxLoginCookieAge;
	
	// Register
	private static String register;
	private static String registerFirstname;
	private static String registerLastname;
	private static String loginCookieName;
	private static String registerCookieName;
	private static String registerEmptyField;
	private static String registerEmailExist;
	private static String registerInvalidEmail;
	private static String registerUnmatchedPassword;
	private static String registerFailed;
	private static String registerMaxLengthFailed;
	private static String registerInvalidSymbolsFailed;
	

	
	public void init()
	{

		try {		
			ServletContext sc = this.getServletContext();
			
			// Database query
			loginQuery = sc.getInitParameter(LOGIN_QUERY);
			emailQuery = sc.getInitParameter(EMAIL_QUERY);
			getSaltByEmailQuery = sc.getInitParameter(GET_SALT_BY_EMAIL_QUERY);
			addUserrQuery = sc.getInitParameter(ADD_USER_QUERY);
			saveNonceQuery = sc.getInitParameter(SAVE_NONCE_QUERY);
			deleteNonceQuery = sc.getInitParameter(DELETE_NONCE_QUERY);
			nonceQuery = sc.getInitParameter(NONCE_QUERY);
			emailInNonceTableQuery = sc.getInitParameter(EMAIL_IN_NONCE_TABLE_QUERY);
			emailAndNonceQuery = sc.getInitParameter(EMAIL_AND_NONCE_QUERY);
			updateNonceByEmailQuery = sc.getInitParameter(UPDATE_NONCE_BY_EMAIL_QUERY);
			userByEmailQuery = sc.getInitParameter(USER_BY_EMAIL_QUERY);
			
			
			sessionName = sc.getInitParameter(SESSION_NAME);
			db_username = sc.getInitParameter(DB_USERNAME);
			db_password = sc.getInitParameter(DB_PASSWORD);
			db_url = sc.getInitParameter(DB_URL);
			
			// Login
			loginEmail = sc.getInitParameter(LOGIN_EMAIL);
			loginPassword = sc.getInitParameter(LOGIN_PASSWORD);
			loginFailed = sc.getInitParameter(LOGIN_FAILED);
			loginMaxLengthFailed = sc.getInitParameter(LOGIN_MAX_LENGTH_FAILED);
			maxLength = Integer.parseInt(sc.getInitParameter(MAX_LENGTH));
			maxInactiveInterval = Integer.parseInt(sc.getInitParameter(MAX_INACTIVE_INTERVAL));
			maxLoginCookieAge = Integer.parseInt(sc.getInitParameter(MAX_LOGIN_COOKIE_AGE));
			
			// Register
			register = sc.getInitParameter(REGISTER);
			registerFirstname = sc.getInitParameter(REGISTER_FIRSTNAME);
			registerLastname = sc.getInitParameter(REGISTER_LASTNAME);
			registerConfirmPassword = sc.getInitParameter(REGISTER_CONFIRM_PASSWORD);
			loginCookieName = sc.getInitParameter(LOGIN_COOKIE_NAME);
			registerCookieName = sc.getInitParameter(REGISTER_COOKIE_NAME);
			registerEmptyField = sc.getInitParameter(REGISTER_EMPTY_FIELD);
			registerEmailExist = sc.getInitParameter(REGISTER_EMAIL_EXIST);
			registerInvalidEmail = sc.getInitParameter(REGISTER_INVALID_EMAIL);
			registerUnmatchedPassword = sc.getInitParameter(REGISTER_UNMATCHED_PASSWORD);
			registerFailed = sc.getInitParameter(REGISTER_FAILED);
			registerMaxLengthFailed = sc.getInitParameter(REGISTER_MAX_LENGTH_FAILED);
			registerInvalidSymbolsFailed = sc.getInitParameter(REGISTER_INVALID_SYMBOLS_FAILED);
			
			
			DriverManager.registerDriver(new Driver());


		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	public boolean checkInvalidSymbols(String text)
	{
		return (text.contains(LESSTHANSIGN) || text.contains(GREATERTHANSIGN) || text.contains(EQUALSIGN));
		//return (text.contains(LESSTHANSIGN) || text.contains(GREATERTHANSIGN) || text.contains(ATSIGN) || text.contains(EQUALSIGN));
	}
	
	public boolean checkMaxLength(String text)
	{
		return (text.length() > maxLength);
	}

	public boolean checkEmailAndPassword(String loginEmail, String loginPassword)
	{			
		String salt = getSalt(loginEmail);
		try {				
			hashedPassword = SHA512.hashText(loginPassword+salt);
			conn = DriverManager.getConnection(db_url, db_username, db_password);	
			
			pstmt = conn.prepareStatement(loginQuery);
			pstmt.setString(1, loginEmail);
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
	
	public String getSalt(String email)
	{
		String salt = "";
		try {				
			conn = DriverManager.getConnection(db_url, db_username, db_password);	
			pstmt = conn.prepareStatement(getSaltByEmailQuery);
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
			
			if(rs.next()) 
				salt = rs.getString("Salt");

			conn.close();
			pstmt.close();
			rs.close();
		} catch (Exception e) {
			System.err.println(e);
		}
		
		return salt;
	}
	
	public boolean checkEmail(String loginEmail)
	{
		try {				
			conn = DriverManager.getConnection(db_url, db_username, db_password);	
			
			pstmt = conn.prepareStatement(emailQuery);
			pstmt.setString(1, loginEmail);
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
	
	public String getUserFirstNameByEmail(String email)
	{
		String returnFirstname = "";
		try {				
			conn = DriverManager.getConnection(db_url, db_username, db_password);	
			
			pstmt = conn.prepareStatement(userByEmailQuery);
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
			
			while(rs.next())
			{
				returnFirstname = rs.getString("Firstname");
			}

			conn.close();
			pstmt.close();
			rs.close();
		} catch (Exception e) {
			System.err.println(e);
		}
		
		return returnFirstname;
	}
	
	public void addUser(String loginEmail, String salt, String loginPassword, String registerFirstname, String registerLastname)
	{
		
		try {				
			hashedPassword = SHA512.hashText(loginPassword+salt);
			conn = DriverManager.getConnection(db_url, db_username, db_password);	
			
			pstmt = conn.prepareStatement(addUserrQuery);
			pstmt.setString(1, loginEmail);
			pstmt.setString(2, salt);
			pstmt.setString(3, hashedPassword);
			pstmt.setString(4, registerFirstname);
			pstmt.setString(5, registerLastname);
			rs = pstmt.executeQuery();
			
						

			conn.close();
			pstmt.close();
			rs.close();
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	
	public void saveNonce(String email, String nonce)
	{
		
		try {				
			conn = DriverManager.getConnection(db_url, db_username, db_password);	
			
			pstmt = conn.prepareStatement(saveNonceQuery);
			pstmt.setString(1, email);
			pstmt.setString(2, nonce);
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
	
	public boolean checkEmailAndNonce(String email, String nonce)
	{
		try {				
			conn = DriverManager.getConnection(db_url, db_username, db_password);	
			
			pstmt = conn.prepareStatement(emailAndNonceQuery);
			pstmt.setString(1, email);
			pstmt.setString(2, nonce);
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
	
	
	public boolean checkEmailInNonceTable(String email)
	{
		try {				
			conn = DriverManager.getConnection(db_url, db_username, db_password);	
			
			pstmt = conn.prepareStatement(emailInNonceTableQuery);
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
	
	public String getNonceByEmail(String email)
	{
		String returnNonce = "";
		try {				
			conn = DriverManager.getConnection(db_url, db_username, db_password);	
			
			pstmt = conn.prepareStatement(emailInNonceTableQuery);
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
			
			while(rs.next())
			{
				returnNonce = rs.getString("NonceId");
			}


			conn.close();
			pstmt.close();
			rs.close();
		} catch (Exception e) {
			System.err.println(e);
		}
		return returnNonce;
	}
	
	public void updateNonceByEmail(String newNonce, String email)
	{
		try {				
			conn = DriverManager.getConnection(db_url, db_username, db_password);	
			
			pstmt = conn.prepareStatement(updateNonceByEmailQuery);
			pstmt.setString(1, newNonce);
			pstmt.setString(2, email);
			rs = pstmt.executeQuery();
			
			conn.close();
			pstmt.close();
			rs.close();
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	public String getEmailCookie(Cookie[] emailAndNonceCookies)
	{
		String loginEmail = "";
		
		if(emailAndNonceCookies != null)
		{
			for(Cookie tempCookie : emailAndNonceCookies)
			{
				if(LoginDao.getLoginCookieName().equals(tempCookie.getName()))	
				{
					String[] emailAndNonce = tempCookie.getValue().split("=");
					
					if(emailAndNonce.length==2)	
						loginEmail = emailAndNonce[0];
				
					break;
				}
			}
		}
		return loginEmail.trim();
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
		return nonce.trim();
	}
	
    public static String CapitalizeFirstLetter(String text)
    {
    	if(text!=null)
    		return text.substring(0, 1).toUpperCase() + text.substring(1).toLowerCase();
    	return null;
    }
	
	public static String getSessionName()
	{
		return sessionName;
	}
	
	// Login
	public static String getLoginEmail()
	{
		return loginEmail;
	}
	
	public static String getLoginPassword()
	{
		return loginPassword;
	}
	public static String getRegisterConfirmPassword()
	{
		return registerConfirmPassword;
	}

	public static String getLoginFailed()
	{
		return loginFailed;
	}
	
	public static String getLoginMaxLengthFailed()
	{
		return loginMaxLengthFailed;
	}
	public static int getMaxInactiveInterval()
	{
		return maxInactiveInterval;
	}
	public static int getMaxLoginCookieAge()
	{
		return maxLoginCookieAge;
	}
	
	// Register
	public static String getRegister()
	{
		return register;
	}
	public static String getRegisterFirstname()
	{
		return registerFirstname;
	}
	public static String getRegisterLastname()
	{
		return registerLastname;
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
	public static String getRegisterMaxLengthFailed()
	{
		return registerMaxLengthFailed;
	}
	public static String getRegisterInvalidSymbolsLFailed()
	{
		return registerInvalidSymbolsFailed;
	}
}
