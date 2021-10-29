package fr.r1r0r0.deltaengine.model.engines;

import fr.r1r0r0.deltaengine.exceptions.MapAlreadyExistException;
import fr.r1r0r0.deltaengine.exceptions.MapDoesNotExistException;
import fr.r1r0r0.deltaengine.exceptions.UnknownEngineException;
import fr.r1r0r0.deltaengine.model.Map;
import fr.r1r0r0.deltaengine.model.elements.*;
import fr.r1r0r0.deltaengine.model.engines.utils.Key;
import fr.r1r0r0.deltaengine.model.events.Event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Core of the engine, oversees all engines and use them to render final game. <br>
 * Game developer uses all given methods to build its game, kernel just try to render his creation using all others engines.
 */
public class KernelEngine extends Thread {

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
    private static KernelEngine instance;
    private Map currentMap;
    private int frameRate;

    private boolean currentMapHalted;

    /**
     * Default constructor. Not accessible from outside, because kernel must be omnipresent everywhere on the code.
     */
    private KernelEngine() {
        inputEngine = new InputEngine();
        iaEngine = new IAEngine();
        physicsEngine = new PhysicsEngine();
        eventEngine = new EventEngine();
        graphicsEngine = new GraphicsEngine();
        soundEngine = new SoundEngine();
        networkEngine = new NetworkEngine();

        maps = new HashMap<>();
        globalEvents = new ArrayList<>();
        hudElements = new ArrayList<>();

        frameRate = 60;

        currentMapHalted = false;
    }

    private synchronized void tick() {

    }

    /**
     * To get an instance of kernel engine. Kernel is omnipresent everywhere on the code.
     *
     * @return KernelEngine instance
     */
    public static KernelEngine getInstance() {
        if (instance == null)
            instance = new KernelEngine();

        return instance;
    }

    @Override
    public synchronized void start() {
        super.start();

        try {
            for (Engines e : Engines.values()) {
                getEngine(e).init();
            }

            new Thread(soundEngine).start();
            new Thread(networkEngine).start();
        } catch (UnknownEngineException e) {
            System.err.println("KERNEL CRITICAL ERROR :");
            e.printStackTrace();
            System.err.println("Please report this incident on GitHub page.");
            System.exit(1);
        }
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                if (!currentMapHalted) {
                    for (Engines e : Engines.values()) {
                        getEngine(e).run();
                    }
                } else {
                    inputEngine.run();
                    graphicsEngine.run();
                }
            }
        } catch (UnknownEngineException e) {
            System.err.println("KERNEL CRITICAL ERROR :");
            e.printStackTrace();
            System.err.println("Please report this incident on GitHub page.");
            System.exit(1);
        }
    }

    /**
     * Allows to know kernel's frame rate goal currently. <br>
     * Default : 60. Kernel will try to process itself and all others engines 60 times in one second.
     *
     * @return current frame rate
     */
    public int getFrameRate() {
        return frameRate;
    }

    /**
     * To define a frame rate that kernel will be sync on <br>
     * Default : 60. Kernel will try to process itself and all others engines 60 times in one second.
     *
     * @param frameRate new engine frame rate
     */
    public void setFrameRate(int frameRate) {
        this.frameRate = frameRate;
    }

    /**
     * Private method to get others engines. Used while looping on all others engines.
     *
     * @param engine engine ID to get
     * @return Engine object
     * @throws UnknownEngineException if wanted engine doesn't exist (or not implemented yet)
     */
    private Engine getEngine(Engines engine) throws UnknownEngineException {
        return switch (engine) {
            case EVENT_ENGINE -> eventEngine;
            case GRAPHICS_ENGINE -> graphicsEngine;
            case IA_ENGINE -> iaEngine;
            case INPUT_ENGINE -> inputEngine;
            case NETWORK_ENGINE -> networkEngine;
            case PHYSICS_ENGINE -> physicsEngine;
            case SOUND_ENGINE -> soundEngine;
            default -> throw new UnknownEngineException(engine);
        };
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
     * Remove given name map to the kernel. If current map is the map to remove, map will be unloaded
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
     * Clear all maps from the kernel. If current map is running, it will be unloaded
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
     * Load a previous added map on the kernel by its name
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
     * @param event triggered when key is pressed
     */
    public void setInput(Key key, Event event) {
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
     * @param hudElement the element to add
     */
    public synchronized void addHUDElement(HUDElement hudElement) {
        hudElements.add(hudElement);
        graphicsEngine.addElement(hudElement);
    }

    /**
     * Remove a static element corresponing to HUD previously added on the interface
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
     * @param map the map to load
     */
    private void loadMap(Map map) {
        if (currentMap != null)
            unloadMap();

        int width = map.getWidth(),
                height = map.getHeight();

        List<Case> mapCases = map.getCases();
        List<Entity> mapEntities = map.getEntities();
        List<Event> mapEvents = map.getEvents();

        physicsEngine.setMap(map);
        graphicsEngine.setCases(mapCases, width, height);

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
        List<Case> mapCases = currentMap.getCases();
        List<Entity> mapEntities = currentMap.getEntities();
        List<Event> mapEvents = currentMap.getEvents();

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
