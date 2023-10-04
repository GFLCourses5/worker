package executor.service.service;

import executor.service.model.Scenario;

import java.util.List;

/**
 * An interface for providing scenarios from various sources.
 * <p>
 *
 * @author Yurii Kotsiuba, Oleksandr Tuleninov, Kostia Hromovii.
 * @version 01
 */
public interface ScenarioProvider {

    List<Scenario> readScenarios(String fileName);

}
