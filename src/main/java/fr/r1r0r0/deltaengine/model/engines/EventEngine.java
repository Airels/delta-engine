package fr.r1r0r0.deltaengine.model.engines;

import fr.r1r0r0.deltaengine.model.events.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 */
final class EventEngine implements Engine {

    private final List<Event> events;

    EventEngine() {
        this.events = new ArrayList<>();
    }

    @Override
    public void init() {

    }

    @Override
    public void run() {
        synchronized (this) {
            events.forEach(Event::checkEvent);
        }
    }

    /**
     * TODO
     * @param event
     */
    public synchronized void addEvent(Event event) {
        events.add(event);
    }

    /**
     * TODO
     * @param event
     */
    public synchronized void removeEvent(Event event) {
        events.remove(event);
    }

    /**
     * TODO
     */
    public synchronized void clearEvents() {
        events.clear();
    }
}
