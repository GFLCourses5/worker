package executor.service.service;

import java.util.List;

public interface Provider {

    <T> List<T> provideData(String resource, Class<T> valueType);

}
