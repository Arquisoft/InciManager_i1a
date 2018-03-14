package InciManager.entities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.management.Agent;

public class AgentInfo {

    private String userName;
    private String password;
    private String location;

    public AgentInfo(){}

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}