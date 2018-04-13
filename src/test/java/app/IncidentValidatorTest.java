package app;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import com.app.MainApplication;
import com.app.entities.Agent;
import com.app.entities.Incident;
import com.app.entities.Operator;
import com.app.validator.IncidentValidator;

@SuppressWarnings("deprecation")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {MainApplication.class})
@WebAppConfiguration
@IntegrationTest({"server.port=0"})
public class IncidentValidatorTest {

	@Value("${local.server.port}")
	private int port;

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private IncidentValidator incidentValidator;

	@Before
	public void setUp() throws Exception {
		URL base = new URL("http://localhost:" + port);
		RestTemplate template = new TestRestTemplate();
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
	public void createPostIncorrect() throws Exception {
		//all empty
		String message = mockMvc.perform(post("/create")
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
		message = mockMvc.perform(post("/create")
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
		message = mockMvc.perform(post("/create")
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
		message = mockMvc.perform(post("/create")
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
		message = mockMvc.perform(post("/create")
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
	}

}
