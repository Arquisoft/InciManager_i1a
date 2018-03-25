package com.app.entities;

import java.util.List;
import java.util.Map;

/**
 * Created by herminio on 27/2/17.
 */
public class Incident {
    private AgentInfo agentInfo;
    private String incidentName;
    private String description;
    private String location;
    private List<String> tags;
    private Map<String, String> aditionalProperties;
    private String topic;
    private Status status;
    public String toString(){
        return incidentName;
    }

    public enum Status{OPEN,IN_PROCESS,CLOSED,CANCELLED}

    public Incident (){}

    public String getIncidentName() {
        return incidentName;
    }

    public void setIncidentName(String incidentName) {
        this.incidentName = incidentName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Map<String, String> getAditionalProperties() {
        return aditionalProperties;
    }

    public void setAditionalProperties(Map<String, String> aditionalProperties) {
        this.aditionalProperties = aditionalProperties;
    }
    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public AgentInfo getAgentInfo() {
        return agentInfo;
    }

    public void setAgentInfo(AgentInfo agentInfo) {
        this.agentInfo = agentInfo;
    }

}