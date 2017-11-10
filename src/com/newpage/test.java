package com.newpage;

import java.util.ArrayList;

public class test {

	public static void main(String[] args) 
	{
		NewpageDao np = new NewpageDao();
		ArrayList<String> x = np.getBookingInfoByCustomerId("Test-1234");
		
		for(String i : x)
		{
			System.out.println(i);
		}

				
	}

}
