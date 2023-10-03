package executor.service.service.simpleDI;

public interface ObjectFactory {

    <T> T createObject(Class<T> clazz);

}
