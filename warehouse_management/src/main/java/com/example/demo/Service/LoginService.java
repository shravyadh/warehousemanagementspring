package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Repository.LoginRepository;
import com.example.demo.Repository.LoginRepositoryInterface;
import com.example.demo.bean.Login;

@Service
public class LoginService implements LoginRepositoryInterface {
	@Autowired
	LoginRepository loginRepository;

	@Override
	public Login validate(Login login) {
		return loginRepository.validate(login.getUsername(), login.getPassword());
	}
}
