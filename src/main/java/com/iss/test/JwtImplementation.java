package com.iss.test;

public class JwtImplementation {
	public static void main(String[] args) {
		
		 String token = JwtUtil.generateToken("user123");
	        System.out.println("Generated Token: " + token);

	        String user = JwtValidator.validateToken(token);
	        System.out.println("Validated User: " + user);
	}

}
