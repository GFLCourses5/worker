package executor.service.service;

/**
 * The {@code ProxySourcesClient} interface defines a contract for clients responsible for executing
 * the retrieval and handling of proxy configurations using a {@link ItemHandler}.
 * <p>
 * Implementing classes or objects that implement this interface should provide the logic for retrieving
 * and processing proxy configurations using the specified {@link ItemHandler}.
 * <p>
 * The actual behavior of retrieving and handling proxy configurations may vary based on the implementation.
 *
 * @author Yurii Kotsiuba
 * @see ItemHandler
 */
public interface ProxySourceClient extends Listener {

    /**
     * Executes the retrieval and handling of proxy configurations using the provided {@link ItemHandler}.
     *
     * @param handler The handler responsible for processing received Item configurations.
     */
    void execute(ItemHandler handler);

}