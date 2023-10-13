package executor.service.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for testing the functionality of the {@code SourceListenerData} class.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 * @see SourceListenerData
 */
public class SourceListenerDataTest {

    private SourceListenerData data;
    private SourceListenerData data1;
    private SourceListenerData data2;
    private SourceListenerData data3;

    @BeforeEach
    public void setUp() {
        data = new SourceListenerData();
        data1 = new SourceListenerData(10L, 20L);
        data2 = new SourceListenerData(10L, 20L);
        data3 = new SourceListenerData(5L, 20L);
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
        Long delayProxy = 1L;
        Long delayScenario = 1L;
        data.setDelayProxy(delayProxy);
        data.setDelayScenario(delayScenario);

        assertEquals(delayProxy, data.getDelayProxy());
        assertEquals(delayScenario, data.getDelayScenario());
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
