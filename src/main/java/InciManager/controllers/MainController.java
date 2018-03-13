package InciManager.controllers;


import InciManager.entities.AgentInfo;
import InciManager.entities.Incident;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import InciManager.producers.KafkaProducer;

@Controller
public class MainController {

    @Autowired
    private KafkaProducer kafkaProducer;

    @RequestMapping("/")
    public String landing(Model model) {
        model.addAttribute("message", new AgentInfo());
        return "index";
    }

    @RequestMapping("/create")
    public String create(Model model, @ModelAttribute AgentInfo agentInfo){
        Incident incident = new Incident();
        incident.setAgentInfo(agentInfo);
        model.addAttribute("incident",incident);

        return "create";
    }
    
    @RequestMapping("/send")
    public String send(Model model, @ModelAttribute Incident incident) {
        kafkaProducer.send(incident.getTopic(), incident.toString());
        return "redirect:/";
    }

}