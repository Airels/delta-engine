package fr.r1r0r0.deltaengine.model.engines;

import fr.r1r0r0.deltaengine.model.events.Event;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * The Event Engine.
 * Executes checkEvent() method of each registered events, each tick.
 */
final class EventEngine implements Engine {

    private final Collection<Event> events;

    /**
     * Default constructor
     */
    EventEngine() {
        this.events = new ConcurrentLinkedDeque<>();
    }

    @Override
    public void init() {

    }

    @Override
    public void run() {
        events.forEach(Event::checkEvent);
    }

    /**
     * Adding an event to the engine
     * @param event event to add
     */
    public void addEvent(Event event) {
        events.add(event);
    }

    /**
     * Remove an event to the engine
     * @param event event to remove
     */
    public void removeEvent(Event event) {
        events.remove(event);
    }

    /**
     * Clear all registered events from the engine
     */
    public void clearEvents() {
        events.clear();
    }
}
