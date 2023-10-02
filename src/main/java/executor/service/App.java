package executor.service;

import executor.service.service.simpleDI.DIFactory;
import executor.service.service.ParallelFlowExecutorService;

public class App {

    public static void main( String[] args ) {
        DIFactory diFactory = new DIFactory();
        ParallelFlowExecutorService service = diFactory.createObject(ParallelFlowExecutorService.class);
        service.execute();
    }

}
