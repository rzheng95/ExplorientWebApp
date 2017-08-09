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
	public final static String FAILED = "failed";
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs;
	private static String db_username;
	private static String db_password;
	private static String db_url;
	private static String query;
	private static String email;
	private static String password;
	private static String failed;

	
	public void init()
	{

		try {
			ServletContext sc = this.getServletContext();
			db_username = sc.getInitParameter(DB_USERNAME);
			db_password = sc.getInitParameter(DB_PASSWORD);
			db_url = sc.getInitParameter(DB_URL);
			email = sc.getInitParameter(EMAIL);
			password = sc.getInitParameter(PASSWORD);
			failed = sc.getInitParameter(FAILED);
			query = sc.getInitParameter(LOGIN_QUERY);
						
			//Class.forName("org.mariadb.jdbc.Driver");
//			DriverManager.registerDriver(new Driver());
//			conn = DriverManager.getConnection(db_url, db_username, db_password);	


		} catch (Exception e) {
			System.err.println(e);
		}
	}
	

	public boolean check(String email, String password)
	{

		try {		
			System.out.println("in check, db_username is: "+db_username);
			DriverManager.registerDriver(new Driver());
			conn = DriverManager.getConnection(db_url, db_username, db_password);	

			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			rs = pstmt.executeQuery();
			
			if(rs.next()) 
				{
				System.out.println("yes");
				return true;
				}else
					System.out.println("no");
					
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

	public static String getFailed()
	{
		return failed;
	}
	
	
	
	public static void main(String [] args)
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
