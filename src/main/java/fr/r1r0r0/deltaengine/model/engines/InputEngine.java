package fr.r1r0r0.deltaengine.model.engines;

import fr.r1r0r0.deltaengine.model.engines.utils.Key;
import fr.r1r0r0.deltaengine.model.events.InputEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

class InputEngine implements Engine {

    private EventHandler<KeyEvent> handler;
    InputEngine(Stage stage) {

    }

    @Override
    public void init() {

    }

    @Override
    public void run() {

    }

    public void setInput(Key key, InputEvent event) {

        KeyEvent keyEvent = new KeyEvent(key.name(),);

    }
}
