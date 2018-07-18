package io.minimum.libertycord.event;

import com.google.common.base.Preconditions;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.event.EventHandler;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@RequiredArgsConstructor
public class ReflectiveEventBusAdapter {
    private final EventBus bus;
    private final Multimap<Object, RegisteredEventExecutor<?>> registeredListeners = ArrayListMultimap.create();
    private final Lock registrationLock = new ReentrantLock();

    public void register(Object listener) {
        Preconditions.checkNotNull(listener, "listener");
        List<Method> toRegister = new ArrayList<>();
        for (Method method : listener.getClass().getDeclaredMethods()) {
            if (!method.isAnnotationPresent(EventHandler.class)) continue;
            Preconditions.checkArgument(method.getParameterTypes().length == 1, "Method provided does not have exactly one parameter");
            toRegister.add(method);
        }

        for (Method method : toRegister) {
            EventHandler handler = method.getAnnotation(EventHandler.class);
            ReflectiveEventExecutor executor = new ReflectiveEventExecutor<>(listener, method);
            RegisteredEventExecutor registered = bus.registerHandler(handler.priority(), method.getParameterTypes()[0], executor);
            registrationLock.lock();
            try {
                registeredListeners.put(listener, registered);
            } finally {
                registrationLock.unlock();
            }
        }
    }

    public void unregister(Object listener) {
        Collection<RegisteredEventExecutor<?>> registered;
        registrationLock.lock();
        try {
            registered = registeredListeners.removeAll(listener);
        } finally {
            registrationLock.unlock();
        }
        registered.forEach(bus::deregisterHandler);
    }
}
