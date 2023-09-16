package executor.service.config.DIFactory;

public interface ObjectFactory {
    <T> T create(Class<T> clazz) ;
}
