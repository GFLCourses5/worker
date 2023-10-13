package executor.service.model;

import java.util.Objects;

public class QueueData {

    private Integer capacity;

    public QueueData() {
    }

    public QueueData(Integer capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QueueData queueData = (QueueData) o;
        return Objects.equals(capacity, queueData.capacity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(capacity);
    }
}
