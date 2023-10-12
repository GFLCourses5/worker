package executor.service.model;

import java.util.Objects;

public class SourceListenerData {

    private Long delayProxy;
    private Long delayScenario;

    public SourceListenerData() {
    }

    public SourceListenerData(Long delayProxy, Long delayScenario) {
        this.delayProxy = delayProxy;
        this.delayScenario = delayScenario;
    }

    public Long getDelayProxy() {
        return delayProxy;
    }

    public void setDelayProxy(Long delayProxy) {
        this.delayProxy = delayProxy;
    }

    public Long getDelayScenario() {
        return delayScenario;
    }

    public void setDelayScenario(Long delayScenario) {
        this.delayScenario = delayScenario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SourceListenerData that = (SourceListenerData) o;
        return Objects.equals(delayProxy, that.delayProxy) && Objects.equals(delayScenario, that.delayScenario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(delayProxy, delayScenario);
    }
}
