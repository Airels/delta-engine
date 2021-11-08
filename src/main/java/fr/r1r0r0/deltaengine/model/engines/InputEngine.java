package fr.r1r0r0.deltaengine.model.engines;

import fr.r1r0r0.deltaengine.exceptions.InputKeyStackingError;
import fr.r1r0r0.deltaengine.model.engines.utils.Key;
import fr.r1r0r0.deltaengine.model.events.InputEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

class InputEngine implements Engine {

    private Stage stage;
    private final Map<KeyCode, InputEvent> events;

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

    public void setInput(Key key, InputEvent event) throws InputKeyStackingError {
        KeyCode jfxKey = key.getJFXInput();
        if (events.containsKey(jfxKey) && events.get(jfxKey) != event)
            throw new InputKeyStackingError(key,event,events.get(jfxKey));
        events.put(jfxKey, event);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void clearKey(Key key) {
        events.remove(key.getJFXInput());
    }
}
