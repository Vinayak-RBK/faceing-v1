package com.iss.validate;


import com.iss.util.JwtUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

public class JwtValidator {

	public static String validateToken(String token) {
        Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(JwtUtil.key)
                .build()
                .parseClaimsJws(token);

        return claimsJws.getBody().getSubject();
    }
	
}
