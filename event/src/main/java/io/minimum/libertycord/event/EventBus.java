package io.minimum.libertycord.event;

import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EventBus {
    private final Map<Class<?>, EventExecutor<?>[]> bakedExecutors = new ConcurrentHashMap<>();
    private final Map<Class<?>, List<RegisteredEventExecutor>> registeredExecutors = new HashMap<>();
    private final Lock registrationLock = new ReentrantLock();
    private final Logger logger;

    public EventBus(Logger logger) {
        this.logger = ( logger == null ) ? Logger.getLogger( Logger.GLOBAL_LOGGER_NAME ) : logger;
    }

    public void post(Object event) {
        EventExecutor[] executors = bakedExecutors.get(event.getClass());
        if (executors != null) {
            for (EventExecutor executor : executors) {
                try {
                    // Despite our own checks, we might have to live with this...
                    executor.execute(event);
                } catch (Exception e) {
                    logger.log( Level.WARNING, MessageFormat.format( "Error dispatching event {0} to executor {1}", event, executor ), e );
                }
            }
        }
    }

    private void bakeHandlers(Class<?> eventClass) {
        List<RegisteredEventExecutor> executors = registeredExecutors.get(eventClass);
        if (executors == null || executors.isEmpty()) {
            bakedExecutors.remove(eventClass);
            return;
        }
        executors.sort(null);
        EventExecutor[] flattened = new EventExecutor[executors.size()];
        for (int i = 0; i < executors.size(); i++) {
            flattened[i] = executors.get(i).getExecutor();
        }
        bakedExecutors.put(eventClass, flattened);
    }

    public <T> RegisteredEventExecutor<T> registerHandler(byte priority, Class<T> eventClass, EventExecutor<T> executor) {
        registrationLock.lock();
        try {
            RegisteredEventExecutor<T> regExec = new RegisteredEventExecutor<>(priority, eventClass, executor);
            registeredExecutors.computeIfAbsent(eventClass, (k) -> new ArrayList<>()).add(regExec);
            bakeHandlers(eventClass);
            return regExec;
        } finally {
            registrationLock.unlock();
        }
    }

    public <T> void deregisterHandler(RegisteredEventExecutor<T> executor) {
        registrationLock.lock();
        try {
            List<RegisteredEventExecutor> registered = registeredExecutors.get(executor.getHandles());
            if (registered != null && registered.remove(executor)) {
                bakeHandlers(executor.getHandles());
            }
        } finally {
            registrationLock.unlock();
        }
    }
}
