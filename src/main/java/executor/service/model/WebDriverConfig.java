package executor.service.model;

import java.util.Objects;

public class WebDriverConfig {
   private String webDriverExecutable;
   private String userAgent;
   private Long pageLoadTimeout;
   private Long implicitlyWait;
   private String chromeExecutable;
   private String chromeVersion;

    public WebDriverConfig() {
    }

    public WebDriverConfig(String webDriverExecutable, String userAgent,
                           Long pageLoadTimeout, Long implicitlyWait,
                           String chromeExecutable, String chromeVersion) {
        this.webDriverExecutable = webDriverExecutable;
        this.userAgent = userAgent;
        this.pageLoadTimeout = pageLoadTimeout;
        this.implicitlyWait = implicitlyWait;
        this.chromeExecutable = chromeExecutable;
        this.chromeVersion = chromeVersion;
    }

    public String getWebDriverExecutable() {
        return webDriverExecutable;
    }

    public void setWebDriverExecutable(String webDriverExecutable) {
        this.webDriverExecutable = webDriverExecutable;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public Long getPageLoadTimeout() {
        return pageLoadTimeout;
    }

    public void setPageLoadTimeout(Long pageLoadTimeout) {
        this.pageLoadTimeout = pageLoadTimeout;
    }

    public Long getImplicitlyWait() {
        return implicitlyWait;
    }

    public void setImplicitlyWait(Long implicitlyWait) {
        this.implicitlyWait = implicitlyWait;
    }

    public String getChromeExecutable() {
        return chromeExecutable;
    }

    public void setChromeExecutable(String chromeExecutable) {
        this.chromeExecutable = chromeExecutable;
    }

    public String getChromeVersion() {
        return chromeVersion;
    }

    public void setChromeVersion(String chromeVersion) {
        this.chromeVersion = chromeVersion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WebDriverConfig that = (WebDriverConfig) o;
        return Objects.equals(webDriverExecutable, that.webDriverExecutable)
                && Objects.equals(userAgent, that.userAgent)
                && Objects.equals(pageLoadTimeout, that.pageLoadTimeout)
                && Objects.equals(implicitlyWait, that.implicitlyWait)
                && Objects.equals(chromeExecutable, that.chromeExecutable)
                && Objects.equals(chromeVersion, that.chromeVersion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(webDriverExecutable, userAgent, pageLoadTimeout,
                implicitlyWait, chromeExecutable, chromeVersion);
    }
}
