package com.iss.test;

public class TypeCastingOfObject {
	public static void main(String[] args) {
		String noStr="190";
		System.out.println("No string : "+(noStr+10));
		
		Integer noInt=new Integer(noStr);
		System.out.println("No string : "+(noInt+10));
		
		String noStr1=Integer.toString(noInt);
		System.out.println(" Con to String : "+noStr1);
		
	}

}
