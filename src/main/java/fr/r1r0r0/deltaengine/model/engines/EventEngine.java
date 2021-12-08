package fr.r1r0r0.deltaengine.model.engines;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedDeque;

import fr.r1r0r0.deltaengine.model.events.Event;
import fr.r1r0r0.deltaengine.model.maplevel.MapLevel;

/**
 * The Event Engine.
 * Executes checkEvent() method of each registered events, each tick.
 */
final class EventEngine implements Engine {

    private final Collection<Event> globalEvents;
    private MapLevel currentMap;

    /**
     * Default constructor
     */
    EventEngine() {
        this.globalEvents = new ConcurrentLinkedDeque<>();
    }

    @Override
    public void init() {

    }

    @Override
    public void run() {
        globalEvents.forEach(Event::checkEvent);

        if (currentMap != null)
            currentMap.getEvents().forEach(Event::checkEvent);
    }

    /**
     * Adding a global event to the engine
     * @param event event to add
     */
    public void addGlobalEvent(Event event) {
        globalEvents.add(event);
    }

    /**
     * Remove a global event to the engine
     * @param event event to remove
     */
    public void removeGlobalEvent(Event event) {
        globalEvents.remove(event);
    }

    /**
     * Clear all registered global events from the engine
     */
    public void clearGlobalEvents() {
        globalEvents.clear();
    }

    /**
     * Set current map to activate all map events
     * @param map Map to set
     */
    public void setMap(MapLevel map){
        this.currentMap = map;
    }

    /**
     * Reset current map, to stop map events treatment
     */
    public void clearMap() {
        this.currentMap = null;
    }
}
