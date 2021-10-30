package fr.r1r0r0.deltaengine.model.events;

import java.util.LinkedList;
import java.util.List;

/**
 * An event. Contain triggers who can be triggered when predefined conditions are met by checkEvent method.
 * If trigger method is called, all triggered contained will be activated.
 * (Implements Observer pattern)
 */
public abstract class Event {

    private List<Trigger> triggers;

    /**
     * Default constructor
     */
    public Event() {
        triggers = new LinkedList<>();
    }

    /**
     * Add a trigger to the event
     * @param trigger trigger to add
     */
    public final void addTrigger(Trigger trigger) {
        triggers.add(trigger);
    }

    /**
     * Remove a trigger from the event
     * @param trigger trigger to remove
     * @return true if trigger has been removed successfully, false otherwise
     */
    public final boolean removeTrigger(Trigger trigger) {
        return triggers.remove(trigger);
    }

    /**
     * Clear all triggers registered to this event
     */
    public final void clearTriggers() {
        triggers.clear();
    }

    /**
     * When called, call all triggers and run their code
     */
    public final void runTriggers() {
        for (Trigger t : triggers)
            t.trigger();
    }

    /**
     * Called by the engine, all code of the event. Everything can be implemented here. <br>
     * To activate all attached triggers, runTriggers() method from Event object must be called.
     * @see Event#runTriggers() to run all registered triggers
     */
    public abstract void checkEvent();
}
