package com.app.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.app.entities.AgentInfo;
import com.app.services.AgentInfoService;

@Component
public class AgentInfoValidator implements Validator {
	
    @Autowired
    private AgentInfoService agentInfoService;
    
    @Override
    public boolean supports(Class<?> aClass) {
        return AgentInfo.class.equals(aClass);
    }
    
    @Override
    public void validate(Object target, Errors errors){
        AgentInfo agentInfo= (AgentInfo) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "id", "error.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "error.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "kind", "error.empty");
        AgentInfo posibleAgent = agentInfoService.findById( agentInfo.getId() );
        if ( posibleAgent == null) {
        	errors.rejectValue("id", "error.user.identification");
        } else if (!posibleAgent.getPassword().equals(agentInfo.getPassword())) {
        	errors.rejectValue("password", "error.password.identification");
        }else if (posibleAgent.getKind()!=agentInfo.getKind()) {
        	errors.rejectValue("kind", "error.kind");
        }
    }
}
