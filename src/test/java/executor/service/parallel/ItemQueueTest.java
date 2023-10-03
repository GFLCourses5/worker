package executor.service.parallel;

import executor.service.model.Scenario;
import executor.service.service.parallel.ItemQueue;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for testing the functionality of the {@code ItemQueue} class.
 * This class contains unit tests to verify that {@code ItemQueue} is working correctly.
 * <p>
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public class ItemQueueTest {

    private ItemQueue queue;

    @BeforeEach
    void setUp() {
        queue = new ItemQueue();
    }

    @AfterEach
    void tearDown() {
        this.queue = null;
    }

    @Test
    void testPutItemAndGetItem() {
        var scenario = new Scenario();
        queue.putItem(scenario);

        var item = queue.getItem();

        assertEquals(scenario, item);
    }
}
