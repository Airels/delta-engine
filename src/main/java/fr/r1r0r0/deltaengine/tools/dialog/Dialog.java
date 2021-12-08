package fr.r1r0r0.deltaengine.tools.dialog;

import fr.r1r0r0.deltaengine.tools.JavaFXCommand;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.StageStyle;

import java.awt.*;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;

/**
 * Utility class usable to show simple dialog alert
 * <br />
 * This class is a fork from the original class here :
 * https://github.com/Airels/HeroManager/blob/master/src/main/java/fr/univ_amu/heromanager/utils/gui/Dialog.java
 */
public class Dialog {

    private Alert alert;
    private boolean exceptionAlert;

    /**
     * Constructor of this class, who allows to init desired dialog
     *
     * @param dialogType      type of dialog
     * @param applicationName name of your application
     * @param title           title of the dialog
     * @param text            text of the dialog
     */
    public Dialog(DialogType dialogType, String applicationName, String title, String text) {
        try {
            JavaFXCommand.runAndWait(() -> {
                alert = new Alert(dialogType.getJfxType());
                alert.setTitle(applicationName + " - " + dialogType.name().toLowerCase());
                alert.setHeaderText(title);
                alert.setContentText(text);

                exceptionAlert = false;
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 2nd constructor, used to show an error pop up with an exception.
     * Print stack trace in standard output, and end program
     *
     * @param applicationName name of your application
     * @param title           title of the dialog
     * @param e               exception to show
     */
    public Dialog(String applicationName, String title, Exception e) {
        try {
            JavaFXCommand.runAndWait(() -> {
                alert = new Alert(DialogType.ERROR.getJfxType());
                alert.setTitle(applicationName + " - Critical error");
                alert.setHeaderText(title);
                alert.setContentText(e.getMessage());

                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                String exceptionText = sw.toString();

                Label label = new Label("Exception stacktrace :");

                TextArea exceptionTextArea = new TextArea(exceptionText);
                exceptionTextArea.setEditable(false);
                exceptionTextArea.setWrapText(true);
                exceptionTextArea.setMaxWidth(Double.MAX_VALUE);
                exceptionTextArea.setMaxHeight(Double.MAX_VALUE);
                GridPane.setVgrow(exceptionTextArea, Priority.ALWAYS);
                GridPane.setHgrow(exceptionTextArea, Priority.ALWAYS);

                GridPane expContent = new GridPane();
                expContent.setMaxWidth(Double.MAX_VALUE);
                expContent.add(label, 0, 0);
                expContent.add(exceptionTextArea, 0, 1);

                alert.getDialogPane().setExpandableContent(expContent);

                e.printStackTrace();

                exceptionAlert = true;
            });
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 3rd constructor, used to create a busy wait dialog
     *
     * @param applicationName name of your application
     * @param message         message to show
     */
    public Dialog(String applicationName, String message) {
        try {
            JavaFXCommand.runAndWait(() -> {
                alert = new Alert(Alert.AlertType.NONE);
                alert.setTitle(applicationName);
                alert.setContentText(message);

                alert.getDialogPane().getButtonTypes().clear();
                alert.getDialogPane().getButtonTypes().add(ButtonType.OK);
                alert.getDialogPane().lookupButton(ButtonType.OK).setDisable(true);
                alert.initStyle(StageStyle.UNDECORATED);

                exceptionAlert = false;
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * To show dialog
     */
    public void show() {
        Platform.runLater(() -> {
            playSound();
            alert.show();

            if (exceptionAlert)
                alert.setOnCloseRequest(event -> System.exit(1));
        });
    }

    /**
     * To show dialog and wait its closure by the user
     */
    public void showAndWait() {
        try {
            JavaFXCommand.runAndWait(() -> {
                playSound();

                if (exceptionAlert) {
                    alert.showAndWait();
                    System.exit(1);
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Used to play Windows sound according to alert type
     */
    private void playSound() {
        Runnable sound = null;
        switch (alert.getAlertType()) {
            case ERROR -> sound = ((Runnable) Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.hand"));
            case WARNING -> sound = ((Runnable) Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.exclamation"));
            case INFORMATION -> sound = ((Runnable) Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.asterisk"));
            case CONFIRMATION -> sound = ((Runnable) Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.question"));
        }
        if (sound != null) sound.run();
    }

    /**
     * To close dialog
     */
    public void close() {
        try {
            JavaFXCommand.runAndWait(alert::close);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

