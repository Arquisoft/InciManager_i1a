package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import entities.AgentInfo;
import repositories.AgentInfoRepository;

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
