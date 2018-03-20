package InciManager.controllers;


import InciManager.entities.AgentInfo;
import InciManager.entities.Incident;
import InciManager.producers.KafkaIncidentProducer;
import InciManager.services.AgentInfoService;
import InciManager.services.TopicsService;
import InciManager.validator.AgentInfoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginPost(Model model, @RequestParam(value = "login") String id, @RequestParam(value = "password") String password,
                            @RequestParam(value = "kind") String kind) {
        // If the combination of id and password is correct, the data of the user is returned
        // If not, 404 NOT FOUND is returned
        AgentInfo user = service.getAgent(id, password, Integer.parseInt(kind));

        if (user == null)
            return "usererror";
        else {
            model.addAttribute( "name", user.getName());
            model.addAttribute( "location", user.getLocation());
            model.addAttribute( "email", user.getEmail());
            model.addAttribute( "id", user.getId());
            model.addAttribute( "kindCode", user.getKind());
            if (user.getKind() == 1) model.addAttribute( "kind", "person");
            else if (user.getKind() == 2) model.addAttribute( "kind", "entity");
            else if (user.getKind() == 3) model.addAttribute( "kind", "sensor");

            return "getAgentInfo";
        }

    }

}