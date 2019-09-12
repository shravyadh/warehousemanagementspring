package com.example.demo.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Service.LoginService;
import com.example.demo.bean.Login;

@RestController
public class LoginRest {

@Autowired
LoginService loginService;

@PostMapping("/validate")
public Login validate(@RequestBody Login login)
{
	 return loginService.validate(login);
}

}
