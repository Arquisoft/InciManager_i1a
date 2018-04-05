package com.app.services;

import org.jboss.netty.handler.codec.http.HttpMethod;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.app.entities.AgentInfo;
import com.app.repositories.AgentInfoRepository;

@Service
public class AgentInfoService {
	
	@Value("http://localhost:8080/login")
	private String url;

	@Autowired
	AgentInfoRepository agentInfoRepository;

	public AgentInfo findById(String id) {
		return agentInfoRepository.findById(id);
	}

	
	public String getLocation(AgentInfo agentInfo) {
		// TODO
		return "";
	}
	
	public boolean verifyAgent(AgentInfo agentInfo) {
		HttpStatus response;
		
		try {
			HttpHeaders header = new HttpHeaders();
			header.setContentType(MediaType.APPLICATION_JSON);
			
			JSONObject request = new JSONObject();
			request.put("login", agentInfo.getId());
			request.put("password", agentInfo.getPassword());
			request.put("kind", agentInfo.getKind());
			
			HttpEntity<String> entity = new HttpEntity<>(request.toString(), header);
			response = this.getResponseStatus(url, HttpMethod.POST, entity);
		} catch (Exception e) {
			return false;
		}
		
		return response.equals(HttpStatus.OK);
	}
	
	public HttpStatus getResponseStatus(String url, HttpMethod method, HttpEntity<String> entity) {
		ResponseEntity<String> response = new RestTemplate().exchange(url, method, entity, String.class);
        return response.getStatusCode();
	}
}
