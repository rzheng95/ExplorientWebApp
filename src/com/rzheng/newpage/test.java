package com.rzheng.newpage;

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

		boolean x = Boolean.parseBoolean("1");
		

		ArrayList<Integer> test = new ArrayList<>();
		test.add(0);
		test.add(1);
		test.add(2);
		test.add(3);
		test.add(4);
		
		for(int i=0; i<test.size(); i++)
		{
			if(i != test.size()-1 && test.get(i) == 0)
			{
				test.remove(i);
				//i--;
			}
			System.out.println(test.get(i));
		}
		

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
