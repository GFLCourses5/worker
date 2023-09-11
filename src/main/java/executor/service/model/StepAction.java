package executor.service.model;

public enum StepAction {
    CLICK_CSS("clickCss"),
    SLEEP("sleep"),
    CLICK_XPATH("clickXpath");

    private final String name;

    private StepAction(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
