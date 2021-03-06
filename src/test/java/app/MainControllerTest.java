package app;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URL;

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
import com.app.services.AgentInfoService;

@SuppressWarnings("deprecation")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {MainApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class MainControllerTest {

	@Value("${local.server.port:8081}")
	private int port;

	private MockMvc mockMvc;
	
	@Autowired
	private AgentInfoService agentService;

	@Autowired
	private WebApplicationContext context;
	
	@Before
	public void setUp() throws Exception {
		URL base = new URL("http://localhost:" + port);
		TestRestTemplate template = new TestRestTemplate();
		template.getForEntity(base.toString(), String.class);
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	@Test
	public void createIncorrect() throws Exception {
		String message = mockMvc.perform(get("/create"))
				.andExpect(status().is3xxRedirection())
				.andReturn().getResponse().getErrorMessage();
		assertNull(message);
	}
	
	@Test
	public void createCorrect() throws Exception {
		Agent origin = new Agent("","","lucia123","8",1);
		Agent agent = agentService.findById(origin);
		
		String message = mockMvc.perform(get("/create").sessionAttr("agent", agent))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("Create incident")))
				.andReturn().getResponse().getErrorMessage();
		assertNull(message);
	}

	@Test
	public void sendIncorrect() throws Exception {
		String message = mockMvc.perform(get("/send"))
				.andExpect(status().is3xxRedirection())
				.andReturn().getResponse().getErrorMessage();
		assertNull( message );
	}
	
	@Test
	public void sendCorrect() throws Exception {
		Agent origin = new Agent("","","lucia123","8",1);
		Agent agent = agentService.findById(origin);
		
		String message = mockMvc.perform(get("/send").sessionAttr("agent", agent))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("Incident sent correctly!")))
				.andReturn().getResponse().getErrorMessage();
		assertNull( message );
	}
	
}
