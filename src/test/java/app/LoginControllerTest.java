package app;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

@SuppressWarnings("deprecation")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {MainApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class LoginControllerTest {

	@Value("${local.server.port:8081}")
	private int port;

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	@Before
	public void setUp() throws Exception {
		URL base = new URL("http://localhost:" + port + "/login");
		TestRestTemplate template = new TestRestTemplate();
		template.getForEntity(base.toString(), String.class);
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	public void getLanding() throws Exception {
		String message = mockMvc.perform(get("/"))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("Log in")))
				.andExpect(content().string(containsString("Username:")))
				.andExpect(content().string(containsString("Password:")))
				.andExpect(content().string(containsString("Kind:")))
				.andReturn().getResponse().getErrorMessage();
		assertNull( message );
	}

	@Test
	public void loginCorrecto() throws Exception {
		String message = mockMvc.perform(post("/login")
				.param("id", "8")
				.param("password", "lucia123")
				.param("kind","1"))
				.andExpect(status().is3xxRedirection())
				.andReturn().getResponse().getErrorMessage();
		assertNull( message );
	}

}
