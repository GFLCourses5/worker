package executor.service.model;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ScenarioTest {

    private Scenario scenario;


    //Initialize a new script before each test
    @Before
    public void setUp() {
        List<Step> steps = new ArrayList<>();
        steps.add(new Step("Action 1", "Value 1"));
        steps.add(new Step("Action 2", "Value 2"));

        scenario = new Scenario("Test Scenario", "example.com", steps);
    }

    @Test
    public void testGetName() {
        assertEquals("Test Scenario", scenario.getName());
    }

    @Test
    public void testGetSite() {
        assertEquals("example.com", scenario.getSite());
    }
    @Test
    public void testGetSteps() {
        List<Step> expectedSteps = new ArrayList<>();
        expectedSteps.add(new Step("Action 1", "Value 1"));
        expectedSteps.add(new Step("Action 2", "Value 2"));

        assertEquals(expectedSteps, scenario.getSteps());
    }

    @Test
    public void testSetName() {
        scenario.setName("New Name");
        assertEquals("New Name", scenario.getName());
    }

    @Test
    public void testSetSite() {
        scenario.setSite("example1.com");
        assertEquals("example1.com", scenario.getSite());
    }

    @Test
    public void testSetSteps() {
        List<Step> newSteps = new ArrayList<>();
        newSteps.add(new Step("New Action 1", "New Value 1"));
        newSteps.add(new Step("New Action 2", "New Value 2"));

        scenario.setSteps(newSteps);

        assertEquals(newSteps, scenario.getSteps());
    }

    @Test
    public void testEquals() {
        Scenario sameScenario = new Scenario("Test Scenario", "example.com", scenario.getSteps());
        Scenario differentScenario = new Scenario("Different Scenario", "example.com", scenario.getSteps());

        assertEquals(scenario, sameScenario);
        assertNotEquals(scenario, differentScenario);
    }

    @Test
    public void testHashCode() {
        Scenario sameScenario = new Scenario("Test Scenario", "example.com", scenario.getSteps());
        assertEquals(scenario.hashCode(), sameScenario.hashCode());
    }

}
