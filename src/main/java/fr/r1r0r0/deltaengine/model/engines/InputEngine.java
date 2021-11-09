package fr.r1r0r0.deltaengine.model.engines;

import fr.r1r0r0.deltaengine.exceptions.InputKeyStackingError;
import fr.r1r0r0.deltaengine.model.engines.utils.Key;
import fr.r1r0r0.deltaengine.model.events.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

/**
 * The Input Engine.
 * JavaFX Key Binding Driver, allows to bind any keyboard keys to a specific event
 *
 * @see Key to see all supported keys
 */
final class InputEngine implements Engine {

    private final Map<KeyCode, InputEvent> events;
    private Stage stage;

    /**
     * Default constructor
     */
    InputEngine() {
        events = new HashMap<>();
    }

    @Override
    public void init() {
        stage.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
            InputEvent event = events.get(e.getCode());
            if (event != null)
                event.runPressTrigger();
        });

        stage.addEventHandler(KeyEvent.KEY_RELEASED, e -> {
            InputEvent event = events.get(e.getCode());
            if (event != null)
                event.runReleaseTrigger();
        });
    }

    @Override
    public void run() {

    }

    /**
     * Allows setting a new input keyboard event to given Keyboard with specified Event to trigger.
     *
     * @param key   Key to bind
     * @param event Event to trigger when Key is bound
     * @throws InputKeyStackingError if given Key is already bound to an Event
     */
    public void setInput(Key key, InputEvent event) throws InputKeyStackingError {
        KeyCode jfxKey = key.getJFXInput();
        if (events.containsKey(jfxKey) && events.get(jfxKey) != event)
            throw new InputKeyStackingError(key, event, events.get(jfxKey));
        events.put(jfxKey, event);
    }

    /**
     * Setting the JavaFX stage. Allowing to bind keyboard inputs to it.
     *
     * @param stage JavaFX stage.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Remove Event from a given key.
     *
     * @param key Key of event that must be removed.
     */
    public void clearKey(Key key) {
        events.remove(key.getJFXInput());
    }
}
