package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import entities.AgentInfo;
import entities.Incident;
import producers.KafkaIncidentProducer;
import services.AgentInfoService;
import services.TopicsService;
import validator.AgentInfoValidator;

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

    @RequestMapping("/")
    public String landing(Model model) {
        model.addAttribute("agentInfo", new AgentInfo());
        return "index";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String create(@Validated AgentInfo agentInfo
            ,BindingResult result, Model model){
        agentInfoValidator.validate(agentInfo,result);
        if(result.hasErrors()){
            return "/";
        }
        agentInfo.setLocation(agentService.getLocation(agentInfo));
        Incident incident = new Incident();
        incident.setAgentInfo(agentInfo);
        incident.setLocation(agentInfo.getLocation());
        model.addAttribute("incident",incident);
        model.addAttribute("topics",topicsService.getTopics());
        return "redirect:create";
    }
    
    @RequestMapping("/send")
    public String send(Model model, @ModelAttribute Incident incident) {
        kafkaIncidentProducer.sendIncident(incident);
        return "redirect:/";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String loginPost(Model model) {
    	// TODO
    	return null;
    }

}