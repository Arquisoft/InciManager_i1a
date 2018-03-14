package InciManager.validator;

import InciManager.entities.AgentInfo;
import InciManager.services.AgentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class AgentInfoValidator implements Validator{
    @Autowired
    private AgentInfoService agentInfoService;
    @Override
    public boolean supports(Class<?> aClass) {
        return AgentInfo.class.equals(aClass);
    }
    @Override
    public void validate(Object target, Errors errors){
        AgentInfo agentInfo=(AgentInfo)target;
        //TODO
    }
}
