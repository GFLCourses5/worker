package executor.service.model;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertSame;

public class ScenarioTest {
    @Test
    public void testConstructor() {
        Scenario actualScenario = new Scenario();
        actualScenario.setName("Name");
        actualScenario.setSite("Site");
        ArrayList<Step> steps = new ArrayList<>();
        actualScenario.setSteps(steps);
        assertEquals("Name", actualScenario.getName());
        assertEquals("Site", actualScenario.getSite());
        assertSame(steps, actualScenario.getSteps());
    }

    @Test
    public void testConstructor2() {
        ArrayList<Step> steps = new ArrayList<>();
        Scenario actualScenario = new Scenario("Name", "Site", steps);
        actualScenario.setName("Name");
        actualScenario.setSite("Site");
        ArrayList<Step> steps2 = new ArrayList<>();
        actualScenario.setSteps(steps2);
        assertEquals("Name", actualScenario.getName());
        assertEquals("Site", actualScenario.getSite());
        List<Step> steps3 = actualScenario.getSteps();
        assertSame(steps2, steps3);
        assertEquals(steps, steps3);
    }
    @Test
    public void testEquals() {
        assertNotEquals(new Scenario(), null);
        assertNotEquals(new Scenario(), "Different type to Scenario");
    }
    @Test
    public void testEquals2() {
        Scenario scenario = new Scenario();
        assertEquals(scenario, scenario);
        int expectedHashCodeResult = scenario.hashCode();
        assertEquals(expectedHashCodeResult, scenario.hashCode());
    }
    @Test
    public void testEquals3() {
        Scenario scenario = new Scenario();
        Scenario scenario2 = new Scenario();
        assertEquals(scenario, scenario2);
        int expectedHashCodeResult = scenario.hashCode();
        assertEquals(expectedHashCodeResult, scenario2.hashCode());
    }
    @Test
    public void testEquals4() {
        Scenario scenario = new Scenario("Name", "Site", new ArrayList<>());
        assertNotEquals(scenario, new Scenario());
    }
    @Test
    public void testEquals5() {
        Scenario scenario = new Scenario();
        scenario.setSite("Site");
        assertNotEquals(scenario, new Scenario());
    }
    @Test
    public void testEquals6() {
        Scenario scenario = new Scenario();
        scenario.setSteps(new ArrayList<>());
        assertNotEquals(scenario, new Scenario());
    }

}
