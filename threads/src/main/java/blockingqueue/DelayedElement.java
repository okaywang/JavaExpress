package blockingqueue;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * foo...Created by wgj on 2017/2/26.
 */
public class DelayedElement implements Delayed {
    private long expired;
    private long delay;
    private String name;

    DelayedElement(String elementName, long delay) {
        this.name = elementName;
        this.delay = delay;
        this.expired = (delay + System.currentTimeMillis());
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return expired - System.currentTimeMillis();
    }

    @Override
    public int compareTo(Delayed o) {
        DelayedElement cached = (DelayedElement) o;
        return cached.expired > expired ? 1 : -1;
    }

    @Override
    public String toString() {
        return String.format("DelayedElement [name=%s,delay=%s]", name, delay);
    }
}
