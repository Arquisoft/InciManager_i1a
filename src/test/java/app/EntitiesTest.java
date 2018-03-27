package app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.app.MainApplication;
import com.app.entities.Agent;
import com.app.entities.Incident;
import com.app.entities.Incident.IncidentStatus;
import com.app.entities.Operator;
import com.app.utils.LatLng;

@SuppressWarnings("deprecation")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MainApplication.class)
@WebAppConfiguration
@IntegrationTest({"server.port=0"})
public class EntitiesTest {
	@Value("${local.server.port}")
	private int port;

	@Before
	public void setUp() throws Exception {
		new URL("http://localhost:" + port + "/");
		new TestRestTemplate();
	}

	@Test
	public void testAgent() throws Exception {
		String password;
        String name;
        String email;
        String location;
        String NIF;
        int kind;

        name = "name";
        password = "password";
        email = "mail@mail.com";
        location = "caceres";
        NIF = "10203040A";
        kind = 1;

        Agent user = new Agent(name,email,password,NIF,kind);
        Agent user2 = new Agent(name,email,password,location,NIF,kind);

        assertTrue(user.getPassword().equals(password));
        assertTrue(user.getName().equals(name));
        assertTrue(user.getId().equals(NIF));
        assertTrue(user.getEmail().equals(email));
        assertTrue(user.getLocation().equals(""));
        assertTrue(user.getIdautogenerado() == null);
        assertTrue(user.getKind() == 1);

        assertTrue(user2.getPassword().equals(password));
        assertTrue(user2.getName().equals(name));
        assertTrue(user2.getId().equals(NIF));
        assertTrue(user2.getEmail().equals(email));
        assertTrue(user2.getLocation().equals(location));
        assertTrue(user2.getIdautogenerado() == null);
        assertTrue(user2.getKind() == 1);

        assertEquals("Agent [idautogenerado='null', name='name'" +
                ", email='mail@mail.com', password='password', location='', id='10203040A'" +
                ", kind=1]", user.toString());

        assertEquals("Agent [idautogenerado='null', name='name'" +
                ", email='mail@mail.com', password='password', location='caceres', id='10203040A'" +
                ", kind=1]", user2.toString());

        assertFalse(user.hashCode() == user2.hashCode());
        assertFalse(user.equals(user2));
        
        user.setPassword("");
        assertFalse(user.getPassword().equals(password));
        user.setName("");
        assertFalse(user.getName().equals(name));
        user.setId("");
        assertFalse(user.getId().equals(NIF));
        user.setEmail("");
        assertFalse(user.getEmail().equals(email));
        user.setLocation("Málaga");
        assertFalse(user.getLocation().equals(location));
        user.setKind(2);
        assertFalse(user.getKind() == kind);
        
        assertEquals("Agent [idautogenerado='null', name=''" +
                ", email='', password='', location='Málaga', id=''" +
                ", kind=2]", user.toString());
        
        assertFalse(user.hashCode() == user2.hashCode());
        assertFalse(user.equals(user2));
        
	}
	
	@Test
	public void testIncident() {
		Agent agent = new Agent();
		String incidentName = "IncidentTest";
		String description = "IncidentTest description";
		List<String> tags = new ArrayList<String>();;
		Operator operator = new Operator(); 
		String topic = "Esto es una prueba";
		String locationString = "43.354872,-5.8513731";
		double lat = Double.parseDouble("43.354872");
		double lng = Double.parseDouble("-5.8513731");
		LatLng location = new LatLng(lat, lng);
		Date date = new Date();
		Map<String, String> aditionalProperties = new HashMap<String, String>();
		String aditionalPropertiesString;
		IncidentStatus status = IncidentStatus.OPEN;
		
		Incident incident = new Incident();
		assertEquals("Incident [agent='null', incidentName='null', description='null'"
				+ ", tags='[]', operator='null', topic='null', locationString='null'"
				+ ", location='null', date='null', aditionalProperties='{}'"
				+ ", aditionalPropertiesString='null', status='null']", incident.toString());
		
		
		agent.setName("Tester");
		incident.setAgent(agent);
		assertNotNull(incident.getAgent());
		assertTrue(agent.equals(incident.getAgent()));
		
		incident.setIncidentName(incidentName);
		assertNotNull(incident.getIncidentName());
		assertTrue(incidentName.equals(incident.getIncidentName()));
		
		incident.setDescription(description);
		assertNotNull(incident.getIncidentName());
		assertTrue(description.equals(incident.getDescription()));
		
		tags.add("Test");
		tags.add("Troll");
		incident.setTags(tags);
		assertNotNull(incident.getTags());
		assertTrue(tags.equals(incident.getTags()));
		assertTrue(incident.getTags().size() == 2);
		
		operator.setUsername("TesterOperator");
		incident.setOperator(operator);
		assertNotNull(incident.getOperator());
		assertTrue(operator.equals(incident.getOperator()));
		
		incident.setTopic(topic);
		assertNotNull(incident.getTopic());
		assertTrue(topic.equals(incident.getTopic()));
		
		incident.setLocationString(locationString);
		assertNotNull(incident.getLocationString());
		assertTrue(locationString.equals(incident.getLocationString()));
		
		incident.setLocation(location);
		assertNotNull(incident.getLocation());
		assertTrue(location.equals(incident.getLocation()));
		
		incident.setDate(date);
		assertNotNull(incident.getDate());
		assertTrue(new Date().after(incident.getDate()));
		
		aditionalProperties.put("Temperatura", "Muy alta");
		incident.setAditionalProperties(aditionalProperties);
		assertNotNull(incident.getAditionalProperties());
		assertFalse(incident.getAditionalProperties().isEmpty());
		assertTrue(aditionalProperties.equals(incident.getAditionalProperties()));
		
		aditionalPropertiesString = "Temperatura muy alta";
		incident.setAditionalPropertiesString(aditionalPropertiesString);
		assertNotNull(incident.getAditionalPropertiesString());
		assertTrue(incident.getAditionalPropertiesString().equals(aditionalPropertiesString));
		
		incident.setStatus(status);
		assertNotNull(incident.getStatus());
		assertTrue(status.equals(incident.getStatus()));
		
		Incident incident2 = new Incident();
		assertFalse(incident.hashCode() == incident2.hashCode());
		assertFalse(incident.equals(incident2));
	}

}
