package cucumber.steps;

import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.MockitoAnnotations.Mock;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.app.controllers.MainController;
import com.app.entities.Agent;
import com.app.services.AgentInfoService;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class LoginSteps {

	@Mock
	private AgentInfoService agentInfoService;

	private MockMvc mockMvc;

	private ResultActions resultValidLogin;

	@InjectMocks
	private MainController maincontroller;

	@Given("^a list of agents:$")
	public void a_list_of_agents(List<Agent> agents) throws Throwable {
		MockitoAnnotations.initMocks(this);
		for (Agent agent : agents) {
			when(agentInfoService.verifyAgent(agent)).thenReturn(true);
		}
		mockMvc = MockMvcBuilders.standaloneSetup(maincontroller).build();

	}

	@When("^I login with name \"([^\"]*)\", password \"([^\"]*)\" and kind \"([^\"]*)\"$")
	public void i_login_with_name_password_and_kind(String id, String password, String kind) throws Throwable {
		MockHttpServletRequestBuilder request = post("/login").param("id", id).param("password", password).param("kind",
				kind);

		resultValidLogin = mockMvc.perform(request);

	}

	@Then("^I can create a new incident$")
	public void i_can_create_a_new_incident() throws Throwable {
		resultValidLogin.andExpect(content().string((contains("Create incident"))));

	}

}
