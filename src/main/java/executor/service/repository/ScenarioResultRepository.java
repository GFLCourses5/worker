package executor.service.repository;

import executor.service.model.ScenarioResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScenarioResultRepository extends JpaRepository<ScenarioResult, Integer> {

    Page<ScenarioResult> findAllByUserId(Integer userId, Pageable pageable);

    List<ScenarioResult> findAllByUserId(Integer userId);

}
