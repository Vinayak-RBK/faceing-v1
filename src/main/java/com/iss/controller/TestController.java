package com.iss.controller;

import java.util.Map;

import org.opencv.core.Core;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iss.dao.UserDao;
import com.iss.service.JsonWebTokenService;
import com.iss.util.JwtUtil;

@RestController
//@RequestMapping("/auth")
public class TestController {
	
	@Autowired
	private JsonWebTokenService jwtService;

	private final JwtUtil jwtUtil;

    public TestController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }
	
	@PostMapping("/loginJWT")
    public ResponseEntity<?> login(@RequestBody UserDao dao) {
		System.out.println("This is Login method");
//        String username = request.get("username");
//        String password = request.get("password");
		 System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

//        if ("admin".equals(username) && "password".equals(password)) { // Mock authentication
        if(true) {
        	dao.setUserId("1234567890");
            String token = jwtUtil.generateToken(dao.getUserId(),new Long(10));
            jwtService.createUSerWithToken("", token, token, "date",new Long(10),new Long(10));
            
            return ResponseEntity.ok(Map.of("token", token));
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
	
	 @GetMapping("/hello")
	    public String hello() {
	        return "Hello, Authenticated User!";
	    }
}
