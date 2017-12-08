package com.newpage;

import java.time.temporal.ChronoUnit;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

public class test {

	public static void main(String[] args) 
	{
		
		ArrayList<ArrayList<String>> temp = new ArrayList<>();
		
		LocalDate dateBefore = LocalDate.parse("2017-02-01");
		if(dateBefore.equals(LocalDate.parse("2017-02-01")))
			System.out.println("yes");
		else
			System.out.println("no");
		/*
		 								<% String activity =  ""; %>
								<% String accommodation =  ""; %>
									<% if(tourList.size() > i) { %>
										<% activity = ((ArrayList<String>)tourList.get(i)).get(2).trim(); %>
										<% if(((ArrayList<String>)tourList.get(i)).get(4) != null) {%>
											<% accommodation = ((ArrayList<String>)tourList.get(i)).get(4); }%>
									<% } %>
		 *
		 *
		 *
		 */
				
	}
	
	// yyyy-mm-dd
	public static long daysBetweenDates(String startDate, String endDate)
	{

		
		LocalDate dateBefore = LocalDate.parse(startDate);
		LocalDate dateAfter = LocalDate.parse(endDate);
		
		long noOfDaysBetween = ChronoUnit.DAYS.between(dateBefore, dateAfter);
		
		return noOfDaysBetween;
		
	}
	

	

}
