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

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class CreateIncidenSteps {

	@Mock
	private AgentInfoService agentInfoService;

	private MockMvc mockMvc;

	private ResultActions resultValidCreationIncident;

	private ResultActions resultValidLogin;

	@InjectMocks
	private MainController maincontroller;

	@Given("^an Agents:$")
	public void an_Agents(List<Agent> agents) throws Throwable {
		MockitoAnnotations.initMocks(this);
		when(agentInfoService.verifyAgent(agents.get(0))).thenReturn(true);

		mockMvc = MockMvcBuilders.standaloneSetup(maincontroller).build();
	}

	@When("^I login as an agent with name \"([^\"]*)\", password \"([^\"]*)\" and kind \"([^\"]*)\"$")
	public void i_login_as_an_agent_with_name_password_and_kind(String id, String password, String kind)
			throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		MockHttpServletRequestBuilder request = post("/login").param("id", id).param("password", password).param("kind",
				kind);

		resultValidLogin = mockMvc.perform(request);
	}

	@And("^I can create a new incident with incidentName ,\"([^\"]*)\" , description \"([^\"]*)\" , locationString \"([^\"]*)\" , tags \"([^\"]*)\" , aditionalPropertiesString \"([^\"]*)\" , topic \"([^\"]*)\"$")
	public void i_can_create_a_new_incident_with_incidentName_description_locationString_tags_aditionalPropertiesString_topic(
			String incidentName, String description, String locationString, String tags,
			String aditionalPropertiesString, String topic) throws Throwable {

		MockHttpServletRequestBuilder request = post("/create").param("incidentName", incidentName)
				.param("description", description).param("locationString", locationString).param("tags", tags)
				.param("aditionalPropertiesString", aditionalPropertiesString).param("topic", topic);

		resultValidCreationIncident = mockMvc.perform(request);
	}

	@Then("^I can verify it$")
	public void i_can_verify_it() throws Throwable {
		resultValidCreationIncident.andExpect(content().string((contains("Incident sent correctly!"))));
	}

}
