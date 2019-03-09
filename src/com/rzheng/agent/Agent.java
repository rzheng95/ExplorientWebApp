package com.rzheng.agent;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rzheng.hotel.HotelDao;
import com.rzheng.login.EmailChecker;
import com.rzheng.login.LoginDao;

@WebServlet("/Agent")
public class Agent extends HttpServlet {
	
	private LoginDao ldao;
	private AgentDao adao;
	private ArrayList<String> required;
	private ArrayList<String> notRequired;
	
	// search box
	private String searchCountry;
	private String searchCity;
	
	// buttons
	private String getAgentsButton;
	private String createButton;
	private String updateButton;
	private String deleteButton;
	private String clearButton;
	
	// hotel variables
	private String agent;
	private String lastname;
	private String firstname;
	private String address;
	private String city;
	private String state;
	private String country;
	private String zipcode;
	private String telephone1;
	private String telephone2;
	private String fax;
	private String email1;
	private String email2;
	private String website;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("Agent.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ldao = new LoginDao();
		adao = new AgentDao();
		
		
		required = new ArrayList<>();
		notRequired = new ArrayList<>();
		
		
		// search box
		searchCountry = request.getParameter(HotelDao.getHotelSearchCountry()).trim();
		searchCity = request.getParameter(HotelDao.getHotelSearchCity()).trim();
		
		// buttons
		getAgentsButton = AgentDao.getAgentGetAgentsButton();
		createButton = HotelDao.getHotelCreateButton();
		updateButton = HotelDao.getHotelUpdateButton();
		deleteButton = HotelDao.getHotelDeleteButton();
		clearButton = HotelDao.getHotelClearButton();
		
		// agent variables
		agent = request.getParameter(AgentDao.getAgentAgent()).trim();
		lastname = request.getParameter(AgentDao.getAgentLastname()).trim();
		firstname = request.getParameter(AgentDao.getAgentFirstname()).trim();
		
		address = request.getParameter(HotelDao.getHotelAddress()).trim();
		city = request.getParameter(HotelDao.getHotelCity()).trim();
		state = request.getParameter(HotelDao.getHotelState()).trim();
		country = request.getParameter(HotelDao.getHotelCountry()).trim();
		zipcode = request.getParameter(HotelDao.getHotelZipcode()).trim();
		telephone1 = request.getParameter(HotelDao.getHotelTelephone1()).trim();
		telephone2 = request.getParameter(HotelDao.getHotelTelephone2()).trim();
		fax = request.getParameter(HotelDao.getHotelFax()).trim();
		email1 = request.getParameter(HotelDao.getHotelEmail1()).trim();
		email2 = request.getParameter(HotelDao.getHotelEmail2()).trim();
		website = request.getParameter(HotelDao.getHotelWebsite()).trim();
		
		notRequired.add(searchCountry); notRequired.add(searchCity); notRequired.add(state); notRequired.add(zipcode); notRequired.add(telephone2);
		notRequired.add(fax); notRequired.add(email1); notRequired.add(email2); notRequired.add(website); 
		
		required.add(agent); required.add(lastname); required.add(firstname); required.add(address); required.add(city);  required.add(country); required.add(telephone1);

		
		// Get Agents button pressed
		if(request.getParameter(getAgentsButton) != null)
		{
			// will get Agents at the end
		}
		// clear button pressed
		else if(request.getParameter(clearButton) != null)
		{
			clear();
		}
		// delete button pressed
		else if(request.getParameter(deleteButton) != null)
		{
			// if agent exists
			if(adao.checkAgent(agent))
			{
				adao.deleteAgent(agent);
				clear();
				request.setAttribute(AgentDao.AGENT_FAILED, AgentDao.getAgentDeletedMessage());
			}
			else // agent doesn't exist
			{
				clear();		
				request.setAttribute(AgentDao.AGENT_FAILED, AgentDao.getAgentAgentNotFoundFailed());
			}
			

		}
		// check valid email
		else if((!email1.equals("") && !EmailChecker.validate(email1)) || (!email2.equals("") && !EmailChecker.validate(email2)))
		{
			request.setAttribute(AgentDao.AGENT_FAILED, LoginDao.getRegisterInvalidEmailMessage());
			email1 = ""; email2 = "";
		}
		// update button pressed
		else if(request.getParameter(updateButton) != null)
		{
			// if agent exists
			if(adao.checkAgent(agent)) 
			{
				adao.updateAgent(lastname, firstname, address, city, state, country, zipcode, telephone1, telephone2, fax, email1, email2, website, agent);
				clear();
				request.setAttribute(AgentDao.AGENT_FAILED, AgentDao.getAgentUpdatedMessage());
			}
			else // agent doesn't exist
			{
				clear();
				request.setAttribute(AgentDao.AGENT_FAILED, AgentDao.getAgentAgentNotFoundFailed());
			}
		}
		// one of the agents is clicked
		else if(request.getParameter(AgentDao.AGENT_BUTTONS) != null)
		{
			
			String clickedAgent = request.getParameter(AgentDao.AGENT_BUTTONS);
			ArrayList<String> agentInfo = adao.getAgentByAgentName(clickedAgent);
			
			agent = agentInfo.get(AgentDao.AGENT_INDEX); 
			lastname = agentInfo.get(AgentDao.LASTNAME_INDEX);
			firstname = agentInfo.get(AgentDao.FIRSTNAME_INDEX);
			address = agentInfo.get(AgentDao.ADDRESS_INDEX);
			city = agentInfo.get(AgentDao.CITY_INDEX);
			state = agentInfo.get(AgentDao.STATE_INDEX);
			country = agentInfo.get(AgentDao.COUNTRY_INDEX);
			zipcode = agentInfo.get(AgentDao.ZIPCODE_INDEX);
			telephone1 = agentInfo.get(AgentDao.TELEPHONE1_INDEX);
			telephone2 = agentInfo.get(AgentDao.TELEPHONE2_INDEX);
			fax = agentInfo.get(AgentDao.FAX_INDEX);
			email1 = agentInfo.get(AgentDao.EMAIL1_INDEX);
			email2 = agentInfo.get(AgentDao.EMAIL2_INDEX);
			website = agentInfo.get(AgentDao.WEBSITE_INDEX);
		}
		// some other buttons pressed
		else
		{
			Boolean failed = false;
			if(!failed)
			{
				// going though not required fileds
				for(int i=0; i<notRequired.size(); i++)
				{
					// check invaild symbols
					if(ldao.checkInvalidSymbols(notRequired.get(i)))
					{
						request.setAttribute(AgentDao.AGENT_FAILED, LoginDao.getRegisterInvalidSymbolsLFailed());
						failed = true;
						clear();
						break;
					}
				}
			}
			
			if(!failed)
			{
				// going though required fileds
				for(int i=0; i<required.size(); i++)
				{
					// check invaild symbols
					if(ldao.checkInvalidSymbols(required.get(i)))
					{
						request.setAttribute(AgentDao.AGENT_FAILED, LoginDao.getRegisterInvalidSymbolsLFailed());
						failed = true;
						clear();
						break;			
					}
				}
			}
			
			if(!failed)
			{
				// going though required fileds
				for(int i=0; i<required.size(); i++)
				{
					// check empty
					if(required.get(i).equals("") || required.get(i) == null)
					{
						request.setAttribute(AgentDao.AGENT_FAILED, HotelDao.getHotelRequiredFieldFailed());
						failed = true;
						break;
					}
				}
			}
			
			if(failed)
			{
				
			}
			// create button pressed
			else if(request.getParameter(createButton) != null)
			{
				// if agent exists
				if(adao.checkAgent(agent)) 
				{
					clear();
					request.setAttribute(AgentDao.AGENT_FAILED, AgentDao.getAgentAgentAlreadyExistsFailed());
				}
				else // create agent if agent doesn't exist
				{
					adao.addAgent(agent, lastname, firstname, address, city, state, country, zipcode, telephone1, telephone2, fax, email1, email2, website);
					request.setAttribute(AgentDao.AGENT_FAILED, AgentDao.getAgentCreatedMessage());
					clear();
				}
			}
		}
		
		request.setAttribute(AgentDao.AGENT_LIST, adao.getAgents(searchCountry, searchCity));
		
		request.setAttribute(AgentDao.AGENT, 
				 agent+HotelDao.EQUAL+
				 lastname+HotelDao.EQUAL+
				 firstname+HotelDao.EQUAL+
				 address+HotelDao.EQUAL+
				 city+HotelDao.EQUAL+
				 state+HotelDao.EQUAL+
				 country+HotelDao.EQUAL+
				 zipcode+HotelDao.EQUAL+
				 telephone1+HotelDao.EQUAL+
				 telephone2+HotelDao.EQUAL+
				 fax+HotelDao.EQUAL+
				 email1+HotelDao.EQUAL+
				 email2+HotelDao.EQUAL+
				 website+HotelDao.EQUAL+
				 searchCountry+HotelDao.EQUAL+
				 searchCity);

		request.getRequestDispatcher("Agent.jsp").forward(request, response);
		
		
	}
	
	private void clear()
	{
		agent = ""; lastname = ""; firstname = ""; address = ""; city = ""; state = ""; country = ""; zipcode = ""; telephone1 = ""; telephone2 = ""; fax = ""; email1 = ""; email2 = ""; website = "";
	}

}
