package fr.r1r0r0.deltaengine.model.engines;

import fr.r1r0r0.deltaengine.model.elements.*;
import fr.r1r0r0.deltaengine.model.elements.cells.Cell;
import fr.r1r0r0.deltaengine.model.elements.entity.Entity;
import fr.r1r0r0.deltaengine.model.maplevel.MapLevel;
import fr.r1r0r0.deltaengine.model.sprites.Sprite;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Graphic engine takes care of maintaining the view updated
 */
final class GraphicsEngine implements Engine {

    private ConcurrentLinkedDeque<Element> elements;
    private Map<Element, Sprite> elementSpriteMap;
    private double caseSize = 40;
    private int offsetX = 0, offsetY = 0;
    private MapLevel mapLevel;
    private Stage stage;
    private Pane root;
    private Scene scene;
    private boolean initialized;
    private boolean started;

    private double widthMargin, heightMargin;

    /**
     * Constructor to instantiate the engine but not initialising its components
     */
    public GraphicsEngine() {
        initialized = false;
        started = false;
    }

    /**
     * initialising the graphic engine components
     */
    @Override
    public synchronized void init() {
        elementSpriteMap = new HashMap<>();
        elements = new ConcurrentLinkedDeque<>();
        mapLevel = null;

        int initialWidth = 1, initialHeight = 1;

        root = new Pane();
        scene = new Scene(root, initialWidth, initialHeight);
        root.setVisible(true);
        root.setStyle("-fx-background-color: #000000");

        stage.setScene(scene);
        stage.setResizable(false);
        stage.setFullScreenExitHint("");
        try {
            setStageIcon(new Image(getClass().getResourceAsStream("/icon.png")));
        }catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);

        stage.show();

        widthMargin = stage.getWidth() - initialWidth-1;
        heightMargin = stage.getHeight() - initialHeight-1;

        stage.setFullScreen(true);

        initialized = true;
    }

    /**
     * Graphic engine loop
     */
    @Override
    public synchronized void run() {
        if (started) throw new RuntimeException("Graphic Engine is already running");
        started = true;

        if (mapLevel != null){
            for (Cell c:mapLevel.getBufferedCells().getAndResetAddedElements())
                addElement(c);
            for (Cell c:mapLevel.getBufferedCells().getAndResetRemovedElements())
                removeElement(c);

            for (Element e:mapLevel.getBufferedEntities().getAndResetAddedElements())
                addElement(e);
            for (Element e:mapLevel.getBufferedEntities().getAndResetRemovedElements())
                removeElement(e);
        }

        for (Element e : elements) {
            updateElement(e);
        }
        started = false;
    }

    /**
     * Update an element's graphical view
     * if the sprite of the element is a new object,
     * the old sprite is removed and the new one added.
     * @param e the element to be updated
     */
    private synchronized void updateElement(Element e) {
        if (!elements.contains(e)) throw new NoSuchElementException();

        Sprite oldSprite = elementSpriteMap.get(e);
        Sprite newSprite = e.getSprite();

        if (newSprite != oldSprite) {
            root.getChildren().remove(oldSprite.getNode());
            root.getChildren().add(newSprite.getNode());
            elementSpriteMap.put(e, newSprite);

            newSprite.resize(
                    caseSize * e.getDimension().getWidth(),
                    caseSize * e.getDimension().getHeight());
        }

        newSprite.setLayout(offsetX + e.getCoordinates().getX().doubleValue() * caseSize,
                offsetY + e.getCoordinates().getY().doubleValue() * caseSize);
    }

    /**
     * Set the map to be diplayed, replace the old map
     * @param mapLevel map to be shown
     */
    public synchronized void setMap(MapLevel mapLevel) {
        if (this.mapLevel != null)
            clearMap();

        this.mapLevel = mapLevel;
        fitMapToStage();

        Collection<Cell> addedCells = mapLevel.getBufferedCells().getAndResetAddedElements();
        Collection<Cell> removedCells = mapLevel.getBufferedCells().getAndResetRemovedElements();
        Collection<Entity> addedEntities = mapLevel.getBufferedEntities().getAndResetAddedElements();
        Collection<Entity> removedEntities = mapLevel.getBufferedEntities().getAndResetRemovedElements();

        if (addedCells.size() > 0) {
            for (Cell c : addedCells)
                addElement(c);
        } else {
            for (Cell c : mapLevel.getCells())
                addElement(c);
        }

        if (addedEntities.size() > 0) {
            for (Entity e : addedEntities)
                addElement(e);
        } else {
            for (Entity e : mapLevel.getEntities())
                addElement(e);
        }

        for (Cell c : removedCells)
            removeElement(c);
        for (Entity e : removedEntities)
            removeElement(e);
    }

    /**
     * fit the map to the screen,
     * caseSize is calculated to fit exactly,
     * the offset are calculated to center the elements
     */
    private synchronized void fitMapToStage() {
        double caseSizeWidth = stage.getWidth() / mapLevel.getWidth();
        double caseSizeHeight = stage.getHeight() / mapLevel.getHeight();
        caseSize = Math.min(caseSizeWidth, caseSizeHeight);

        offsetX = 0;
        offsetY = 0;

        if (caseSizeHeight > caseSizeWidth){
            offsetY = (int) ((stage.getHeight() - mapLevel.getHeight()*caseSize)/2 - heightMargin);
        }
        else {
            offsetX = (int) ((stage.getWidth()- mapLevel.getWidth()*caseSize)/2 - widthMargin);
        }

        stage.setTitle(mapLevel.getName());
    }

    /**
     * add an element to display
     * element is resized according to CASE_SIZE
     * @param element element to display
     */
    public synchronized void addElement(Element element) {
        if (elements.contains(element)) {
            removeElement(element);
        }
        elementSpriteMap.put(element, element.getSprite());
        elements.add(element);
        root.getChildren().add(element.getSprite().getNode());
        element.getSprite().resize(
                    caseSize * element.getDimension().getWidth(),
                    caseSize * element.getDimension().getHeight());
        updateElement(element);
    }

    /**
     * remove an element from the graphic engine and from being displayed
     *
     * @param element an element from the graphic engine
     */
    public synchronized void removeElement(Element element) {
        elements.remove(element);

        Sprite s;
        if ((s = elementSpriteMap.remove(element)) == null)
            s = element.getSprite();

        root.getChildren().remove(s.getNode());
    }

    /**
     * Remove all the current map's elements
     */
    public synchronized void clearMap() {
        for (Element e : this.mapLevel.getCells()){
            removeElement(e);
        }
        for (Element e : this.mapLevel.getEntities()){
            removeElement(e);
        }

        this.mapLevel = null;
    }

    /**
     * add an element to display
     * element is NOT resized
     * @param element element to display
     */
    public synchronized void addHudElement(Element element){
        if (elements.contains(element)) {
            removeElement(element);
        }
        elementSpriteMap.put(element, element.getSprite());
        elements.add(element);
        root.getChildren().add(element.getSprite().getNode());
        updateElement(element);
    }

    /**
     * Empty the all element from the graphic engine and from the view
     */
    public synchronized void clearElements() {
        elements.clear();
        elementSpriteMap.clear();
        root.getChildren().clear();
    }

    /**
     * set the icon used for the engine's
     * @param image icon used
     */
    public synchronized void setStageIcon(Image image){
        stage.getIcons().clear();
        stage.getIcons().add(image);
    }

    public synchronized void setStage(Stage stage) {
        this.stage = stage;
    }
}
