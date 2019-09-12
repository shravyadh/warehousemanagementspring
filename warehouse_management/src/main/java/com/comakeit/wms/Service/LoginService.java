package com.comakeit.wms.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comakeit.wms.Repository.LoginRepository;
import com.comakeit.wms.bean.Login;

@Service
public class LoginService implements ILogin{
	@Autowired
	LoginRepository loginRepository;

	@Override
	public Login validate(Login login) {
		
		return  loginRepository.validate(login.getUsername(), login.getPassword());
	}
}
