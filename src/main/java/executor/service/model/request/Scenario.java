package executor.service.model.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;


import java.util.List;
import java.util.Objects;

public class Scenario {

    @Min(1)
    private Integer userId;
    @NotBlank(message = "scenario name must not be null or empty")
    private String name;
    @NotBlank(message = "scenario site must not be null or empty")
    private String site;

    private @Valid List<StepRequest> stepRequests;

    public Scenario() {
    }

    public Scenario(String name, String site, List<StepRequest> stepRequests) {
        this.name = name;
        this.site = site;
        this.stepRequests = stepRequests;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public List<StepRequest> getSteps() {
        return stepRequests;
    }

    public void setSteps(List<StepRequest> stepRequests) {
        this.stepRequests = stepRequests;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Scenario scenario = (Scenario) o;
        return Objects.equals(userId, scenario.userId) && Objects.equals(name, scenario.name)
                && Objects.equals(site, scenario.site) && Objects.equals(stepRequests, scenario.stepRequests);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, name, site, stepRequests);
    }

    @Override
    public String toString() {
        return "Scenario{" +
                "name='" + name + '\'' +
                ", site='" + site + '\'' +
                ", steps=" + stepRequests +
                '}';
    }
}
