package executor.service.service;

public interface ObjectFactory {

    <T> T createObject(Class<T> clazz);

}
