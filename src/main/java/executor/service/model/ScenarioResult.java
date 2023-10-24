package executor.service.model;

import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.Set;

/**
 * A class representing a response for scenarios.
 *
 * @author Oleksii Bondarenko
 * @version 01
 */
@Entity
@Table(name = "scenario_results")
public class ScenarioResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String site;

    @ManyToMany
    @JoinTable(name = "scenario_results_step_results",
            joinColumns = @JoinColumn(name = "scenario_result_id"),
            inverseJoinColumns = @JoinColumn(name = "step_result_id"))
    private Set<StepResult> stepsResults;

    @Column(name = "executed_at", nullable = false)
    private OffsetDateTime executedAt;

    public ScenarioResult() {
    }

    public ScenarioResult(Integer userId, String name, String site,
                          Set<StepResult> stepsResults, OffsetDateTime executedAt) {
        this.userId = userId;
        this.name = name;
        this.site = site;
        this.stepsResults = stepsResults;
        this.executedAt = executedAt;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public Set<StepResult> getStepsResults() {
        return stepsResults;
    }

    public void setStepsResults(Set<StepResult> stepsResults) {
        this.stepsResults = stepsResults;
    }

    public OffsetDateTime getExecutedAt() {
        return executedAt;
    }

    public void setExecutedAt(OffsetDateTime executedAt) {
        this.executedAt = executedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScenarioResult that = (ScenarioResult) o;
        return Objects.equals(id, that.id)
                && Objects.equals(name, that.name)
                && Objects.equals(site, that.site)
                && Objects.equals(stepsResults, that.stepsResults)
                && Objects.equals(executedAt, that.executedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, site, stepsResults, executedAt);
    }
}
