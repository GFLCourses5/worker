package executor.service.model.response;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

/**
 * A class representing a response for scenarios.
 *
 * @author Oleksii Bondarenko
 * @version 01
 */
public class ScenarioResult {

    private Integer id;
    private String name;
    private String site;
    private List<StepResult> stepsResults;
    private OffsetDateTime executedAt;

    public ScenarioResult() {
    }

    public ScenarioResult(Integer id, String name, String site,
                          List<StepResult> stepsResults, OffsetDateTime executedAt) {
        this.id = id;
        this.name = name;
        this.site = site;
        this.stepsResults = stepsResults;
        this.executedAt = executedAt;
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

    public List<StepResult> getStepsResults() {
        return stepsResults;
    }

    public void setStepsResults(List<StepResult> stepsResults) {
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
