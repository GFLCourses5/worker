package executor.service.service;

public interface ProxySourcesClient<T> {

    void execute(T handler);

}
