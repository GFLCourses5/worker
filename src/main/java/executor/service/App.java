package executor.service;

import executor.service.config.di.FactoryForDI;
import executor.service.config.di.ApplicationContext;
import executor.service.service.ParallelFlowExecutorService;

import java.util.HashMap;
import java.util.Map;

public class App {

    public static void main( String[] args ) {

    }

    private static String getPackageName() {
        Package currentPackage = App.class.getPackage();
        return currentPackage.getName();
    }
}
