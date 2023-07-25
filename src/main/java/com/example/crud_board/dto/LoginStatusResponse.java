package com.example.crud_board.dto;

import lombok.Data;

@Data
public class LoginStatusResponse {
	boolean isLoggedIn;
	String username;
	
	public LoginStatusResponse(boolean isLoggedIn, String userName) {
		// TODO Auto-generated constructor stub
		
		this.isLoggedIn = isLoggedIn;
		this.username = userName;
	}
	
	public LoginStatusResponse() {
		isLoggedIn = false;
		username = null;
	}
}
