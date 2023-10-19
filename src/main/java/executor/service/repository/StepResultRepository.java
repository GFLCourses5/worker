package executor.service.repository;

import executor.service.model.StepResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StepResultRepository extends JpaRepository<StepResult, Integer> {
}
