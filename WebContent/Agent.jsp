<!-- 

  This file is part of Explorient Web App
  Copyright (C) 2016-2019 Richard R. Zheng
 
  https://github.com/rzheng95/ExplorientWebApp
  
  All Right Reserved.
 
 -->

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Explorient | Agent</title>
		<link rel="stylesheet" href="CSS/Agent.css" type="text/css">
	</head>
<body>

	<%@include file="Website Template.jsp"%>

		<%
		
		AgentDao adao = new AgentDao();  
		
		// agent list
		ArrayList<String> agents = (ArrayList<String>)request.getAttribute(AgentDao.AGENT_LIST); 
		if(agents != null && agents.size() != 0)
			Collections.sort(agents);
		
		// country list
		ArrayList<String> countries = adao.getCountries(); 
		if(countries != null && countries.size() != 0)
			Collections.sort(countries);
		
		
		// city list
		ArrayList<String> cities = adao.getCities();
		if(cities != null && cities.size() != 0)
			Collections.sort(cities);
		
	
		// search box
		String searchCountry = HotelDao.getHotelSearchCountry();
		String searchCity = HotelDao.getHotelSearchCity();
		
		// buttons
		String getAgentsButton = AgentDao.getAgentGetAgentsButton();
		String createButton = HotelDao.getHotelCreateButton();
		String updateButton = HotelDao.getHotelUpdateButton();
		String deleteButton = HotelDao.getHotelDeleteButton();
		String clearButton = HotelDao.getHotelClearButton();
		
		// vendor variables
		String agent_agent = AgentDao.getAgentAgent();
		String agent_lastname = AgentDao.getAgentLastname();
		String agent_firstname = AgentDao.getAgentFirstname();
		
		String agent_address = HotelDao.getHotelAddress();
		String agent_city = HotelDao.getHotelCity();
		String agent_state = HotelDao.getHotelState();
		String agent_country = HotelDao.getHotelCountry();
		String agent_zipcode = HotelDao.getHotelZipcode();
		String agent_telephone1 = HotelDao.getHotelTelephone1();
		String agent_telephone2 = HotelDao.getHotelTelephone2();
		String agent_fax = HotelDao.getHotelFax();
		String agent_email1 = HotelDao.getHotelEmail1();
		String agent_email2 = HotelDao.getHotelEmail2();
		String agent_website = HotelDao.getHotelWebsite();
		
		
		
		// failed
		String failed = (String)request.getAttribute(AgentDao.AGENT_FAILED);
		
		// enteredValues
		String searchCountryValue = "", searchCityValue = "", agentValue = "", lastnameValue = "", firstnameValue = "", addressValue = "", cityValue = "", stateValue = "", countryValue = "",
		zipcodeValue = "", telephone1Value = "", telephone2Value = "", faxValue = "", email1Value = "", email2Value = "", websiteValue = "";
		String enteredValues = (String)request.getAttribute(AgentDao.AGENT);
		if(enteredValues != null)
		{
			String[] fragment = enteredValues.split(AgentDao.EQUAL, -1);
			if(fragment.length == AgentDao.AGENT_ENTERED_VALUE_LENGTH)
			{
				agentValue = fragment[AgentDao.AGENT_INDEX];
				lastnameValue = fragment[AgentDao.LASTNAME_INDEX];
				firstnameValue = fragment[AgentDao.FIRSTNAME_INDEX];
				addressValue = fragment[AgentDao.ADDRESS_INDEX];
				cityValue = fragment[AgentDao.CITY_INDEX];
				stateValue = fragment[AgentDao.STATE_INDEX];
				countryValue = fragment[AgentDao.COUNTRY_INDEX];
				zipcodeValue = fragment[AgentDao.ZIPCODE_INDEX];
				telephone1Value = fragment[AgentDao.TELEPHONE1_INDEX];
				telephone2Value = fragment[AgentDao.TELEPHONE2_INDEX];
				faxValue = fragment[AgentDao.FAX_INDEX];
				email1Value = fragment[AgentDao.EMAIL1_INDEX];
				email2Value = fragment[AgentDao.EMAIL2_INDEX];
				websiteValue = fragment[AgentDao.WEBSITE_INDEX];	
				searchCountryValue = fragment[AgentDao.SEARCH_COUNTRY_INDEX];
				searchCityValue = fragment[AgentDao.SEARCH_CITY_INDEX];
			}
		}
		
		
		if(failed == null)
		{
			failed = " ";
		}
	
	
	%>


	<div class="content_wrap">
	<form action="Agent" method="post">
		<div class="agent_div">
			
				<div class="agent_search_div">
					<select class="editable-select countries"  name="<%=searchCountry%>"  value="<%=searchCountryValue%>" placeholder="<%=agent_country.substring(1)%>" title="<%=agent_country.substring(1)%>">
						<% for(int i=0; i < countries.size(); i++) { %>
						<option class="selected"><%=countries.get(i) %></option>
						<% } %>
					</select>
					
					<select class="editable-select cities"  name="<%=searchCity%>"  value="<%=searchCityValue %>" placeholder="<%=agent_city.substring(1)%>" title="<%=agent_city.substring(1)%>">
						<% for(int i=0; i < cities.size(); i++) { %>
						<option class="selected"><%=cities.get(i) %></option>
						<% } %>
					</select>
					<input type="submit" class="agent_buttons" id="getAgents" name="<%=getAgentsButton%>" value="<%=getAgentsButton%>">
				</div><br><br>
				
			
				
				<select class="editable-select agent-info" id="agent" name="<%=agent_agent%>"  value="<%=agentValue %>" placeholder="<%=agent_agent%>" title="<%=agent_agent%>"></select>
				
				<select class="editable-select agent-info" id="lastname" name="<%=agent_lastname%>"  value="<%=lastnameValue %>" placeholder="<%=agent_lastname%>" title="<%=agent_lastname%>"></select>
				
				<select class="editable-select agent-info" id="firstname" name="<%=agent_firstname%>"  value="<%=firstnameValue %>" placeholder="<%=agent_firstname%>" title="<%=agent_firstname%>"></select>
				
				<select class="editable-select agent-info" id="address" name="<%=agent_address%>"  value="<%=addressValue %>" placeholder="<%=agent_address%>" title="<%=agent_address%>"></select>
				
				<select class="editable-select agent-info" id="city" name="<%=agent_city%>"  value="<%=cityValue %>" placeholder="<%=agent_city%>" title="<%=agent_city%>"></select>
				
				<select class="editable-select agent-info" id="state" name="<%=agent_state%>"  value="<%=stateValue %>" placeholder="<%=agent_state%>" title="<%=agent_state%>"></select>
				
				<select class="editable-select agent-info" id="country" name="<%=agent_country%>"  value="<%=countryValue %>" placeholder="<%=agent_country%>" title="<%=agent_country%>"></select>
				
				<select class="editable-select agent-info" id="zipcode" name="<%=agent_zipcode%>"  value="<%=zipcodeValue %>" placeholder="<%=agent_zipcode%>" title="<%=agent_zipcode%>"></select>
				
				<select class="editable-select agent-info" id="telephone1" name="<%=agent_telephone1%>"  value="<%=telephone1Value %>" placeholder="<%=agent_telephone1%>" title="<%=agent_telephone1%>"></select>
				
				<select class="editable-select agent-info" id="telephone2" name="<%=agent_telephone2%>"  value="<%=telephone2Value %>" placeholder="<%=agent_telephone2%>" title="<%=agent_telephone2%>"></select>
				
				<select class="editable-select agent-info" id="fax" name="<%=agent_fax%>"  value="<%=faxValue %>" placeholder="<%=agent_fax%>" title="<%=agent_fax%>"></select>
				
				<select class="editable-select agent-info" id="email1" name="<%=agent_email1%>"  value="<%=email1Value %>" placeholder="<%=agent_email1%>" title="<%=agent_email1%>"></select>
				
				<select class="editable-select agent-info" id="email2" name="<%=agent_email2%>"  value="<%=email2Value %>" placeholder="<%=agent_email2%>" title="<%=agent_email2%>"></select>
				
				<select class="editable-select agent-info" id="website" name="<%=agent_website%>"  value="<%=websiteValue %>" placeholder="<%=agent_website%>" title="<%=agent_website%>"></select>
				
	
				<p class="failedMessages" ><%=failed %></p>
				
				<br><br>
				
				<input type="submit" class="agent_buttons" id="create" name="<%=createButton%>" value="<%=createButton%>">
				<input type="submit" class="agent_buttons" id="update" name="<%=updateButton%>" value="<%=updateButton%>">
				<input type="submit" class="agent_buttons" id="delete" name="<%=deleteButton%>" value="<%=deleteButton%>">
				<input type="submit" class="agent_buttons" id="clear" name="<%=clearButton%>" value="<%=clearButton%>">
				
				
				
		
			
		</div>
		<div class="agent_div">
			<div class="display_panel">
			<div id="display_panel_background"></div> 
				<h2 id="agent_h3">Agent List</h2>

						<%if(agents != null && !agents.isEmpty()) { %>
						<%	for(int i=0; i < agents.size(); i++) { %>
							<input type="submit" class="agents"  name="<%=AgentDao.AGENT_BUTTONS %>" value="<%=agents.get(i)%>">  
							<% } %>
						<% } %>

			</div>
		</div>
	</form>
	
	
	
	
	</div>

</body>
</html>