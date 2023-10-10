package executor.service;

import executor.service.service.ParallelFlowExecutorService;
import executor.service.service.impl.DIFactory;

public class App {

    public static void main( String[] args ) {
        DIFactory diFactory = DIFactory.getInstance();
        ParallelFlowExecutorService service = diFactory.createObject(ParallelFlowExecutorService.class);
        service.execute();
    }

}
