package fr.r1r0r0.deltaengine.tools;

import javafx.application.Platform;

import java.util.concurrent.CountDownLatch;

/**
 * Tool class used to run JavaFX commands in JavaFX thread synchronously
 */
public class JavaFXCommand {

    /**
     * Run an action in JavaFX thread, and wait to be completed.
     * @param action action to run
     * @throws InterruptedException because it waits JavaFX thread, it will return InterruptedException if current Thread is interrupted
     */
    public static void runAndWait(Runnable action) throws InterruptedException {
        if (action == null)
            throw new NullPointerException("Action is null");

        if (Platform.isFxApplicationThread()) {
            action.run();
            return;
        }

        final CountDownLatch doneLatch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                action.run();
            } finally {
                doneLatch.countDown();
            }
        });

        doneLatch.await();
    }
}
