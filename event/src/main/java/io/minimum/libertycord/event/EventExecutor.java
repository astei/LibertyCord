package io.minimum.libertycord.event;

/**
 * Allows plugins to customize the execution of proxy events. For instance, this could be used to hook into an
 * RxJava pipeline.
 */
public interface EventExecutor<T> {
    /**
     * Processes the specified event.
     * @param event the event to process
     */
    void execute(T event);
}
