package executor.service;

/**
 * The {@code Routes} class provides constants for defining API routes and building specific routes.
 * This class is non-instantiable and contains only static members.
 * <p>
 *
 * @author Oleksandr Tuleninov
 * @version 01
 * @see Routes#API_ROOT
 * @see Routes#SCENARIOS
 */
public final class Routes {

    private Routes() {
        throw new AssertionError("non-instantiable class");
    }

    public static final String API_ROOT = "/api/v1";
    public static final String SCENARIOS = API_ROOT + "/scenarios";
}
