package InciManager.entities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.management.Agent;

public class AgentInfo {
	
	// Log
	//private static final Logger LOG = LoggerFactory.getLogger(UserInfo.class);

    private String userName;
    private String password;

//    public AgentInfo(String userName, String password) {
//    	//LOG.info("Creating user " + name + ". age: " + age);
//        this.userName = userName;
//        this.password = password;
//    }
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
}