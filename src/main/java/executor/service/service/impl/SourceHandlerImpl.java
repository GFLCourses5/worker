package executor.service.service.impl;

import executor.service.model.Scenario;
import executor.service.model.request.ScenarioRequest;
import executor.service.service.SourceHandler;
import executor.service.service.impl.proxy.ProxySourceQueueHandler;
import executor.service.service.impl.scenario.ScenarioSourceQueueHandler;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This class, {@code SourceHandlerImpl}, implements the {@code SourceHandler} interface and is responsible for handling
 * incoming scenarios and proxy requests. It provides a service for processing scenario requests and managing queues
 * for scenarios and proxy sources.
 * <p>
 *
 * @author Oleksandr Tuleninov
 * @version 01
 * @see SourceHandler
 */
@Service
public class SourceHandlerImpl implements SourceHandler {

    private final ScenarioSourceQueueHandler scenarioHandler;
    private final ProxySourceQueueHandler proxyHandler;

    public SourceHandlerImpl(ScenarioSourceQueueHandler scenarioHandler,
                             ProxySourceQueueHandler proxyHandler) {
        this.scenarioHandler = scenarioHandler;
        this.proxyHandler = proxyHandler;
    }

    /**
     * Executes the scenario request by adding the provided scenarios to the scenario queue and adding proxy sources
     * based on the number of scenarios.
     *
     * @param request The scenario request to be processed.
     */
    @Override
    public void execute(ScenarioRequest request) {
        List<Scenario> scenarios = request.scenarios();
        scenarioHandler.addAllScenarios(scenarios);
        if (request.proxyRequired()) {
            proxyHandler.addAllProxies(scenarios.size());
        } else {
            proxyHandler.setNoProxy();
        }
    }
}
