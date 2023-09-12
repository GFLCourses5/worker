package executor.service.service;

/**
 * Item Retrieval Function.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public interface ItemHandler<T> {

    void onItemReceived(T item);

}
