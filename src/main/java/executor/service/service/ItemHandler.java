package executor.service.service;

/**
 * The {@code ItemHandler} functional interface defines a contract
 * for handling scenarios, proxies received during a specific process or operation.
 * Implementing classes or lambda expressions should provide the logic
 * for processing received scenarios, proxies by implementing
 * the {@link #onItemReceived(T item)} method.
 * <p>
 * @see     executor.service.model.Scenario
 * @see     executor.service.model.ProxyConfigHolder
 * @author Oleksandr Tuleninov, Yurii Kotsiuba
 * @version 01
 */
public interface ItemHandler<T> {

    void onItemReceived(T item);

}
