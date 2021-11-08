package fr.r1r0r0.deltaengine.model.events;

/**
 * Special event used to bind actions on Keyboard events.
 * Can bind two triggers called when a specific key is pressed or released.
 */
public final class InputEvent {

    private final Trigger pressTrigger, releaseTrigger;

    /**
     * Default constructor. Defines which triggers will be called when key event is caught by the engine.
     * @param pressTrigger trigger when key is pressed
     * @param releaseTrigger trigger when key is released
     */
    public InputEvent(Trigger pressTrigger, Trigger releaseTrigger) {
        this.pressTrigger = pressTrigger;
        this.releaseTrigger = releaseTrigger;
    }

    /**
     * Run bound trigger containing actions to execute when a key is pressed
     */
    public void runPressTrigger() {
        pressTrigger.trigger();
    }

    /**
     * Run bound trigger containing actions to execute when a key is released
     */
    public void runReleaseTrigger() {
        releaseTrigger.trigger();
    }
}
