package executor.service.service;

/**
 * The {@code ItemHandler} functional interface defines a contract
 * for handling item received during a specific process or operation.
 * Implementing classes or lambda expressions should provide the logic
 * for processing received item by implementing
 * the {@link #onItemReceived(T item)} method.
 * <p>
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public interface ItemHandler<T> {

    void onItemReceived(T item);

}
