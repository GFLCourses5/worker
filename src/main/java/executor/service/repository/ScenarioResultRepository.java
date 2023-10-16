package executor.service.repository;

import executor.service.model.response.ScenarioResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScenarioResultRepository extends JpaRepository<ScenarioResult, Integer> {

    List<ScenarioResult> findAllByUserId(Integer userId);
}
