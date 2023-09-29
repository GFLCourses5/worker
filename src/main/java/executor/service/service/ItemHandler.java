package executor.service.service;

public interface ItemHandler<T> {

    void onItemReceived(T proxy);

}
