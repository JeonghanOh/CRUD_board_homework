package com.example.crud_board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.crud_board.dto.LoginDto;
import com.example.crud_board.repository.LoginRepository;

@Service
public class LoginService {
	@Autowired
	LoginRepository loginRepository;
	
	public boolean isOkLogin(LoginDto loginDto) {
		if(loginDto.getPassword().equals(loginRepository.getPassword(loginDto.getUsername()))) {
			return true;
		}
		
		return false;
	}
}