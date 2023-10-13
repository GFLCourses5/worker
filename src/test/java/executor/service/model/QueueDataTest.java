package executor.service.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for testing the functionality of the {@code QueueData} class.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 * @see QueueData
 */
public class QueueDataTest {

    private QueueData data;
    private QueueData data1;
    private QueueData data2;
    private QueueData data3;

    @BeforeEach
    public void setUp() {
        data = new QueueData();
        data1 = new QueueData(1);
        data2 = new QueueData(1);
        data3 = new QueueData(3);
    }

    @AfterEach
    public void tearDown() {
        data = null;
        data1 = null;
        data2 = null;
        data3 = null;
    }

    @Test
    public void testEmptyConstructor() {
        assertNotNull(data);
    }

    @Test
    public void testConstructorWithParameterSetterGetter() {
        Integer capacity = 1;
        data.setCapacity(capacity);

        assertEquals(capacity, data.getCapacity());
    }

    @Test
    public void testEqualsForEqualObjects() {
        assertEquals(data1, data2);
    }

    @Test
    public void testEqualsForDifferentObjects() {
        assertNotEquals(data1, data3);
    }

    @Test
    public void testHashCodeForEqualObjects() {
        assertEquals(data1.hashCode(), data2.hashCode());
    }

    @Test
    public void testHashCodeForDifferentObjects() {
        assertNotEquals(data1.hashCode(), data3.hashCode());
    }
}
