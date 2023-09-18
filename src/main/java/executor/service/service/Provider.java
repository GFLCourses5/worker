package executor.service.service;

import java.util.List;

/**
 * Class for reading properties as JSON from properties file.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public interface Provider {

    <T> List<T> provideData(String resource, Class<T> valueType);

}
