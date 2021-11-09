package fr.r1r0r0.deltaengine.model.engines;

import fr.r1r0r0.deltaengine.model.events.Event;

import java.util.ArrayList;
import java.util.List;

class EventEngine implements Engine {

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

    public synchronized void addEvent(Event event) {
        events.add(event);
    }

    public synchronized void removeEvent(Event event) {
        events.remove(event);
    }

    public synchronized void clearEvents() {
        events.clear();
    }
}
