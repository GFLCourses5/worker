package executor.service.service;

public interface ScenarioSourceListener<T> {

    void execute(T handler);

}
