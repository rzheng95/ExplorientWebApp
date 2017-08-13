package com.login;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String tempCookie = "richard@explorient.com==Zheng";
		String[] cookieFragment = tempCookie.split("=", -1);
		
		for(String i : cookieFragment)
			System.out.println(i);
		
		System.out.println("lenth is: "+cookieFragment.length);
		System.out.println("first element is: "+cookieFragment[0]);
		
		System.out.println("First is: "+first("richard@explorient.com==Zheng"));

	}
	// ==Zheng
	// richard@explorient.com==Zheng
	// richard@explorient.com==
	// =Richard=Zheng
	// richard@explorient.com=Richard=
	// =Richard=
	
	public static String first(String text)
	{
		String returnString = "";
		for(int i=0; i<text.length(); i++)
		{
			if(text.substring(i, i+1).equals("="))
			{
				if(!text.substring(0, 1).equals("="))
					returnString = text.substring(0, i-1);
				
			}
				
		}
		
		return returnString;
	}

}
