package executor.service.model.response;

public class ProxyResponse {
    private String username;
    private String password;
    private String host;
    private int port;
    private boolean valid;

    public ProxyResponse() {
    }

    public ProxyResponse(String username, String password,
                         String host, int port, boolean valid) {
        this.username = username;
        this.password = password;
        this.host = host;
        this.port = port;
        this.valid = valid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}
