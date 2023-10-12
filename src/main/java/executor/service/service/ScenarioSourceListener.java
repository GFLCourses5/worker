package executor.service.service;

/**
 * The {@code ScenarioSourceListener} interface defines a contract for listening to scenarios and
 * executing actions on received scenarios.
 * <p>
 * Implementing classes or objects that implement this interface are responsible for executing
 * the specified {@link #execute(ItemHandler)} method when scenarios are available for processing.
 * The actual behavior of handling scenarios may vary based on the implementation.
 * <p>
 * @author Yurii Kotsiuba
 * @see ItemHandler
 */
public interface ScenarioSourceListener {

    /**
     * Executes an action on received scenarios using the provided {@link ItemHandler}.
     * The specific behavior of handling item is determined by the implementing class.
     *
     * @param handler The handler responsible for processing received item.
     */
    void execute(ItemHandler handler);

}
