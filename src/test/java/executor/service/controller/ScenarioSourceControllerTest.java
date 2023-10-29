package executor.service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import executor.service.exceptions.ScenarioResultExceptions;
import executor.service.model.Step;
import executor.service.model.request.Scenario;
import executor.service.model.request.ScenariosRequest;
import executor.service.model.request.StepRequest;
import executor.service.model.response.ScenarioResultResponse;
import executor.service.model.response.StepResultResponse;
import executor.service.service.ScenarioOperations;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static executor.service.Routes.SCENARIOS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(ScenarioSourceController.class)
@AutoConfigureMockMvc(addFilters = false)
class ScenarioSourceControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ScenarioOperations service;

    private ObjectMapper objectMapper = new ObjectMapper();

    private final static OffsetDateTime now = OffsetDateTime.now();

    @Test
    void receiveNotValidScenarios() throws Exception {
        doNothing().when(service).execute(any(ScenariosRequest.class));
        mvc.perform(post(SCENARIOS).contentType(MediaType.APPLICATION_JSON).content(
                objectMapper.writeValueAsString(getNotValidScenarioRequest())
        )).andExpect(status().isBadRequest());
    }

    @Test
    void receiveValidScenarios() throws Exception {
        doNothing().when(service).execute(any(ScenariosRequest.class));
        mvc.perform(post(SCENARIOS).contentType(MediaType.APPLICATION_JSON).content(
                objectMapper.writeValueAsString(getValidScenarioRequest())
        )).andExpect(status().isOk());
    }

    @Test
    @Disabled
    //TODO I don't how it important to test method witch doesn't use
    void getScenarioResultResponseAsPage() throws Exception {
    }

    @Test
    void getScenarioResultResponseAsList() throws Exception {
        when(service.getAllScenarioResultsByUserId(any(Long.class))).thenReturn(List.of(getScenarioResultResponse(getStepResultResponse())));
        mvc.perform(get(SCENARIOS+ "/user/1")).andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void getScenarioByIdIfPresent() throws Exception {

        StepResultResponse stepResultResponse = getStepResultResponse();

        when(service.getScenarioResultById(any(Long.class))).thenReturn(Optional.of(getScenarioResultResponse(stepResultResponse)));
        mvc.perform(get(SCENARIOS+ "/1")).andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("name"))
                .andExpect(jsonPath("$.site").value("site"))
                .andExpect(jsonPath("$.executedAt").value(now.toString()))
                .andExpect(jsonPath("$.stepsResults").isArray())
                .andExpect(validResponse("$.stepsResults[0]", stepResultResponse));
    }

    private StepResultResponse getStepResultResponse() {
        return new StepResultResponse(new Step("action", "value"), true);
    }

    public static ResultMatcher validResponse(String prefix, StepResultResponse stepResultResponse) {
        return ResultMatcher.matchAll(
                jsonPath(prefix + ".step.value").value(stepResultResponse.step().getValue()),
                jsonPath(prefix + ".step.action").value(stepResultResponse.step().getAction()),
                jsonPath(prefix + ".isPassed").value(stepResultResponse.isPassed()));
    }

    @Test
    void getScenarioByIdIfNotPresent() throws Exception {

       when(service.getScenarioResultById(2l)).thenReturn(Optional.empty());
        mvc.perform(get(SCENARIOS+ "/2")).andExpect(status().isNotFound());

    }

    @Test
    void deleteResultById() throws Exception {
        doNothing().when(service).deleteById(any(Long.class),any(Long.class));
        mvc.perform(delete(SCENARIOS+ "/1?userId=1")).andExpect(status().isOk());
    }

    private ScenariosRequest getNotValidScenarioRequest() {
        List<Scenario> scenarios = List.of(new Scenario());
        return new ScenariosRequest(scenarios,1, true );
    }

    private ScenariosRequest getValidScenarioRequest() {
        List<StepRequest> stepRequests = List.of(new StepRequest("action", "value"));
        List<Scenario> scenarios = List.of(new Scenario("name", "site", stepRequests));
        return new ScenariosRequest(scenarios,1, true );
    }

    private ScenarioResultResponse getScenarioResultResponse(StepResultResponse stepResultResponse) {
        List<StepResultResponse> result = List.of(stepResultResponse);
        return  new ScenarioResultResponse(1, "name", "site", result, now);
    }

}