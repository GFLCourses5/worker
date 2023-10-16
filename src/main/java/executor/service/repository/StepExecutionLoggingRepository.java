package executor.service.repository;

import executor.service.model.response.StepResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StepExecutionLoggingRepository extends JpaRepository<StepResult, Integer> {

    List<StepResult> findStepResultsByScenarioId(Integer id);
}
