package executor.service.model.request;

public enum Mode {
    DIRECT("direct"), BACKBONE("backbone");

    private final String name;

    Mode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
