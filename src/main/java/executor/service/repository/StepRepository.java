package executor.service.repository;

import executor.service.model.Step;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StepRepository extends JpaRepository<Step, Integer> {

    Optional<Step> findStepByActionAndValue(String action, String value);

}
