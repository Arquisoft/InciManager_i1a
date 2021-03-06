package app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
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
@SpringBootTest(classes = {MainApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)

public class EntitiesTest {
	@Value("${local.server.port:8081}")
	private int port;

	@Before
	public void setUp() throws Exception {
		new URL("http://localhost:" + port + "/");
		new TestRestTemplate();
	}

	@Test
	public void testAgent() throws Exception {
        String name = "name";
		String password = "password";
        String email = "mail@mail.com";
        String location = "caceres";
        String NIF = "10203040A";
        int kind = 1;

        Agent user = new Agent(name,email,password,NIF,kind);
        Agent user2 = new Agent(name,email,password,location,NIF,kind);
        
        String[] valores = {name, email, password, location, NIF, String.valueOf(kind)};
        Agent user3 = new Agent(valores);

        assertTrue(user.getName().equals(name));
        assertTrue(user.getPassword().equals(password));
        assertTrue(user.getId().equals(NIF));
        assertTrue(user.getEmail().equals(email));
        assertTrue(user.getLocation().equals(""));
        assertNull(user.getIdautogenerado());
        assertTrue(user.getKind() == 1);

        assertTrue(user2.getName().equals(name));
        assertTrue(user2.getPassword().equals(password));
        assertTrue(user2.getId().equals(NIF));
        assertTrue(user2.getEmail().equals(email));
        assertTrue(user2.getLocation().equals(location));
        assertNull(user2.getIdautogenerado());
        assertTrue(user2.getKind() == 1);
        
        assertTrue(user3.getName().equals(name));
        assertTrue(user3.getPassword().equals(password));
        assertTrue(user3.getId().equals(NIF));
        assertTrue(user3.getEmail().equals(email));
        assertTrue(user3.getLocation().equals(location));
        assertNull(user3.getIdautogenerado());
        assertTrue(user3.getKind() == 1);

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
        
        Agent user4 = new Agent("","","","Málaga","",2);
        
        assertNotNull(user);
        assertTrue(user.equals(user));
        assertFalse(user.equals(null));
        assertFalse(user.equals(new Incident()));
        assertFalse(user.hashCode() == user2.hashCode());
        assertFalse(user.equals(user2));
        assertTrue(user.equals(user4));
        
	}
	
	@Test
	public void testIncident() {
		Agent agent = new Agent();
		String incidentName = "IncidentTest";
		String description = "IncidentTest description";
		List<String> tags = new ArrayList<String>();
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
				+ ", aditionalPropertiesString='null', status='OPEN']", incident.toString());
		
		
		agent.setName("Tester");
		incident.setAgent(agent);
		assertNotNull(incident.getAgent());
		assertTrue(agent.equals(incident.getAgent()));
		
		assertNull(incident.getIdautogenerado());
		
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
		assertTrue(incident.getTags().size() == tags.size());
		
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
		assertTrue(date.equals(incident.getDate()));
		
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

		assertNotNull(incident);
		assertTrue(incident.equals(incident));
		
		Incident incident2 = new Incident();
		assertFalse(incident.hashCode() == incident2.hashCode());
		assertFalse(incident.equals(incident2));
	}

	@Test
	public void testOperator() {
		String username = "Operator1";
		String password = "123456";
		
		Operator operator1 = new Operator(username, password);
		assertEquals("Operator [idautogenerado='null', username='Operator1', password='123456']", operator1.toString());
		
		Operator operator2 = new Operator();
		assertEquals("Operator [idautogenerado='null', username='null', password='null']", operator2.toString());
		
		assertNull(operator1.getIdautogenerado());
		
		String username2 = "Operator2";
		operator2.setUsername(username2);
		assertNotNull(operator2.getUsername());
		assertTrue(username2.equals(operator2.getUsername()));
		
		String password2 = "654321";
		operator2.setPassword(password2);
		assertNotNull(operator2.getPassword());
		assertTrue(password2.equals(operator2.getPassword()));
		
		assertNotNull(operator1);
		assertTrue(operator1.equals(operator1));
		
		assertFalse(operator1.hashCode() == operator2.hashCode());
		assertFalse(operator1.equals(operator2));
	}
	
}
