package fr.r1r0r0.deltaengine.model.engines;

import fr.r1r0r0.deltaengine.exceptions.MapAlreadyExistException;
import fr.r1r0r0.deltaengine.exceptions.MapDoesNotExistException;
import fr.r1r0r0.deltaengine.model.Map;
import fr.r1r0r0.deltaengine.model.elements.Case;
import fr.r1r0r0.deltaengine.model.elements.Entity;
import fr.r1r0r0.deltaengine.model.elements.HUDElement;
import fr.r1r0r0.deltaengine.model.engines.utils.Key;
import fr.r1r0r0.deltaengine.model.events.Event;
import fr.r1r0r0.deltaengine.model.events.InputEvent;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Core of the engine, oversees all engines and use them to render final game. <br>
 * Game developer uses all given methods to build its game, engines.kernel just try to render his creation using all others engines.
 */
public final class KernelEngine {

    public final static int DEFAULT_FRAME_RATE = 60;
    private final InputEngine inputEngine;
    private final IAEngine iaEngine;
    private final PhysicsEngine physicsEngine;
    private final EventEngine eventEngine;
    private final GraphicsEngine graphicsEngine;
    private final SoundEngine soundEngine;
    private final NetworkEngine networkEngine;
    private final java.util.Map<String, Map> maps;
    private final List<Event> globalEvents;
    private final List<HUDElement> hudElements;
    private Map currentMap;
    private boolean currentMapHalted;
    private volatile boolean initialized, started;
    private int frameRate, optimalTime;
    private volatile int currentFrameRate;
    private final Thread frameRatePrinter;

    /**
     * Default constructor. Creates all sub-engines but not initializing them.
     */
    KernelEngine() {
        setFrameRate(DEFAULT_FRAME_RATE);
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
        iaEngine = (IAEngine) Engines.IA_ENGINE.getInstance();
        physicsEngine = (PhysicsEngine) Engines.PHYSICS_ENGINE.getInstance();
        eventEngine = (EventEngine) Engines.EVENT_ENGINE.getInstance();
        graphicsEngine = (GraphicsEngine) Engines.GRAPHICS_ENGINE.getInstance();
        soundEngine = (SoundEngine) Engines.SOUND_ENGINE.getInstance();
        networkEngine = (NetworkEngine) Engines.NETWORK_ENGINE.getInstance();

        maps = new HashMap<>();
        globalEvents = new ArrayList<>();
        hudElements = new ArrayList<>();

        currentMapHalted = false;

        initialized = false;
        started = false;
    }

    /**
     * Initializes Kernel Engine and its components. If kernel was initialized before, then init will be skipped.
     */
    void init() {
        if (initialized) return;

        for (Engines e : Engines.values()) {
            getEngine(e).init();
        }

        // new Thread(soundEngine).start();
        new Thread(networkEngine).start();

        initialized = true;
    }

    /**
     * Kernel Engine loop.
     */
    void start() {
        if (started)
            throw new RuntimeException("Engine is already running");

        started = true;
        Object lock = new Object();
        long updateStart, updateDuration, waitTime;
        while (!Thread.interrupted()) {
            updateStart = System.currentTimeMillis();
            if (!currentMapHalted) {
                for (Engines e : Engines.values()) {
                    if (e == Engines.GRAPHICS_ENGINE) {
                        Platform.runLater(() -> {
                            graphicsEngine.run();
                            synchronized (lock) {
                                lock.notifyAll();
                            }
                        });
                    } else {
                        getEngine(e).run();
                    }
                }
            } else {
                inputEngine.run();
                Platform.runLater(graphicsEngine);
            }

            try {
                synchronized (lock) {
                    lock.wait();
                }
            } catch (InterruptedException e) {
                break;
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
     * Add new map to the engine. Map name must be unique.
     *
     * @param map the map to add
     * @throws MapAlreadyExistException if map's name already been added
     */
    public synchronized void addMap(Map map) throws MapAlreadyExistException {
        if (maps.containsKey(map.getName()))
            throw new MapAlreadyExistException(map.getName());

        maps.put(map.getName(), map);
    }

    /**
     * Remove given name map to the engines.kernel. If current map is the map to remove, map will be unloaded
     *
     * @param name the name of the map to remove
     * @return true if removed, false if no map was already been added to this name
     */
    public synchronized boolean removeMap(String name) {
        if (currentMap != null && currentMap.getName().equals(name))
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
     * @return Map loaded and running
     */
    public Map getCurrentMap() {
        return currentMap;
    }

    /**
     * Load a previous added map on the Engine by its name
     *
     * @param name the name of map to load
     * @throws MapDoesNotExistException if map was not added before
     * @see KernelEngine#addMap(Map) to add a new map
     */
    public synchronized void setCurrentMap(String name) throws MapDoesNotExistException {
        Map mapToLoad = maps.get(name);

        if (mapToLoad == null)
            throw new MapDoesNotExistException(name);

        loadMap(mapToLoad);
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
     */
    public void setInput(Key key, InputEvent event) {
        inputEngine.setInput(key, event);
    }

    /**
     * Clear an input event associated to a key
     *
     * @param key to clear
     */
    public void clearInput(Key key) {
        inputEngine.setInput(key, null);
    }

    /**
     * Add a global event, independent of the map
     *
     * @param event the event to add
     */
    public synchronized void addGlobalEvent(Event event) {
        globalEvents.add(event);
        eventEngine.addEvent(event);
    }

    /**
     * Remove specified global event
     *
     * @param event the event to remove
     */
    public synchronized void removeGlobalEvent(Event event) {
        globalEvents.remove(event);
        eventEngine.removeEvent(event);
    }

    /**
     * Clear all global events
     */
    public synchronized void clearGlobalEvents() {
        for (Event e : globalEvents)
            eventEngine.removeEvent(e);

        globalEvents.clear();
    }

    /**
     * Add a static element corresponding to HUD on the interface
     *
     * @param hudElement the element to add
     */
    public synchronized void addHUDElement(HUDElement hudElement) {
        hudElements.add(hudElement);
        graphicsEngine.addElement(hudElement);
    }

    /**
     * Remove a static element corresponding to HUD previously added on the interface
     *
     * @param hudElement the element to remove
     */
    public synchronized void removeHUDElement(HUDElement hudElement) {
        hudElements.remove(hudElement);
        graphicsEngine.removeElement(hudElement);
    }

    /**
     * Clear all static HUD elements from the interface
     */
    public synchronized void clearHUDElements() {
        for (HUDElement hudElement : hudElements)
            graphicsEngine.removeElement(hudElement);

        hudElements.clear();
    }

    /**
     * Loads given map, loading associated elements, IA, and events
     *
     * @param map the map to load
     */
    private void loadMap(Map map) {
        if (currentMap != null)
            unloadMap();

        int width = map.getWidth(),
                height = map.getHeight();

        Collection<Case> mapCases = map.getCases();
        Collection<Entity> mapEntities = map.getEntities();
        Collection<Event> mapEvents = map.getEvents();

        physicsEngine.setMap(map);
        graphicsEngine.setMap(map);

        for (Entity mapEntity : mapEntities) {
            graphicsEngine.addElement(mapEntity);
            if (mapEntity.getIA() != null)
                iaEngine.addIA(mapEntity.getIA());
        }

        for (Event event : mapEvents) {
            eventEngine.addEvent(event);
        }

        currentMap = map;
    }

    /**
     * Unload current map, loading associated elements, IA, and events
     */
    private void unloadMap() {
        Collection<Case> mapCases = currentMap.getCases();
        Collection<Entity> mapEntities = currentMap.getEntities();
        Collection<Event> mapEvents = currentMap.getEvents();

        physicsEngine.clearMap();


        for (Case c : mapCases) {
            graphicsEngine.removeElement(c);
        }

        for (Entity entity : mapEntities) {
            graphicsEngine.removeElement(entity);
            if (entity.getIA() != null)
                iaEngine.removeIA(entity.getIA());
        }

        for (Event mapEvent : mapEvents) {
            eventEngine.removeEvent(mapEvent);
        }

        currentMap = null;
    }
}
