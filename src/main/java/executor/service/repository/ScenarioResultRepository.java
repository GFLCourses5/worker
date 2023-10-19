package executor.service.repository;

import executor.service.model.ScenarioResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScenarioResultRepository extends JpaRepository<ScenarioResult, Integer> {

    Page<ScenarioResult> findAllByUserId(Integer userId, Pageable pageable);

}
