package net.md_5.bungee.event;

import com.google.common.base.Preconditions;
import io.minimum.libertycord.event.EventExecutor;
import lombok.SneakyThrows;

import java.lang.reflect.Method;

class ReflectiveEventExecutor<T> implements EventExecutor<T> {
    private final Object listener;
    private final Method method;

    public ReflectiveEventExecutor(Object listener, Method method) {
        this.listener = Preconditions.checkNotNull(listener, "listener");
        this.method = Preconditions.checkNotNull(method, "method");
        Preconditions.checkArgument(method.getParameterTypes().length == 1, "Method provided does not have exactly one parameter");
    }

    @Override
    @SneakyThrows
    public void execute(T event) {
        Class<?> methodParameterClazz = method.getParameterTypes()[0];
        if (methodParameterClazz.isAssignableFrom(event.getClass())) {
            method.invoke(listener, method);
        }
    }
}
