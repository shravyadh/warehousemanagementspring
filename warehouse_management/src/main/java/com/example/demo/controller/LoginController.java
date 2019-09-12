package com.example.demo.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.bean.Login;
import com.example.demo.constants.Constants;

@Controller
public class LoginController {
	RestTemplate restTemplate = new RestTemplate();

	@RequestMapping(value = "/controller")
	public ModelAndView validate(HttpServletRequest request) {
		String operation = request.getParameter("operation");

		HttpSession session = request.getSession();
		ModelAndView modelView = new ModelAndView();

		if (operation.equals("validate")) {

			String username = request.getParameter("username");
			String password = request.getParameter("password");

			Login user = new Login();
			user.setUsername(username);
			user.setPassword(password);

			user = restTemplate.postForObject(Constants.url + "validate", user, Login.class);

			if (user != null) {
				session.setAttribute("login", user);
				if (user.getRole().getRole_name().equals("Admin")) {
					modelView.setViewName("adminoperations.jsp");
					return modelView;
				} else if (user.getRole().getRole_name().equals("manufacturer")) {
					modelView.setViewName("MerchantOperation?operation=list");
					return modelView;
				}
			} else {
				modelView.setViewName("error.jsp");
				return modelView;
			}
		}
		return null;
	}
}
