package executor.service.exceptions;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StepExecutionExceptionTest {

    private static final String TEST_MESSAGE = "Test message";
    private static final Throwable TEST_CAUSE = new IllegalArgumentException("Cause exception");
    private StepExecutionException stepExecutionException;

    @BeforeEach
    void setUp() {
        stepExecutionException = null;
    }

    @AfterEach
    void tearDown() {
        stepExecutionException = null;
    }

    @Test
    public void testDefaultConstructor() {
        stepExecutionException = new StepExecutionException();
        assertNull(stepExecutionException.getMessage());
        assertNull(stepExecutionException.getCause());
    }

    @Test
    public void testMessageConstructor() {
        stepExecutionException = new StepExecutionException(TEST_MESSAGE);
        assertEquals(TEST_MESSAGE, stepExecutionException.getMessage());
        assertNull(stepExecutionException.getCause());
    }

    @Test
    public void testMessageAndCauseConstructor() {
        stepExecutionException = new StepExecutionException(TEST_MESSAGE, TEST_CAUSE);
        assertEquals(TEST_MESSAGE, stepExecutionException.getMessage());
        assertEquals(TEST_CAUSE, stepExecutionException.getCause());
    }

    @Test
    public void testCauseConstructor() {;
        stepExecutionException = new StepExecutionException(TEST_CAUSE);
        assertEquals(TEST_CAUSE, stepExecutionException.getCause());
    }
}