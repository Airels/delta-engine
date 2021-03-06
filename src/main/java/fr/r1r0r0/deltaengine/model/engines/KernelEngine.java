package fr.r1r0r0.deltaengine.model.engines;

import fr.r1r0r0.deltaengine.exceptions.InputKeyStackingError;
import fr.r1r0r0.deltaengine.exceptions.maplevel.MapLevelAlreadyExistException;
import fr.r1r0r0.deltaengine.exceptions.maplevel.MapLevelDoesNotExistException;
import fr.r1r0r0.deltaengine.model.Direction;
import fr.r1r0r0.deltaengine.model.elements.HUDElement;
import fr.r1r0r0.deltaengine.model.elements.entity.Entity;
import fr.r1r0r0.deltaengine.model.engines.utils.Key;
import fr.r1r0r0.deltaengine.model.events.Event;
import fr.r1r0r0.deltaengine.model.events.InputEvent;
import fr.r1r0r0.deltaengine.model.maplevel.MapLevel;
import fr.r1r0r0.deltaengine.tools.JavaFXCommand;
import javafx.stage.Stage;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Core of the engine, oversees all engines and use them to render final game. <br>
 * Game developer uses all given methods to build its game, engines.kernel just try to render his creation using all others engines.
 */
public final class KernelEngine {

    public final static int DEFAULT_FRAME_RATE = 60;

    private final InputEngine inputEngine;
    private final AIEngine iaEngine;
    private final PhysicsEngine physicsEngine;
    private final EventEngine eventEngine;
    private final GraphicsEngine graphicsEngine;
    private final SoundEngine soundEngine;
    private final NetworkEngine networkEngine;
    private final java.util.Map<String, MapLevel> maps;
    private final List<HUDElement> hudElements;
    private final Thread frameRatePrinter;
    private MapLevel currentMapLevel;
    private volatile boolean currentMapHalted;
    private volatile boolean initialized, started;
    private int frameRate, optimalTime;
    private volatile int currentFrameRate;

    /**
     * Default constructor. Creates all sub-engines but not initializing them.
     */
    KernelEngine() {
        currentFrameRate = 0;
        frameRatePrinter = new Thread(() -> {
            while (!Thread.interrupted()) {
                System.out.println("FPS: " + currentFrameRate);
                currentFrameRate = 0;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {
                    break;
                }
            }
        });

        inputEngine = (InputEngine) Engines.INPUT_ENGINE.getInstance();
        iaEngine = (AIEngine) Engines.AI_ENGINE.getInstance();
        physicsEngine = (PhysicsEngine) Engines.PHYSICS_ENGINE.getInstance();
        eventEngine = (EventEngine) Engines.EVENT_ENGINE.getInstance();
        graphicsEngine = (GraphicsEngine) Engines.GRAPHICS_ENGINE.getInstance();
        soundEngine = (SoundEngine) Engines.SOUND_ENGINE.getInstance();
        networkEngine = (NetworkEngine) Engines.NETWORK_ENGINE.getInstance();

        maps = new HashMap<>();
        hudElements = new ArrayList<>();

        currentMapHalted = false;

        initialized = false;
        started = false;

        setFrameRate(DEFAULT_FRAME_RATE);
    }

    /**
     * Initializes Kernel Engine and its components. If kernel was initialized before, then init will be skipped.
     *
     * @param stage Given stage by JavaFX
     */
    void init(Stage stage) {
        if (initialized) return;

        physicsEngine.setPhysicalRate(100);
        inputEngine.setStage(stage);
        graphicsEngine.setStage(stage);

        for (Engines e : Engines.values()) {
            getEngine(e).init();
        }

        initialized = true;
    }

    /**
     * Kernel Engine loop.
     */
    void start() {
        if (started)
            throw new RuntimeException("Engine is already running");

        started = true;
        long updateStart, updateDuration, waitTime;
        while (!Thread.interrupted()) {
            updateStart = System.currentTimeMillis();
            if (!currentMapHalted) {
                for (Engines e : Engines.values()) {
                    if (e == Engines.GRAPHICS_ENGINE) {
                        try {
                            JavaFXCommand.runAndWait(graphicsEngine);
                        } catch (InterruptedException ignored) {
                        }
                    } else {
                        getEngine(e).run();
                    }
                }
            } else {
                inputEngine.run();
                try {
                    JavaFXCommand.runAndWait(graphicsEngine);
                } catch (InterruptedException ignored) {
                }
            }

            updateDuration = System.currentTimeMillis() - updateStart;

            try {
                currentFrameRate++;
                waitTime = optimalTime - updateDuration;
                if (waitTime > 0)
                    Thread.sleep(waitTime);
                // else we're late!
            } catch (InterruptedException e) {
                break;
            }
        }

        started = false;
    }

    /**
     * Do a manual tick on the DeltaEngine
     * (useful to render only 1 frame, when modifications are made, and you need to render them).
     * Only works if current map is halted, if isn't, this method has no effect
     */
    public void tick() {
        for (Engines e : Engines.values()) {
            tick(e);
        }
    }

    /**
     * Do a manual tick on a specific Engine of the DeltaEngine
     * (useful to compute only 1 frame, when modifications are made, and you need to render them).
     * Only works if current map is halted, if isn't, this method has no effect
     */
    public void tick(Engines engine) {
        try {
            if (!currentMapHalted) return;

            if (engine == Engines.GRAPHICS_ENGINE)
                JavaFXCommand.runAndWait(graphicsEngine);
            else
                getEngine(engine).run();
        } catch (InterruptedException ignored) {
        }
    }

    /**
     * Allows knowing Engine frame rate goal currently. <br>
     * Default : 60. Kernel will try to process itself and all others engines 60 times in one second.
     *
     * @return current frame rate
     */
    public int getFrameRate() {
        return frameRate;
    }

    /**
     * To define a frame rate that Engine will try to be sync on <br>
     * Default : 60. Kernel will try to process itself and all others engines 60 times in one second.
     *
     * @param frameRate new engine frame rate
     */
    public void setFrameRate(int frameRate) {
        this.frameRate = frameRate;
        this.optimalTime = 1000 / frameRate;
        this.physicsEngine.setMaxRunDelta(this.frameRate);
    }

    /**
     * Allowing to set a new rate for the physics.
     * More the value higher, more the precision is
     *
     * @param physicalRate new rate to define
     */
    public void setPhysicalRate(int physicalRate) {
        this.physicsEngine.setPhysicalRate(physicalRate);
    }

    /**
     * Enable or disable printing current FrameRate in standard output.
     *
     * @param enabled boolean true to enable it, false to disable it
     */
    public void printFrameRate(boolean enabled) {
        if (enabled)
            frameRatePrinter.start();
        else
            frameRatePrinter.interrupt();
    }

    /**
     * Private method to get others engines. Used while looping on all others engines.
     *
     * @param engine engine ID to get
     * @return Engine object
     */
    private Engine getEngine(Engines engine) {
        return engine.getInstance();
    }

    /**
     * Getting network engine to send or receive data
     *
     * @return network engine instance
     */
    public NetworkEngine getNetworkEngine() {
        return networkEngine;
    }

    /**
     * Getting sound engine to add, remove or control sounds
     *
     * @return sound engine instance
     */
    public SoundEngine getSoundEngine() {
        return soundEngine;
    }

    /**
     * Add new mapLevel to the engine. MapLevel name must be unique.
     *
     * @param mapLevel the mapLevel to add
     * @throws MapLevelAlreadyExistException if mapLevel's name already been added
     */
    public synchronized void addMap(MapLevel mapLevel) throws MapLevelAlreadyExistException {
        String name = mapLevel.getName();
        if (maps.containsKey(name))
            throw new MapLevelAlreadyExistException(maps.get(name), mapLevel);

        maps.put(name, mapLevel);
    }

    /**
     * Remove given name map to the engines.kernel. If current map is the map to remove, map will be unloaded
     *
     * @param name the name of the map to remove
     * @return true if removed, false if no map was already been added to this name
     */
    public synchronized boolean removeMap(String name) {
        if (currentMapLevel != null && currentMapLevel.getName().equals(name))
            unloadMap();

        return maps.remove(name) != null;
    }

    /**
     * Clear all maps from the engines.kernel. If current map is running, it will be unloaded
     */
    public synchronized void clearMaps() {
        unloadMap();
        maps.clear();
    }

    /**
     * Get current loaded and running map
     *
     * @return MapLevel loaded and running
     */
    public MapLevel getCurrentMap() {
        return currentMapLevel;
    }

    /**
     * Load a previous added map on the Engine by its name
     *
     * @param name the name of map to load
     * @throws MapLevelDoesNotExistException if map was not added before
     * @see KernelEngine#addMap(MapLevel) to add a new map
     */
    public synchronized void setCurrentMap(String name) throws MapLevelDoesNotExistException {
        MapLevel mapLevelToLoad = maps.get(name);

        if (mapLevelToLoad == null)
            throw new MapLevelDoesNotExistException(name);

        loadMap(mapLevelToLoad);
    }

    /**
     * Halt all elements to the map, avoiding them to be processed by engines (like a freeze). <br>
     * Graphical, Input, Sound and Network engines will not be halted
     */
    public void haltCurrentMap() {
        currentMapHalted = true;
    }

    /**
     * Resume halted map
     */
    public void resumeCurrentMap() {
        currentMapHalted = false;
    }

    /**
     * Bind a keyboard input to an event. Event will be triggered when user press given key. <br>
     * If previous input was bound, it will be overridden
     *
     * @param key   to bind
     * @param event event to bind on actions
     * @throws InputKeyStackingError if key is already bound
     */
    public void setInput(Key key, InputEvent event) throws InputKeyStackingError {
        inputEngine.setInput(key, event);
    }

    /**
     * Clear an input event associated to a key
     *
     * @param key to clear
     */
    public void clearInput(Key key) {
        inputEngine.clearKey(key);
    }

    /**
     * Add a global event, independent of the map
     *
     * @param event the event to add
     */
    public synchronized void addGlobalEvent(Event event) {
        eventEngine.addGlobalEvent(event);
    }

    /**
     * Remove specified global event
     *
     * @param event the event to remove
     */
    public synchronized void removeGlobalEvent(Event event) {
        eventEngine.removeGlobalEvent(event);
    }

    /**
     * Clear all global events
     */
    public synchronized void clearGlobalEvents() {
        eventEngine.clearGlobalEvents();
    }

    /**
     * Add a static element corresponding to HUD on the interface
     *
     * @param hudElement the element to add
     */
    public synchronized void addHUDElement(HUDElement hudElement) {
        hudElements.add(hudElement);
        addHUDElementToGraphicsEngine(hudElement);
    }

    /**
     * Remove a static element corresponding to HUD previously added on the interface
     *
     * @param hudElement the element to remove
     */
    public synchronized void removeHUDElement(HUDElement hudElement) {
        hudElements.remove(hudElement);
        removeHUDElementFromGraphicsEngine(hudElement);
    }

    /**
     * Clear all static HUD elements from the interface
     */
    public synchronized void clearHUDElements() {
        for (HUDElement hudElement : hudElements)
            removeHUDElementFromGraphicsEngine(hudElement);

        hudElements.clear();
    }

    /**
     * Loads given mapLevel, loading associated elements, AI, and events
     *
     * @param mapLevel the mapLevel to load
     */
    private synchronized void loadMap(MapLevel mapLevel) {
        if (currentMapLevel != null)
            unloadMap();


        physicsEngine.setMap(mapLevel);
        eventEngine.setMap(mapLevel);
        setMapInTheGraphicsEngine(mapLevel);

        for (Entity mapEntity : mapLevel.getEntities()) {
            if (mapEntity.getAI() != null)
                iaEngine.addAI(mapEntity.getAI());
        }

        currentMapLevel = mapLevel;
    }

    /**
     * Unload current map, loading associated elements, AI, and events
     */
    private synchronized void unloadMap() {
        physicsEngine.clearMap();
        eventEngine.clearMap();
        clearMapFromTheGraphicsEngine();

        for (Entity mapEntity : currentMapLevel.getEntities()) {
            if (mapEntity.getAI() != null)
                iaEngine.removeAI(mapEntity.getAI());
        }

        currentMapLevel = null;
    }

    /**
     * Set up icon of the Engine in the OS Environment.
     *
     * @param img logo image path to set
     */
    public void setGameIcon(String img) {
        InputStream sin = getClass().getResourceAsStream(img);
        try {
            JavaFXCommand.runAndWait(() -> graphicsEngine.setStageIcon(new javafx.scene.image.Image(sin)));
        } catch (InterruptedException ignored) {
        }
    }

    /**
     * Check if an entity can go to the next cell according to given direction
     *
     * @param entity    Entity to check
     * @param direction Next direction
     * @return boolean true if entity can go to next cell, false otherwise
     */
    public boolean canGoToNextCell(Entity entity, Direction direction) {
        return physicsEngine.canGoToNextCell(entity, direction);
    }

    /**
     * Check if the entity can move with the direction given in argument
     *
     * @param entity    Entity to check
     * @param direction Next direction
     * @return boolean true if entity can go to the direction, false otherwise
     */
    public boolean isAvailableDirection(Entity entity, Direction direction) {
        return physicsEngine.isAvailableDirection(entity, direction);
    }

    /**
     * Adding an HUD element to the graphics engine, using Platform.runLater of JavaFX
     *
     * @param element Element to add
     */
    private void addHUDElementToGraphicsEngine(HUDElement element) {
        try {
            JavaFXCommand.runAndWait(() -> graphicsEngine.addHudElement(element));
        } catch (InterruptedException ignored) {
        }
    }

    /**
     * Removing an element from the graphics engine, using Platform.runLater of JavaFX
     *
     * @param element Element to remove
     */
    private void removeHUDElementFromGraphicsEngine(HUDElement element) {
        try {
            JavaFXCommand.runAndWait(() -> graphicsEngine.removeElement(element));
        } catch (InterruptedException ignored) {
        }
    }

    /**
     * Allowing set up wanted map in the Graphics Engine, using Platform.runLater of JavaFX
     *
     * @param map map to load
     */
    private void setMapInTheGraphicsEngine(MapLevel map) {
        try {
            JavaFXCommand.runAndWait(() -> graphicsEngine.setMap(map));
        } catch (InterruptedException ignored) {
        }
    }

    /**
     * Clear current Map rendered from the Graphic engine
     */
    private void clearMapFromTheGraphicsEngine() {
        try {
            JavaFXCommand.runAndWait(graphicsEngine::clearMap);
        } catch (InterruptedException ignored) {
        }
    }
}
