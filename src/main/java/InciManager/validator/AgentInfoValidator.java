package InciManager.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import InciManager.entities.AgentInfo;
import InciManager.services.AgentInfoService;

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
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "El usuario no puede ser vacío");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "El password no puede ser vacío");
        AgentInfo posibleAgent = agentInfoService.findOne( agentInfo.getUserName() );
        if ( posibleAgent == null) {
        	errors.rejectValue("username", "El username no está registrado en la base de datos");
        } else if ( posibleAgent.getPassword() != agentInfo.getPassword() ) {
        	errors.rejectValue("password", "La contraseña no coincide con la guardada");
        }
    }
}
