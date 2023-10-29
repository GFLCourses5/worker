package executor.service.controller;

import executor.service.service.ScenarioOperations;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(ScenarioSourceController.class)
@AutoConfigureMockMvc
class ScenarioSourceControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ScenarioOperations service;

    @Test
    void receiveScenarios() {

    }

    @Test
    void getScenariosResult() {
    }

    @Test
    void testGetScenariosResult() {
    }

    @Test
    void getScenarioById() {
    }

    @Test
    void deleteResultById() {
    }
}