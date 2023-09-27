package executor.service.config.simpleDI;

public interface ObjectFactory {

    <T> T createObject(Class<T> clazz);

}
