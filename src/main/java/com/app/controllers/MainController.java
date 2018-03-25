package com.app.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.app.entities.AgentInfo;
import com.app.entities.Incident;
import com.app.producers.KafkaIncidentProducer;
import com.app.services.AgentInfoService;
import com.app.services.TopicsService;
import com.app.validator.AgentInfoValidator;

@Controller
public class MainController {

	@Autowired
	private AgentInfoValidator agentInfoValidator;

	@Autowired
	private AgentInfoService agentService;

	@Autowired
	private TopicsService topicsService;

	@Autowired
	private KafkaIncidentProducer kafkaIncidentProducer;

	/*
	 * @RequestMapping("/") public String landing(Model model) {
	 * model.addAttribute("agentInfo", new AgentInfo()); return "index"; }
	 */
	@RequestMapping(value = "/")
	public String login(Model model) {
		model.addAttribute("agentInfo", new AgentInfo());
		return "login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@Validated AgentInfo agentInfo, BindingResult result, Model model) {
		/*
		 * agentInfoValidator.validate(agentInfo,result); if(result.hasErrors()){ return
		 * "login"; }
		 */
		return "create";
	}

/*	@RequestMapping("/create")
	public String create(Model model) {
		model.addAttribute("inident", new Incident());
		return "create";
	}*/

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String createPost(Model model, Principal principal) {

		AgentInfo agentInfo = agentService.findOne(principal.getName());
		agentInfo.setLocation(agentService.getLocation(agentInfo));
		Incident incident = new Incident();
		incident.setAgentInfo(agentInfo);
		incident.setLocation(agentInfo.getLocation());
		model.addAttribute("incident", incident);
		model.addAttribute("topics", topicsService.getTopics());
		return "redirect:create";

	}
	
	 @RequestMapping("/send")
	    public String send(Model model, @ModelAttribute Incident incident) {
	        kafkaIncidentProducer.sendIncident(incident);
	        return "redirect:/";
	    }

}