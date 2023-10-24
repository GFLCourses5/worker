package executor.service.repository;

import executor.service.model.ScenarioResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScenarioResultRepository extends JpaRepository<ScenarioResult, Long> {

    Page<ScenarioResult> findAllByUserId(Long userId, Pageable pageable);

    List<ScenarioResult> findAllByUserId(Long userId);

    Optional<ScenarioResult> findScenarioResultById(Long id);

    Optional<ScenarioResult> findScenarioResultByIdAndUserId(Long scenarioId, Long userId);

    boolean existsByIdAndUserId(Long scenarioId, Long userId);

}
