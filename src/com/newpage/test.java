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

		ArrayList<String> x = new ArrayList<>();
		
		x.add(0, "one");
		x.add(5, "five");
		
		System.out.println(x.get(0));
		

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
