package app;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
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
import com.app.validator.AgentInfoValidator;

@SuppressWarnings("deprecation")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {MainApplication.class})
@WebAppConfiguration
@IntegrationTest({"server.port=0"})
public class AgentInfoValidatorTest {
	
	@Value("${local.server.port}")
	private int port;

	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext context;
	
	@Autowired
	private AgentInfoValidator agentValidator;
	
	@Before
	public void setUp() throws Exception {
		URL base = new URL("http://localhost:" + port);
		RestTemplate template = new TestRestTemplate();
		template.getForEntity(base.toString(), String.class);
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	@Test
	public void supports() throws Exception {
		assertTrue( agentValidator.supports( Agent.class ) );
		assertFalse( agentValidator.supports( Incident.class ) );
		assertFalse( agentValidator.supports( Operator.class ) );
	}
	
	@Test
	public void loginInCorrecto() throws Exception {
		//id vacio
		String message = mockMvc.perform(post("/login")
				.param("id", "")
				.param("password", "lucia123")
				.param("kind", "1"))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("Log in")))
				.andExpect(content().string(containsString("This field must not be empty")))
				.andReturn().getResponse().getErrorMessage();
		assertNull( message );

		//password vacio
		message = mockMvc.perform(post("/login")
				.param("id", "8")
				.param("password", "")
				.param("kind", "1"))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("Log in")))
				.andExpect(content().string(containsString("This field must not be empty")))
				.andReturn().getResponse().getErrorMessage();
		assertNull( message );

		//kind vacio
		message = mockMvc.perform(post("/login")
				.param("id", "8")
				.param("password", "lucia123")
				.param("kind", "0"))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("Log in")))
				.andExpect(content().string(containsString("Wrong kind")))
				.andReturn().getResponse().getErrorMessage();
		assertNull( message );

		//all vacio
		message = mockMvc.perform(post("/login")
				.param("id", "")
				.param("password", "")
				.param("kind", ""))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("Log in")))
				.andExpect(content().string(containsString("This field must not be empty")))
				.andExpect(content().string(containsString("Wrong kind")))
				.andReturn().getResponse().getErrorMessage();
		assertNull( message );

		//not found
		message = mockMvc.perform(post("/login")
				.param("id", "7")
				.param("password", "lucia123")
				.param("kind", "1"))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("Log in")))
				.andExpect(content().string(containsString("Username not registered in the data base")))
				.andReturn().getResponse().getErrorMessage();
		assertNull( message );

		//not found
		message = mockMvc.perform(post("/login")
				.param("id", "8")
				.param("password", "lucia13")
				.param("kind", "1"))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("Log in")))
				.andExpect(content().string(containsString("Wrong password")))
				.andReturn().getResponse().getErrorMessage();
		assertNull( message );

		//not found
		message = mockMvc.perform(post("/login")
				.param("id", "8")
				.param("password", "lucia123")
				.param("kind", "2"))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("Log in")))
				.andExpect(content().string(containsString("Wrong kind")))
				.andReturn().getResponse().getErrorMessage();
		assertNull( message );
	}

}
