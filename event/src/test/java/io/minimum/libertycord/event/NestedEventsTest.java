package io.minimum.libertycord.event;

import java.util.concurrent.CountDownLatch;

import net.md_5.bungee.event.EventHandler;
import org.junit.Assert;
import org.junit.Test;

public class NestedEventsTest
{

    private final EventBus bus = new EventBus( null );
    private final CountDownLatch latch = new CountDownLatch( 2 );

    @Test
    public void testNestedEvents()
    {
        new ReflectiveEventBusAdapter(bus).register( this );
        bus.post( new FirstEvent() );
        Assert.assertEquals( 0, latch.getCount() );
    }

    @EventHandler
    public void firstListener(FirstEvent event)
    {
        bus.post( new SecondEvent() );
        Assert.assertEquals( 1, latch.getCount() );
        latch.countDown();
    }

    @EventHandler
    public void secondListener(SecondEvent event)
    {
        latch.countDown();
    }

    public static class FirstEvent
    {
    }

    public static class SecondEvent
    {
    }
}
