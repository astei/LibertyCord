package io.minimum.libertycord.event;

import lombok.Data;
import net.md_5.bungee.event.EventHandler;
import org.junit.Assert;
import org.junit.Test;

public class ReflectiveEventBusAdapterTest {
    @Test
    public void registerAndPost() {
        EventBus bus = new EventBus(null);
        ReflectiveEventBusAdapter adapter = new ReflectiveEventBusAdapter(bus);
        adapter.register(new TestListener());

        TestEvent event = new TestEvent();
        bus.post(event);
        Assert.assertTrue("Event listener was not properly registered!", event.seen);
    }

    @Test
    public void deregisterAndPost() {
        EventBus bus = new EventBus(null);
        ReflectiveEventBusAdapter adapter = new ReflectiveEventBusAdapter(bus);
        TestListener listener = new TestListener();
        adapter.register(listener);

        TestEvent event = new TestEvent();
        bus.post(event);
        Assert.assertTrue("Event listener was not properly registered!", event.seen);

        adapter.unregister(listener);

        TestEvent event2 = new TestEvent();
        bus.post(event2);
        Assert.assertFalse("Event listener was not properly deregistered!", event2.seen);
    }

    private class TestListener {
        @EventHandler
        public void test(TestEvent event) {
            event.seen = true;
        }
    }

    @Data
    private class TestEvent {
        private boolean seen;
    }
}
