package executor.service.queue;

import executor.service.model.proxy.ProxyConfigHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

//TODO подумать над тем стоит ли выделить общий интерфейс
public class ProxyQueue {
    private BlockingQueue<ProxyConfigHolder> proxies;

    public ProxyQueue() {
        this.proxies = new LinkedBlockingDeque<>();
    }

    public void addProxy(ProxyConfigHolder proxy) {
        proxies.add(proxy);
    }

    public ProxyConfigHolder getProxy() throws InterruptedException {
        return proxies.peek();
    }

    public List<ProxyConfigHolder> getAllProxy() throws InterruptedException {
        List<ProxyConfigHolder> list = new ArrayList<>();
        proxies.drainTo(list);
        return list;
    }
}