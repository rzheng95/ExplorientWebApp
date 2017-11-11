package com.agent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import org.mariadb.jdbc.Driver;
import com.newpage.NewpageDao;

public class AgentDao extends HttpServlet
{			
	// db connection
	public final static String DB_URL = "database.url";
	public final static String DB_USERNAME = "database.username";
	public final static String DB_PASSWORD = "database.password";
	
	
	public final static String EQUAL = NewpageDao.EQUAL;
	
	// db queries
	public final static String GET_AGENTS_BY_COUNTRY_QUERY = "database.get.agents.by.country.query";
	public final static String GET_AGENTS_BY_CITY_QUERY = "database.get.agents.by.city.query";
	public final static String GET_AGENT_COUNTRIES_QUERY = "database.get.agent.countries.query";
	public final static String GET_AGENT_CITIES_QUERY = "database.get.agent.cities.query";
	public final static String CHECK_AGENT_QUERY = "database.get.agent.query";
	
	public final static String UPDATE_AGENT_QUERY = "database.update.agent.query";
	public final static String DELETE_AGENT_QUERY = "database.delete.agent.query";
	public final static String ADD_AGENT_QUERY = "database.add.agent.query";
	
	public final static String GET_AGENT_BY_AGENT_NAME_QUERY = "database.get.agent.by.agent.name.query";
	
	
	// agent variables
	public final static String AGENT = "agent";
	public final static String AGENT_AGNET = "agent.agent";
	public final static String AGENT_LIST = "agent.list";
	public final static String AGENT_LASTNAME = "agent.lastname";
	public final static String AGENT_FIRSTNAME = "agent.firstname";
	
	
	// search box variables

	// buttons
	public final static String AGENT_BUTTONS = "agent.buttons";
	public final static String AGENT_GET_AGENTS_BUTTON = "agent.get.Agents.button";

	
	// failed
	public final static String AGENT_FAILED = "agent.failed";
	public final static String AGENT_AGENT_ALREADY_EXISTS_FAILED = "agent.agent.already.exists.failed";
	public final static String AGENT_AGENT_NOT_FOUND_FAILED = "agent.agent.not.found.failed";
	
	// messages
	public final static String AGENT_CREATED_MESSAGE = "agent.agent.created.message";
	public final static String AGENT_UPDATED_MESSAGE = "agent.agent.updated.message";
	public final static String AGENT_DELETED_MESSAGE = "agent.agent.deleted.message";
	
	
	// entered values
	public final static int AGENT_ENTERED_VALUE_LENGTH = 16;
	public final static int AGENT_INDEX = 0;
	public final static int LASTNAME_INDEX = 1;
	public final static int FIRSTNAME_INDEX = 2;
	public final static int ADDRESS_INDEX = 3;
	public final static int CITY_INDEX = 4;
	public final static int STATE_INDEX = 5;
	public final static int COUNTRY_INDEX = 6;
	public final static int ZIPCODE_INDEX = 7;
	public final static int TELEPHONE1_INDEX = 8;
	public final static int TELEPHONE2_INDEX = 9;
	public final static int FAX_INDEX = 10;
	public final static int EMAIL1_INDEX = 11;
	public final static int EMAIL2_INDEX = 12;
	public final static int WEBSITE_INDEX = 13;
	public final static int SEARCH_COUNTRY_INDEX = 14;
	public final static int SEARCH_CITY_INDEX = 15;
	
	
	// db connection
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs;
	private static String db_username;
	private static String db_password;
	private static String db_url;
	
	
	// db queries
	private static String getAgentsByCountryQuery;
	private static String getAgentsByCityQuery;
	private static String getAgentCountriesQuery;
	private static String getAgentCitiesQuery;
	private static String checkAgentQuery;
	private static String addAgentQuery;
	private static String updateAgentQuery;
	private static String deleteAgentQuery;
	private static String getAgentByAgentNameQuery;
	
	
	// agent variables
	private static String agentAgent;
	private static String agentLastname;
	private static String agentFirstname;
	
	// search box variables

	// buttons
	private static String agentGetAgentsButton;

	
	// failed
	private static String agentAgentAlreadyExistsFailed;
	private static String agentAgentNotFoundFailed;

	
	// message
	private static String agentCreatedMessage;
	private static String agentUpdatedMessage;
	private static String agentDeletedMessage;
	
	
	public void init()
	{
		
		try {
			ServletContext sc = this.getServletContext();

			// db connection
			db_username = sc.getInitParameter(DB_USERNAME);
			db_password = sc.getInitParameter(DB_PASSWORD);
			db_url = sc.getInitParameter(DB_URL);
			
			
			// db queries
			getAgentsByCountryQuery = sc.getInitParameter(GET_AGENTS_BY_COUNTRY_QUERY);
			getAgentsByCityQuery = sc.getInitParameter(GET_AGENTS_BY_CITY_QUERY);
			getAgentCountriesQuery = sc.getInitParameter(GET_AGENT_COUNTRIES_QUERY);
			getAgentCitiesQuery = sc.getInitParameter(GET_AGENT_CITIES_QUERY);
			checkAgentQuery = sc.getInitParameter(CHECK_AGENT_QUERY);
			addAgentQuery = sc.getInitParameter(ADD_AGENT_QUERY);
			updateAgentQuery = sc.getInitParameter(UPDATE_AGENT_QUERY);
			deleteAgentQuery = sc.getInitParameter(DELETE_AGENT_QUERY);
			getAgentByAgentNameQuery = sc.getInitParameter(GET_AGENT_BY_AGENT_NAME_QUERY);
			
			// agent variables
			agentAgent = sc.getInitParameter(AGENT_AGNET);
			agentLastname = sc.getInitParameter(AGENT_LASTNAME);
			agentFirstname = sc.getInitParameter(AGENT_FIRSTNAME);
			
			
			// search box variables

			// buttons
			agentGetAgentsButton = sc.getInitParameter(AGENT_GET_AGENTS_BUTTON);
			
			
			// failed
			agentAgentNotFoundFailed = sc.getInitParameter(AGENT_AGENT_NOT_FOUND_FAILED);
			agentAgentAlreadyExistsFailed = sc.getInitParameter(AGENT_AGENT_ALREADY_EXISTS_FAILED);
			
			
			// message
			agentCreatedMessage = sc.getInitParameter(AGENT_CREATED_MESSAGE);
			agentUpdatedMessage = sc.getInitParameter(AGENT_UPDATED_MESSAGE);
			agentDeletedMessage = sc.getInitParameter(AGENT_DELETED_MESSAGE);

			

			DriverManager.registerDriver(new Driver());
			
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	public ArrayList<String> getAgentByAgentName(String agent)
	{		
		ArrayList<String> returnList = new ArrayList<>();

			
		try {				
			conn = DriverManager.getConnection(db_url, db_username, db_password);	
				
			pstmt = conn.prepareStatement(getAgentByAgentNameQuery);
			pstmt.setString(1, agent);

			rs = pstmt.executeQuery();
			
			while(rs.next())
			{							  
				returnList.add(rs.getString("Agent"));
				returnList.add(rs.getString("Lastname"));
				returnList.add(rs.getString("Firstname"));
				returnList.add(rs.getString("Address"));
				returnList.add(rs.getString("City"));
				returnList.add(rs.getString("State"));
				returnList.add(rs.getString("Country"));
				returnList.add(rs.getString("Zipcode"));
				returnList.add(rs.getString("Telephone_1"));
				returnList.add(rs.getString("Telephone_2"));
				returnList.add(rs.getString("Fax"));
				returnList.add(rs.getString("Email_1"));
				returnList.add(rs.getString("Email_2"));
				returnList.add(rs.getString("Website"));
			}
			
			conn.close();
			pstmt.close();
			rs.close();
		} catch (Exception e) {
			System.err.println(e);
		}	
		
		return returnList;
	}
	
	
	public ArrayList<String> getAgents(String country, String city)
	{		
		ArrayList<String> returnList = new ArrayList<>();

			
		try {				
			conn = DriverManager.getConnection(db_url, db_username, db_password);	
			
			String query = "";
			
			
			
			if(city.equals("") && country.equals(""))
				return returnList;
			else if(!city.equals(""))
			{
				query = getAgentsByCityQuery;
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, city);
			}
			else
			{
				query = getAgentsByCountryQuery;
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, country);
			}
			
				
			rs = pstmt.executeQuery();
			
			while(rs.next())
			{							  
				returnList.add(rs.getString("Agent"));
			}
			
			conn.close();
			pstmt.close();
			rs.close();
		} catch (Exception e) {
			System.err.println(e);
		}	
		
		return returnList;
	}
	
	public void addAgent(String agent, String lastname, String firstname, String address, String city, String state, String country, String zipcode, 
			String telephone1, String telephone2, String fax, String email1, String email2, String website)
	{		
		// NOT NULL attributes in database
		if(agent == null ||  lastname == null || firstname == null ||address == null || city == null || country == null || telephone1 == null) return;
		
		try {				
			conn = DriverManager.getConnection(db_url, db_username, db_password);			
			pstmt = conn.prepareStatement(addAgentQuery);
			pstmt.setString(1, agent);
			pstmt.setString(2, lastname);
			pstmt.setString(3, firstname);
			pstmt.setString(4, address);
			pstmt.setString(5, city);
			pstmt.setString(6, state);
			pstmt.setString(7, country);
			pstmt.setString(8, zipcode);
			pstmt.setString(9, telephone1);
			pstmt.setString(10, telephone2);
			pstmt.setString(11, fax);
			pstmt.setString(12, email1);
			pstmt.setString(13, email2);
			pstmt.setString(14, website);
			
			rs = pstmt.executeQuery();
			
			conn.close();
			pstmt.close();
			rs.close();
		} catch (Exception e) {
			System.err.println(e);
		}	
	}
	
	public void deleteAgent(String agent)
	{
		
		try {				
			conn = DriverManager.getConnection(db_url, db_username, db_password);	
			
			pstmt = conn.prepareStatement(deleteAgentQuery);
			pstmt.setString(1, agent);
			rs = pstmt.executeQuery();

			conn.close();
			pstmt.close();
			rs.close();
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	
	public void updateAgent(String Lastname, String Firstname, String address, String city, String state, String country, String zipcode, String telephone1, String telephone2, String fax, String email1, String email2, String website, String agent)
	{

		try {				
			conn = DriverManager.getConnection(db_url, db_username, db_password);	
			
			pstmt = conn.prepareStatement(updateAgentQuery);
			pstmt.setString(1, Lastname);
			pstmt.setString(2, Firstname);
			pstmt.setString(3, address);
			pstmt.setString(4, city);
			pstmt.setString(5, state);
			pstmt.setString(6, country);
			pstmt.setString(7, zipcode);
			pstmt.setString(8, telephone1);
			pstmt.setString(9, telephone2);
			pstmt.setString(10, fax);
			pstmt.setString(11, email1);
			pstmt.setString(12, email2);
			pstmt.setString(13, website);
			pstmt.setString(14, agent);
			
			rs = pstmt.executeQuery();

			conn.close();
			pstmt.close();
			rs.close();
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	public boolean checkAgent(String agent)
	{			
		try {				
			conn = DriverManager.getConnection(db_url, db_username, db_password);	
			
			pstmt = conn.prepareStatement(checkAgentQuery);
			pstmt.setString(1, agent);
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
	
	
	
	public ArrayList<String> getCountries()
	{
		ArrayList<String> returnList = new ArrayList<>();

		try {				
			conn = DriverManager.getConnection(db_url, db_username, db_password);			
			pstmt = conn.prepareStatement(getAgentCountriesQuery);
			rs = pstmt.executeQuery();
			
			while(rs.next())
			{								  
				returnList.add(rs.getString("Country"));
			}
			conn.close();
			pstmt.close();
			rs.close();
		} catch (Exception e) {
			System.err.println(e);
		}	
		return returnList;
	}
	
	
	public ArrayList<String> getCities()
	{
		ArrayList<String> returnList = new ArrayList<>();

		try {				
			conn = DriverManager.getConnection(db_url, db_username, db_password);			
			pstmt = conn.prepareStatement(getAgentCitiesQuery);
			rs = pstmt.executeQuery();
			
			while(rs.next())
			{								  
				returnList.add(rs.getString("City"));
			}
			conn.close();
			pstmt.close();
			rs.close();
		} catch (Exception e) {
			System.err.println(e);
		}	
		return returnList;
	}
	

	
	
	
	
	// agent Variables
	public static String getAgentAgent()
	{
		return agentAgent;
	}
	public static String getAgentLastname()
	{
		return agentLastname;
	}
	public static String getAgentFirstname()
	{
		return agentFirstname;
	}
	
	// buttons
	public static String getAgentGetAgentsButton()
	{
		return agentGetAgentsButton;
	}
	
	// failed
	public static String getAgentAgentNotFoundFailed()
	{
		return agentAgentNotFoundFailed;
	}
	public static String getAgentAgentAlreadyExistsFailed()
	{
		return agentAgentAlreadyExistsFailed;
	}
	
	
	// message
	public static String getAgentCreatedMessage()
	{
		return agentCreatedMessage;
	}
	public static String getAgentUpdatedMessage()
	{
		return agentUpdatedMessage;
	}
	public static String getAgentDeletedMessage()
	{
		return agentDeletedMessage;
	}
}















