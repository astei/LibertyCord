package io.minimum.libertycord.event;

import lombok.Value;

@Value
public class RegisteredEventExecutor<T> implements Comparable<RegisteredEventExecutor<T>> {
    private final byte priority;
    private final Class<T> handles;
    private final EventExecutor<T> executor;

    @Override
    public int compareTo(RegisteredEventExecutor o) {
        return Byte.compare(priority, o.priority);
    }
}
