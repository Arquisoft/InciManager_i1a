package app;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URL;

import com.app.services.AgentInfoService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.app.MainApplication;
import com.app.entities.Agent;
import com.app.entities.Incident;
import com.app.entities.Operator;
import com.app.validator.IncidentValidator;

@SuppressWarnings("deprecation")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {MainApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class IncidentValidatorTest {

	@Value("${local.server.port:8081}")
	private int port;

	private MockMvc mockMvc;

	@Autowired
	private AgentInfoService agentService;

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private IncidentValidator incidentValidator;

	@Before
	public void setUp() throws Exception {
		URL base = new URL("http://localhost:" + port);
		TestRestTemplate template = new TestRestTemplate();
		template.getForEntity(base.toString(), String.class);
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	public void supports() throws Exception {
		assertTrue( incidentValidator.supports( Incident.class ) );
		assertFalse( incidentValidator.supports( Agent.class ) );
		assertFalse( incidentValidator.supports( Operator.class ) );
	}
	@Test
	public void createPostCorrect() throws Exception {
		String message = mockMvc.perform(post("/create")
				.param("incidentName","Incendio!!")
				.param("description", "Fire")
				.param("locationString", "1.54,5.84")
				.param("tags", "Fuego")
				.param("aditionalPropertiesString", "montaña/rocosa, fuego/caliente")
				.param("topic", "Fuego"))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("Incident sent correctly")))
				.andReturn().getResponse().toString();
		assertNotNull(message);
	}
	@Test
	public void createPostIncorrect() throws Exception {
		Agent origin = new Agent("","","lucia123","8",1);
		Agent agent = agentService.findById(origin);

		//all empty
		String message = mockMvc.perform(post("/create").sessionAttr("agent", agent)
				.param("incidentName","")
				.param("description", "")
				.param("locationString", "")
				.param("tags", "")
				.param("topic", ""))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("Create incident")))
				.andExpect(content().string(containsString("Location entered incorrectly.")))
				.andExpect(content().string(containsString("This field must not be empty")))
				.andReturn().getResponse().toString();
		assertNotNull(message);

		//incident empty
		message = mockMvc.perform(post("/create").sessionAttr("agent", agent)
				.param("incidentName","")
				.param("description", "Fire")
				.param("locationString", "1.54,5.84")
				.param("tags", "Fuego")
				.param("topic", "Fuego"))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("Create incident")))
				.andExpect(content().string(containsString("This field must not be empty")))
				.andReturn().getResponse().toString();
		assertNotNull(message);

		//description empty
		message = mockMvc.perform(post("/create").sessionAttr("agent", agent)
				.param("incidentName","Incendio!!")
				.param("description", "")
				.param("locationString", "1.54,5.84")
				.param("tags", "Fuego")
				.param("topic", "Fuego"))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("Create incident")))
				.andExpect(content().string(containsString("This field must not be empty")))
				.andReturn().getResponse().toString();
		assertNotNull(message);

		//location empty
		message = mockMvc.perform(post("/create").sessionAttr("agent", agent)
				.param("incidentName","Incendio!!")
				.param("description", "Fire")
				.param("locationString", "")
				.param("tags", "Fuego")
				.param("topic", "Fuego"))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("Create incident")))
				.andExpect(content().string(containsString("This field must not be empty")))
				.andReturn().getResponse().toString();
		assertNotNull(message);

		//tags empty
		message = mockMvc.perform(post("/create").sessionAttr("agent", agent)
				.param("incidentName","Incendio!!")
				.param("description", "Fire")
				.param("locationString", "1.54,5.84")
				.param("tags", "")
				.param("topic", "Fuego"))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("Create incident")))
				.andExpect(content().string(containsString("This field must not be empty")))
				.andReturn().getResponse().toString();
		assertNotNull(message);

		//wrong location
		message = mockMvc.perform(post("/create").sessionAttr("agent", agent)
				.param("incidentName","Incendio!!")
				.param("description", "Fire")
				.param("locationString", "1.54 5.84")
				.param("tags", "Fuego")
				.param("topic", "Fuego"))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("Create incident")))
				.andExpect(content().string(containsString("Location entered incorrectly.")))
				.andReturn().getResponse().toString();
		assertNotNull(message);

		//aditionalProperties wrong 1
		message = mockMvc.perform(post("/create").sessionAttr("agent", agent)
				.param("incidentName","Incendio!!")
				.param("description", "Fire")
				.param("locationString", "1.54,5.84")
				.param("tags", "Fuego")
				.param("aditionalPropertiesString", "Montaña, mucho fuego")
				.param("topic", "Fuego"))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("Create incident")))
				.andExpect(content().string(containsString("Additional properties entered incorrectly.")))
				.andReturn().getResponse().toString();
		assertNotNull(message);

		//aditionalProperties wrong 2
		message = mockMvc.perform(post("/create").sessionAttr("agent", agent)
				.param("incidentName","Incendio!!")
				.param("description", "Fire")
				.param("locationString", "1.54,5.84")
				.param("tags", "Fuego")
				.param("aditionalPropertiesString", "Montaña y mucho fuego")
				.param("topic", "Fuego"))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("Create incident")))
				.andExpect(content().string(containsString("Additional properties entered incorrectly.")))
				.andReturn().getResponse().toString();
		assertNotNull(message);
	}

}
