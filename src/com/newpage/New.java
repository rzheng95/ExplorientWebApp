package com.newpage;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.login.LoginDao;

@WebServlet("/New")
public class New extends HttpServlet {
	private int DATE_LENGTH = NewpageDao.DATE_LENGTH;
	private int YEAR = NewpageDao.YEAR;
	private int DAY = NewpageDao.DAY;
	private int MONTH = NewpageDao.MONTH;
	private String FORWARD_SLASH = NewpageDao.FORWARD_SLASH;
	private String EQUAL = NewpageDao.EQUAL;
	private String COMMA = NewpageDao.COMMA;
	private String DASH = NewpageDao.DASH;
	
	private NewpageDao dao;
	private LoginDao ldao;
	private String agent;
	private String customer_id;
	private String destination;
	private String air;
	private String date_of_departure;
	private String date_of_return;
	private String country;
	private String getPackages;
	private String tour_package;
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("New.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		dao = new NewpageDao();
		ldao = new LoginDao();
		
		agent = request.getParameter(NewpageDao.getNewAgent()).trim();
		customer_id = request.getParameter(NewpageDao.getNewCustomerId()).trim();
		destination = request.getParameter(NewpageDao.getNewDestination()).trim();
		air = request.getParameter(NewpageDao.getNewAir()).trim();
		date_of_departure = request.getParameter(NewpageDao.getNewDateOfDeparture()).trim();
		date_of_return =  request.getParameter(NewpageDao.getNewDateOfReturn()).trim();
		country = request.getParameter(NewpageDao.getCountry()).trim();
		getPackages = NewpageDao.getGetPackages();
		tour_package = request.getParameter(NewpageDao.getNewTourPackage()).trim(); 
		
		
		// get Packages button pressed
		if (request.getParameter(getPackages) != null) 
		{
			if(dao.getCountries().contains(country))
			{
				request.setAttribute("tourPackages", dao.getTourPackages(country));
				tour_package = "";
			}
			
		}
		// check for = sign, which is reserved for split method and other symbols
		else if(ldao.checkInvalidSymbols(customer_id) || ldao.checkInvalidSymbols(destination) || ldao.checkInvalidSymbols(air) || ldao.checkInvalidSymbols(tour_package))
		{
			request.setAttribute(NewpageDao.NEWPAGE_FAILED, LoginDao.getRegisterInvalidSymbolsLFailed());
			agent = ""; customer_id = ""; destination = ""; air = ""; date_of_departure = ""; date_of_return = ""; tour_package = "";
		}
		// check for empty field
		else if(agent.isEmpty() || customer_id.isEmpty() || destination.isEmpty() || air.isEmpty() || date_of_departure.isEmpty() || date_of_return.isEmpty() || tour_package.isEmpty())
		{
			request.setAttribute(NewpageDao.NEWPAGE_FAILED, NewpageDao.getEmptydFieldFailed());
		}
		// check if agent exists (check includes agent's firstname and lastname)
		else if(!agent.contains(DASH) || !agent.contains(COMMA) || !dao.checkAgent(agent))
		{
			request.setAttribute(NewpageDao.NEWPAGE_FAILED, NewpageDao.getAgentNotFoundFailed());
			agent = "";
		}
		// check customer id format
		else if(!customer_id.contains(DASH))
		{
			request.setAttribute(NewpageDao.NEWPAGE_FAILED, NewpageDao.getCustomerIdInvalidFailed());
			customer_id = "";
		}
		// check if customer id exists
		else if(dao.checkCustomerId(customer_id))
		{
			request.setAttribute(NewpageDao.NEWPAGE_FAILED, NewpageDao.getCustomerIdExistsFailed());
			customer_id = "";
		}
		// check date format
		else if(!date_of_departure.contains(FORWARD_SLASH) || !date_of_return.contains(FORWARD_SLASH) || !checkDateFormat(date_of_departure) || !checkDateFormat(date_of_return) || !compareDates(date_of_departure, date_of_return))
		{
			request.setAttribute(NewpageDao.NEWPAGE_FAILED, NewpageDao.getDateFormatInvalidFailed());
			date_of_departure = "";
			date_of_return = "";
		}
		// add booking
		else
		{						
			if(agent.contains(DASH))
			{
				String[] arr = agent.split(DASH);
				if(arr.length == 2)
				{
					agent = arr[0].trim();
				}
			}
						
			
			String[] temp = date_of_departure.split(FORWARD_SLASH);
			if(temp.length == DATE_LENGTH)
				date_of_departure = temp[YEAR]+DASH+temp[MONTH]+DASH+temp[DAY];
			
			temp = date_of_return.split(FORWARD_SLASH);
			if(temp.length == DATE_LENGTH)
				date_of_return = temp[YEAR]+DASH+temp[MONTH]+DASH+temp[DAY];
			
			dao.addBooking(agent, customer_id, destination, air, date_of_departure, date_of_return, tour_package);
			
			HashMap<String, String> map = NewpageDao.getHashMap();	
			map.put(NewpageDao.getNewCustomerId(), customer_id); 
			
			response.sendRedirect("BookingCreated");
			return;
		}

		request.setAttribute(NewpageDao.NEWPAGE, 
				agent+EQUAL+
				customer_id+EQUAL+
				destination+EQUAL+
				air+EQUAL+
				date_of_departure+EQUAL+
				date_of_return+EQUAL+
				country+EQUAL+
				tour_package);
		
		request.getRequestDispatcher("New.jsp").forward(request, response);

	}
	
	public boolean checkDateFormat(String date)
	{
		if(date.contains(FORWARD_SLASH))
		{
			String[] temp = date.split(FORWARD_SLASH);
			if(temp.length == DATE_LENGTH)
			{
				for(String i : temp)
					if(i.trim().isEmpty())
						return false;
				
				
				return true;
			}
		}
		return false;
	}

	
	public boolean compareDates(String dop, String dor)
	{
		if(checkDateFormat(dop) && checkDateFormat(dor))
		{
			String[] tempDop = dop.split(FORWARD_SLASH);
			String[] tempDor = dor.split(FORWARD_SLASH);
			
			// 08/21/2017  // 09/01/2017
			if(tempDop.length == DATE_LENGTH && tempDor.length == DATE_LENGTH)
			{
				int dopYear = Integer.parseInt(tempDop[YEAR].trim());
				int dorYear = Integer.parseInt(tempDor[YEAR].trim());
				
				int dopMonth = Integer.parseInt(tempDop[MONTH].trim());
				int dorMonth = Integer.parseInt(tempDor[MONTH].trim());
				
				int dopDay = Integer.parseInt(tempDop[DAY].trim());
				int dorDay = Integer.parseInt(tempDor[DAY].trim());
				
				if(dopYear < dorYear) 
					return true;
				else if(dopYear > dorYear)
					return false;
				else // same year
				{
					if(dopMonth < dorMonth) 
						return true;
					else if(dopMonth > dorMonth)
						return false;
					else // same month
					{
						if(dopDay < dorDay) 
							return true;
						else if(dopDay > dorDay)
							return false;
						else // same day
						{
							return false;
						}
					}
				}
				
				
			}
		}
			
		return false;
	}
}












