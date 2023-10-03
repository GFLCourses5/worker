package executor.service.service;

/**
 * The {@code ParallelFlowExecutorService} interface defines methods for managing parallel execution
 * of ScenarioSourceListener, ProxySourcesClient, and ExecutionService.
 * <p>
 *
 * @author Oleksandr Tuleninov
 * @version 01
 * @see executor.service.service.ExecutionService
 * @see executor.service.service.ScenarioSourceListener
 * @see executor.service.service.ProxySourcesClient
 */
public interface ParallelFlowExecutorService {

    /**
     * Starts the ScenarioSourceListener, ProxySourcesClient, and ExecutionService
     * in parallel multithreading mode.
     */
    void execute();

    /**
     * Initiates an orderly shutdown in which previously submitted tasks are executed,
     * but no new tasks will be accepted.
     */
    void shutdown();

}
