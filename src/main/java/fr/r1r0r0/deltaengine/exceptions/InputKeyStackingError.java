package fr.r1r0r0.deltaengine.exceptions;

import fr.r1r0r0.deltaengine.model.engines.utils.Key;
import fr.r1r0r0.deltaengine.model.events.Event;
import fr.r1r0r0.deltaengine.model.events.InputEvent;

public class InputKeyStackingError extends Exception {

    public InputKeyStackingError (String msg) {
        super(msg);
    }

    public InputKeyStackingError (Key key, InputEvent event1, InputEvent event2) {
        this("Key is matching to 2 different event : key=" + key
                + ", event1=" + event1 + ", event2=" + event2);
    }

}
