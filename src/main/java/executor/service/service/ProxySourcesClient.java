package executor.service.service;

/**
 * Class for reading properties as JSON from properties file.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public interface ProxySourcesClient<T> {

    void execute(T handler);

}
