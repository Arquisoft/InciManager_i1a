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
import com.app.services.AgentInfoService;

@SuppressWarnings("deprecation")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {MainApplication.class})
@WebAppConfiguration
@IntegrationTest({"server.port=0"})
public class MainControllerTest {

	@Value("${local.server.port}")
	private int port;

	private URL base;
	private RestTemplate template;

	private MockMvc mockMvc;
	
	@Autowired
	private AgentInfoService agentService;

	@Autowired
	private WebApplicationContext context;
	
	@Before
	public void setUp() throws Exception {
		this.base = new URL("http://localhost:" + port);
		template = new TestRestTemplate();
		template.getForEntity(base.toString(), String.class);
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	@Test
	public void createIncorrect() throws Exception {
		template.getForEntity(base.toString(), String.class);
		String message = mockMvc.perform(get("/create/8"))
				.andExpect(status().is3xxRedirection())
				.andReturn().getResponse().getErrorMessage();
		assertNull(message);
	}
	
	@Test
	public void createCorrect() throws Exception {
		Agent origin = new Agent("","","lucia123","8",1);
		Agent agent = agentService.findById(origin);
		
		String message = mockMvc.perform(get("/create/8").sessionAttr("agent", agent))
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
