package executor.service.model.response;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "step_results")
public class StepResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String action;

    @Column(nullable = false)
    private String value;

    @Column(name = "is_passed", nullable = false)
    private Boolean isPassed;

    @Column(name = "scenario_id", nullable = false)
    private Integer scenarioId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Boolean getIsPassed() {
        return isPassed;
    }

    public void setIsPassed(Boolean isPassed) {
        this.isPassed = isPassed;
    }

    public Integer getScenarioId() {
        return scenarioId;
    }

    public void setScenarioId(Integer scenarioId) {
        this.scenarioId = scenarioId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StepResult that = (StepResult) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(action, that.action) &&
                Objects.equals(value, that.value) &&
                Objects.equals(isPassed, that.isPassed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, action, value, isPassed);
    }
}
