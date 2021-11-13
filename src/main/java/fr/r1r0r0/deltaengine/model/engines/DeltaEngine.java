package fr.r1r0r0.deltaengine.model.engines;

import fr.r1r0r0.deltaengine.exceptions.AlreadyInitializedException;
import fr.r1r0r0.deltaengine.exceptions.NotInitializedException;
import javafx.application.Application;
import javafx.stage.Stage;

public final class DeltaEngine extends Application {

    private static KernelEngine kernelEngine;

    /**
     * The main entry point for all JavaFX applications.
     * The start method is called after the init method has returned,
     * and after the system is ready for the application to begin running.
     *
     * <p>
     * NOTE: This method is called on the JavaFX Application Thread.
     * </p>
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set.
     *                     Applications may create other stages, if needed, but they will not be
     *                     primary stages.
     * @throws Exception if something goes wrong
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        kernelEngine = new KernelEngine();
        kernelEngine.init(primaryStage);

        synchronized (DeltaEngine.class) {
            DeltaEngine.class.notifyAll();
        }
    }

    @Override
    public void stop() {
        System.exit(0);
    }

    /**
     * DeltaEngine launcher. Main entry point of the Engine. <br>
     * Once initialized and started, it will return the Kernel of the engine,
     * allowing to fully manipulate the engine and develop your game.
     * @return KernelEngine The Core of the Engine
     * @throws AlreadyInitializedException If DeltaEngine was previously launched
     * @see DeltaEngine#getKernelEngine() to retrieve Kernel if DeltaEngine is already launched
     */
    public static KernelEngine launch() throws AlreadyInitializedException {
        if (kernelEngine != null)
            throw new AlreadyInitializedException("DeltaEngine is already launched. Please use getKernelEngine() to retrieve it.");

        new Thread(() -> Application.launch(DeltaEngine.class)).start();

        synchronized (DeltaEngine.class) {
            try {
                DeltaEngine.class.wait();
            } catch (InterruptedException e) {
                showKernelCriticalError(e);
            }
        }

        new Thread(kernelEngine::start).start();
        return kernelEngine;
    }

    /**
     * Retrieves the Kernel of DeltaEngine.
     * It must first has been launched before getting it.
     * @return KernelEngine Kernel of the DeltaEngine
     * @throws NotInitializedException If DeltaEngine was not launched properly before.
     * @see DeltaEngine#launch() to launch DeltaEngine first before trying to retrieve Kernel
     */
    public static KernelEngine getKernelEngine() throws NotInitializedException {
        if (kernelEngine == null) throw new NotInitializedException("DeltaEngine was not initialized yet. Please use launch() first.");

        return kernelEngine;
    }

    static void showKernelCriticalError(Exception e) {
        System.err.println("KERNEL CRITICAL ERROR :");
        e.printStackTrace();
        System.err.println("Please report this incident on GitHub page.");
        System.exit(1);
    }

    static void showEngineError(Exception e) {
        System.err.println("DELTAENGINE ERROR :");
        e.printStackTrace();
    }
}
