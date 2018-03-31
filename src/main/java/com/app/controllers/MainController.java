package com.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
	private AgentInfoService agentService;

	@Autowired
	private TopicsService topicsService;

	@Autowired
	private KafkaIncidentProducer kafkaIncidentProducer;

	@RequestMapping(value = "/create/{id}")
	public String create(Model model, @PathVariable("id") Long id) {
		AgentInfo agentInfo = agentService.findById( String.valueOf( id ) );
		Incident i = new Incident();
		i.setAgentInfo(agentInfo);
		i.setLocation(agentInfo.getLocation());
		model.addAttribute("incident", i);
		model.addAttribute("topics", topicsService.getTopics());
		return "create";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String createPost(Model model, @ModelAttribute Incident incident) {
		kafkaIncidentProducer.sendIncident(incident);
		return "send";

	}

	@RequestMapping(value = "/send")
	public String send(Model model) {
		return "send";
	}

}