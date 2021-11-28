package fr.r1r0r0.deltaengine.tools.dialog;

import javafx.scene.control.Alert;

/**
 * All types dialogues can be
 */
public enum DialogType {

    /**
     * NONE type will set a default output, with no sounds or features
     */
    NONE(Alert.AlertType.NONE),

    /**
     * INFORMATION type will show dialog box as an information box
     */
    INFORMATION(Alert.AlertType.INFORMATION),

    /**
     * WARNING type will show dialog box as a warning box
     */
    WARNING(Alert.AlertType.WARNING),

    /**
     * CONFIRMATION type will ask user a confirmation
     */
    CONFIRMATION(Alert.AlertType.CONFIRMATION),

    /**
     * ERROR type is a simple error box alert
     */
    ERROR(Alert.AlertType.ERROR);

    private final Alert.AlertType jfxType;

    DialogType(Alert.AlertType jfxType) {
        this.jfxType = jfxType;
    }

    Alert.AlertType getJfxType() {
        return jfxType;
    }
}
