package executor.service.service.impl;

import executor.service.model.request.ScenariosRequest;
import executor.service.model.response.ScenarioResultResponse;
import executor.service.service.ScenarioResultService;
import executor.service.service.SourceHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Test class for testing the functionality of the {@code ScenarioService} class.
 * This class contains unit tests to verify that {@code ScenarioService} is working correctly.
 * <p>
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public class ScenarioServiceTest {

    private ScenarioService scenarioService;
    private SourceHandler sourceHandler;
    private ScenarioResultService scenarioResultService;

    @BeforeEach
    public void setUp() {
        sourceHandler = Mockito.mock(SourceHandler.class);
        scenarioResultService = Mockito.mock(ScenarioResultService.class);
        scenarioService = new ScenarioService(sourceHandler, scenarioResultService);
    }

    @Test
    public void testExecute() {
        var request = new ScenariosRequest(null, null, null);
        scenarioService.execute(request);

        verify(sourceHandler, times(1)).execute(request);
    }

    @Test
    public void testGetAllScenarioResultsByUserId() {
        Long userId = 1L;
        Pageable pageable = Pageable.unpaged();
        Page<ScenarioResultResponse> expectedResult = Page.empty();

        when(scenarioResultService.getAllScenarioResultsByUserId(userId, pageable)).thenReturn(expectedResult);

        Page<ScenarioResultResponse> result = scenarioService.getAllScenarioResultsByUserId(userId, pageable);

        assertEquals(expectedResult, result);
    }

    @Test
    public void testDeleteById() {
        Long userId = 2L;
        Long resultId = 2L;
        scenarioService.deleteById(userId, resultId);

        verify(scenarioResultService, times(1)).deleteById(userId, resultId);
    }
}
