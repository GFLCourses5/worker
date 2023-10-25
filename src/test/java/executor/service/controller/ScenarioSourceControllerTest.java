package executor.service.controller;

import executor.service.Routes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RestScenarioController.class)
class ScenarioSourceControllerTest {

    @Autowired
    private MockMvc mock;

    @Test
    public void whenReceiveValidScenario() throws Exception {
        //HERE HAVE TO BE SCENARIO DTO!!!!!!! any REQUEST CONTROLLER
        //RETURN new ResponseEntity()!!!!!!!! FIX THIS!!!!!!!!!
        mock.perform(MockMvcRequestBuilders
                        .post(Routes.SCENARIOS)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void whenReceiveValidScenarios() throws Exception {

        mock.perform(MockMvcRequestBuilders
                        .post(Routes.SCENARIOS + "list")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}