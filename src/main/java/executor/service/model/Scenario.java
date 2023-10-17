package executor.service.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.Objects;

public class Scenario {

    private Integer userId;
    @NotBlank(message = "scenario name must not be null or empty")
    private String name;
    @NotBlank(message = "scenario site must not be null or empty")
    private String site;
    private @Valid List<Step> steps;

    public Scenario() {
    }

    public Scenario(String name, String site, List<Step> steps) {
        this.name = name;
        this.site = site;
        this.steps = steps;
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

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Scenario scenario = (Scenario) o;
        return Objects.equals(userId, scenario.userId) && Objects.equals(name, scenario.name)
                && Objects.equals(site, scenario.site) && Objects.equals(steps, scenario.steps);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, name, site, steps);
    }

    @Override
    public String toString() {
        return "Scenario{" +
                "name='" + name + '\'' +
                ", site='" + site + '\'' +
                ", steps=" + steps +
                '}';
    }
}
