package com.comakeit.wms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.comakeit.wms.bean.Login;
import com.comakeit.wms.exception.WMSUnAuthorizedException;
import com.comakeit.wms.repository.LoginRepository;

@Service
public class LoginService implements ILogin{
	@Autowired
	LoginRepository loginRepository;

	@Override
	public Login validate(Login login) throws Exception{
		// Validation 
		Login loginRes = loginRepository.validate(login.getUsername(), login.getPassword());
		if(loginRes == null) {
			
			throw new WMSUnAuthorizedException("Invalid Credentials");
		}
		return loginRes;
	}
}
