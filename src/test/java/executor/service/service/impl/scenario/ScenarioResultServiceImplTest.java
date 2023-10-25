package executor.service.service.impl.scenario;

import executor.service.model.ScenarioResult;
import executor.service.model.Step;
import executor.service.model.StepResult;
import executor.service.model.request.Scenario;
import executor.service.model.request.StepRequest;
import executor.service.model.response.ScenarioResultResponse;
import executor.service.repository.ScenarioResultRepository;
import executor.service.repository.StepRepository;
import executor.service.repository.StepResultRepository;
import executor.service.service.ScenarioResultService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Test class for testing the functionality of the {@code ScenarioResultServiceImpl} class is an
 * implementation of the {@link ScenarioResultService}.
 * This class contains unit tests to verify that
 * {@code ScenarioResultServiceImpl} is working correctly.
 *
 * @author Oleksandr Antonenko
 * @version 01
 * @see ScenarioResultRepository
 * @see StepResultRepository
 * @see ScenarioResultServiceImpl
 */
class ScenarioResultServiceImplTest {

    private static final Long USER_ID = 1L;
    private static final Long SCENARIO_ID = 1L;
    private ScenarioResultRepository scenarioResultRepository;
    private StepResultRepository stepResultRepository;
    private ScenarioResultServiceImpl scenarioResultService;
    private StepRepository stepRepository;

    @BeforeEach
    public void setUp() {
        this.scenarioResultRepository = mock(ScenarioResultRepository.class);
        this.stepResultRepository = mock(StepResultRepository.class);
        this.stepRepository = mock(StepRepository.class);
        this.scenarioResultService = new ScenarioResultServiceImpl(scenarioResultRepository, stepResultRepository, stepRepository);
    }

    @Test
    public void testCreateScenarioResult() {
        Scenario scenario = new Scenario("Test Scenario", "Test Site", Collections.emptyList());
        Map<StepRequest, Boolean> stepResults = new HashMap<>();
        when(scenarioResultRepository.save(any())).thenReturn(any(ScenarioResult.class));
        scenarioResultService.create(scenario, stepResults);

        verify(scenarioResultRepository, times(1)).save(any());
    }

    @Test
    public void testGetAllScenarioResultsByUserId() {
        Pageable pageable = Pageable.unpaged();
        when(scenarioResultRepository.findAllByUserId(USER_ID, pageable)).thenReturn(Page.empty());
        Page<ScenarioResultResponse> result = scenarioResultService.getAllScenarioResultsByUserId(USER_ID, pageable);

        assertEquals(0, result.getTotalElements());
    }

    @Test
    public void testGetAllScenarioResultsByUserIdList() {
        when(scenarioResultRepository.findAllByUserId(USER_ID)).thenReturn(Collections.emptyList());
        List<ScenarioResultResponse> result = scenarioResultService.getAllScenarioResultsByUserId(USER_ID);

        assertEquals(0, result.size());
    }

    @Test
    public void testDeleteScenarioResult() {
        StepResult stepResult = new StepResult(new Step(), true);
        Set<StepResult> stepResults = new LinkedHashSet<>();
        stepResults.add(stepResult);
        ScenarioResult scenarioResult = new ScenarioResult();
        scenarioResult.setStepsResults(stepResults);

        when(scenarioResultRepository.existsByIdAndUserId(SCENARIO_ID, USER_ID)).thenReturn(true);
        when(scenarioResultRepository.findScenarioResultByIdAndUserId(SCENARIO_ID, USER_ID))
                .thenReturn(Optional.of(scenarioResult));

        doNothing().when(stepResultRepository).deleteAll(any(Set.class));
        when(scenarioResultRepository.findById(SCENARIO_ID)).thenReturn(Optional.of(scenarioResult));

        List<StepResult> stepResultsList = stepResults.stream().toList();
        when(stepResultRepository.findAllByStep(any(Step.class))).thenReturn(stepResultsList);
        doNothing().when(stepRepository).deleteAll(any(List.class));

        doNothing().when(scenarioResultRepository).delete(any(ScenarioResult.class));

        scenarioResultService.deleteById(SCENARIO_ID, USER_ID);

        verify(stepRepository, times(1)).deleteAll(any(List.class));
        verify(stepResultRepository, times(1)).deleteAll(any(Set.class));
        verify(scenarioResultRepository, times(1))
                .existsByIdAndUserId(any(Long.class), any(Long.class));
        verify(scenarioResultRepository, times(1))
                .findScenarioResultByIdAndUserId(any(Long.class), any(Long.class));
        verify(scenarioResultRepository, times(1)).findById(any(Long.class));
        verify(scenarioResultRepository, times(1)).delete(any(ScenarioResult.class));
    }
}
