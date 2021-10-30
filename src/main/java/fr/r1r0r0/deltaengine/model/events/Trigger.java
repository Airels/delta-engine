package fr.r1r0r0.deltaengine.model.events;

/**
 * A trigger, who will be triggered commonly by an Event object.
 */
public interface Trigger {

    /**
     * Method to execute trigger behaviour
     */
    void trigger();
}
