package executor.service.model.response;

import executor.service.model.Step;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "step_results")
public class StepResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "step_id")
    private Step step;

    @Column(name = "is_passed", nullable = false)
    private Boolean isPassed;

    public StepResult() {
    }

    public StepResult(Step step, Boolean isPassed) {
        this.step = step;
        this.isPassed = isPassed;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Step getStep() {
        return step;
    }

    public void setStep(Step step) {
        this.step = step;
    }

    public Boolean getIsPassed() {
        return isPassed;
    }

    public void setIsPassed(Boolean isPassed) {
        this.isPassed = isPassed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StepResult that = (StepResult) o;
        return Objects.equals(id, that.id) && Objects.equals(step, that.step) && Objects.equals(isPassed, that.isPassed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, step, isPassed);
    }
}
