package com.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.app.entities.AgentInfo;
import com.app.validator.AgentInfoValidator;

@Controller
public class LoginController {
	
	@Autowired
	private AgentInfoValidator agentInfoValidator;
	
	@RequestMapping(value = "/")
	public String login(Model model) {
		model.addAttribute("agentInfo", new AgentInfo());
		return "login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@Validated AgentInfo agentInfo, BindingResult result, Model model) {

		agentInfoValidator.validate(agentInfo, result);
		if (result.hasErrors()) {
			return "login";
		}
		return "redirect:/create/" + agentInfo.getId();
	}

}
