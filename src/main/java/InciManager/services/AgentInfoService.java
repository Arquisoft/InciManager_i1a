package InciManager.services;

import InciManager.entities.AgentInfo;
import InciManager.repositories.AgentInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgentInfoService {
	
    @Autowired
    AgentInfoRepository agentInfoRepository;
    
    public AgentInfo findOne(String id) {
    	return agentInfoRepository.findOne( id );
    }
    
    public String getLocation(AgentInfo agentInfo){
        //TODO
        return "";
    }
}
