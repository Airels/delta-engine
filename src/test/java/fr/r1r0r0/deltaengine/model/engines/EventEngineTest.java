package fr.r1r0r0.deltaengine.model.engines;

import fr.r1r0r0.deltaengine.model.events.Event;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class EventEngineTest {

    private final EventEngine eventEngine = new EventEngine();

    @Test
    void addEvent() {
        AtomicInteger counter = new AtomicInteger(0);

        eventEngine.run();

        Assertions.assertEquals(0, counter.get());

        eventEngine.addEvent(new Event() {
            @Override
            public void checkEvent() {
                counter.incrementAndGet();
            }
        });

        Assertions.assertEquals(0, counter.get());
        eventEngine.run();
        Assertions.assertEquals(1, counter.get());

        for (int i = 0; i < 3; i++) {
            eventEngine.addEvent(new Event() {
                @Override
                public void checkEvent() {
                    counter.incrementAndGet();
                }
            });
        }

        Assertions.assertEquals(1, counter.get());
        eventEngine.run();
        Assertions.assertEquals(5, counter.get());
    }

    @Test
    void removeEvent() {
        AtomicInteger counter = new AtomicInteger();

        Event e = new Event() {
            @Override
            public void checkEvent() {
                counter.incrementAndGet();
            }
        };

        eventEngine.addEvent(e);

        for (int i = 0; i < 9; i++) {
            eventEngine.addEvent(new Event() {
                @Override
                public void checkEvent() {
                    counter.incrementAndGet();
                }
            });
        }

        eventEngine.run();
        Assertions.assertEquals(10, counter.get());

        eventEngine.removeEvent(e);
        eventEngine.run();

        Assertions.assertEquals(19, counter.get());
    }

    @Test
    void clearEvents() {
        AtomicInteger counter = new AtomicInteger();

        for (int i = 0; i < 10; i++) {
            eventEngine.addEvent(new Event() {
                @Override
                public void checkEvent() {
                    counter.incrementAndGet();
                }
            });
        }

        eventEngine.run();
        Assertions.assertEquals(10, counter.get());

        eventEngine.clearEvents();

        eventEngine.run();
        Assertions.assertEquals(10, counter.get());
    }
}