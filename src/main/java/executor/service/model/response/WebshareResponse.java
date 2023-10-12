package executor.service.model.response;

import java.util.List;

/**
 * Class represents response obtained from https://proxy.webshare.io/api/.
 */
public class WebshareResponse {
    private Integer count;
    private List<ProxyResponse> results;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<ProxyResponse> getResults() {
        return results;
    }

    public void setResults(List<ProxyResponse> results) {
        this.results = results;
    }
}
