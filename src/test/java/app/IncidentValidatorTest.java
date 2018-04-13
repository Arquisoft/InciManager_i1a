package app;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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

	private URL base;
	private RestTemplate template;

	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext context;
	
	@Autowired
	private IncidentValidator incidentValidator;
	
	@Before
	public void setUp() throws Exception {
		this.base = new URL("http://localhost:" + port);
		template = new TestRestTemplate();
		template.getForEntity(base.toString(), String.class);
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	@Test
	public void supports() throws Exception {
		assertTrue( incidentValidator.supports( Incident.class ) );
		assertFalse( incidentValidator.supports( Agent.class ) );
		assertFalse( incidentValidator.supports( Operator.class ) );
	}
	
}
