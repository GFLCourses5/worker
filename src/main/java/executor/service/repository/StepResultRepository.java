package executor.service.repository;

import executor.service.model.Step;
import executor.service.model.StepResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StepResultRepository extends JpaRepository<StepResult, Integer> {

    List<StepResult> findAllByStep(Step step);

}
