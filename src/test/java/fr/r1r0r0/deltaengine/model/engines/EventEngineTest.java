package fr.r1r0r0.deltaengine.model.engines;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import fr.r1r0r0.deltaengine.model.events.Event;
import fr.r1r0r0.deltaengine.model.maplevel.MapLevel;
import fr.r1r0r0.deltaengine.model.maplevel.MapLevelBuilder;

class EventEngineTest {

    private final EventEngine eventEngine = new EventEngine();

    @Test
    void addEvent() {
        AtomicInteger counter = new AtomicInteger(0);

        eventEngine.run();

        assertEquals(0, counter.get());

        eventEngine.addGlobalEvent(new Event() {
            @Override
            public void checkEvent() {
                counter.incrementAndGet();
            }
        });

        assertEquals(0, counter.get());
        eventEngine.run();
        assertEquals(1, counter.get());

        for (int i = 0; i < 3; i++) {
            eventEngine.addGlobalEvent(new Event() {
                @Override
                public void checkEvent() {
                    counter.incrementAndGet();
                }
            });
        }

        assertEquals(1, counter.get());
        eventEngine.run();
        assertEquals(5, counter.get());
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

        eventEngine.addGlobalEvent(e);

        for (int i = 0; i < 9; i++) {
            eventEngine.addGlobalEvent(new Event() {
                @Override
                public void checkEvent() {
                    counter.incrementAndGet();
                }
            });
        }

        eventEngine.run();
        assertEquals(10, counter.get());

        eventEngine.removeGlobalEvent(e);
        eventEngine.run();

        assertEquals(19, counter.get());
    }

    @Test
    void clearEvents() {
        AtomicInteger counter = new AtomicInteger();

        for (int i = 0; i < 10; i++) {
            eventEngine.addGlobalEvent(new Event() {
                @Override
                public void checkEvent() {
                    counter.incrementAndGet();
                }
            });
        }

        eventEngine.run();
        assertEquals(10, counter.get());

        eventEngine.clearGlobalEvents();

        eventEngine.run();
        assertEquals(10, counter.get());
    }

    @Test
    void eventsFromMap() {
        EventEngine eventEngine = new EventEngine();
        eventEngine.init();

        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        MapLevel map = new MapLevelBuilder("test", 0, 0).build();
        map.addEvent(new Event() {
            @Override
            public void checkEvent() {
                atomicBoolean.set(true);
            }
        });
        eventEngine.setMap(map);
        eventEngine.run();
        assertTrue(atomicBoolean.get());

        AtomicBoolean atomicBoolean1 = new AtomicBoolean(false);
        map.addEvent(new Event() {
            @Override
            public void checkEvent() {
                atomicBoolean1.set(true);
            }
        });
        eventEngine.run();
        assertTrue(atomicBoolean1.get());

        AtomicBoolean atomicBoolean2 = new AtomicBoolean(false);
        map.addEvent(new Event() {
            @Override
            public void checkEvent() {
                atomicBoolean2.set(true);
            }
        });

        eventEngine.clearMap();
        eventEngine.run();
        assertFalse(atomicBoolean2.get());
    }
}