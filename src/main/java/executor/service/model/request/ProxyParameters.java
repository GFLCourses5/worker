package executor.service.model.request;

public class ProxyParameters {
    private String mode;
    private boolean valid;

    public ProxyParameters(String mode, boolean valid) {
        this.mode = mode;
        this.valid = valid;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}
