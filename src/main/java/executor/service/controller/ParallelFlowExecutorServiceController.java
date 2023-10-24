package executor.service.controller;

import executor.service.service.ParallelFlowExecutorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static executor.service.Routes.EXECUTION;

/**
 * The {@code ParallelFlowExecutorServiceController} class represents a REST controller
 * that provides endpoints for managing the ParallelFlowExecutorService.
 * <p>
 *
 * @author Oleksandr Tuleninov
 * @version 01
 * @see ParallelFlowExecutorService
 */
@RestController
@RequestMapping(value = EXECUTION)
public class ParallelFlowExecutorServiceController {

    private final ParallelFlowExecutorService service;

    public ParallelFlowExecutorServiceController(ParallelFlowExecutorService service) {
        this.service = service;
    }

    /**
     * Shuts down the ParallelFlowExecutorService, terminating all parallel executions.
     */
    @GetMapping(value = "/close")
    public void shutdown() {
        service.shutdown();
    }
}
