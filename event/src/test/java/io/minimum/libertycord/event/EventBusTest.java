package io.minimum.libertycord.event;

import lombok.Data;
import org.junit.Assert;
import org.junit.Test;

public class EventBusTest {
    @Test
    public void deliversEvents() {
        EventBus bus = new EventBus(null);
        bus.registerHandler((byte) 0, TestEvent.class, event -> {
            event.seen = true;
        });
        TestEvent e = new TestEvent();
        bus.post(e);
        Assert.assertTrue("Event did not successfully post!", e.seen);
    }

    @Test
    public void deregisterListener() {
        EventBus bus = new EventBus(null);
        RegisteredEventExecutor<TestEvent> r = bus.registerHandler((byte) 0, TestEvent.class, event -> {
            event.seen = true;
        });
        bus.deregisterHandler(r);
        TestEvent e = new TestEvent();
        bus.post(e);
        Assert.assertFalse("Listener did not successfully deregister!", e.seen);
    }

    @Data
    private class TestEvent {
        private boolean seen;
    }
}
