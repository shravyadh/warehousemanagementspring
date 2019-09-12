package com.comakeit.wms.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.comakeit.wms.bean.Login;
import com.comakeit.wms.service.LoginService;

@RestController
public class LoginRest {

@Autowired
LoginService loginService;

@PostMapping("/validate")
public Login validate(@RequestBody Login login) throws Exception
{
	 return loginService.validate(login);
}

}
