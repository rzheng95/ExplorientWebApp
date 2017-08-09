package com.login;

import java.sql.*;
import org.mariadb.jdbc.Driver;
public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(LoginDao.DB_URL);
		
		try {
			DriverManager.registerDriver(new Driver());
			Connection conn = DriverManager.getConnection("jdbc:mysql://db.explorient.com:3306/ExplorientDB", "Richard", "jay.yang.kwak");
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
	}

}
