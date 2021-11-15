package fr.r1r0r0.deltaengine.model.engines;

import fr.r1r0r0.deltaengine.model.elements.*;
import fr.r1r0r0.deltaengine.model.maplevel.MapLevel;
import fr.r1r0r0.deltaengine.model.sprites.Sprite;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Graphic engine takes care of maintaining the view updated
 */
final class GraphicsEngine implements Engine {

    private ArrayList<Element> elements;
    private ArrayList<Element> mapElements;
    private Map<Element, Sprite> elementSpriteMap;
    private final int CASE_SIZE = 40;
    private MapLevel mapLevel;
    private Stage stage;
    private Group root;
    private Scene scene;
    private double z;

    private boolean initialized;
    private boolean started;

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
    public void init() {
        elementSpriteMap = new HashMap<>();
        elements = new ArrayList<>();
        mapElements = new ArrayList<>();
        mapLevel = null;
        z = 0.0;

        root = new Group();
        scene = new Scene(root, 10, 10);
        root.setVisible(true);

        stage.setScene(scene);
        stage.show();

        initialized = true;
    }

    /**
     * Graphic engine loop
     */
    @Override
    public void run() {
        if (started) throw new RuntimeException("Graphic Engine is already running");
        started = true;
        for (Element e : elements) {
            updateElement(e);
        }
        started = false;

    }

    /**
     * Update an element's graphical view
     *
     * @param e the element to be updated
     */
    private void updateElement(Element e) {
        if (!elements.contains(e)) throw new NoSuchElementException();

        Sprite oldSprite = elementSpriteMap.get(e);
        Sprite newSprite = e.getSprite();

        if (newSprite != oldSprite) {
            root.getChildren().remove(oldSprite.getNode());
            root.getChildren().add(newSprite.getNode());
            elementSpriteMap.put(e, newSprite);
        }

        newSprite.setLayout(e.getCoordinates().getX().doubleValue() * CASE_SIZE,
                e.getCoordinates().getY().doubleValue() * CASE_SIZE);
    }

    /**
     * Set the map to be diplayed, replace the old map
     * @param mapLevel map to be shown
     */
    public void setMap(MapLevel mapLevel) {

        for (Element e:this.mapLevel.getCells()) removeElement(e);
        for (Element e:this.mapLevel.getEntities()) removeElement(e);

        this.mapLevel = mapLevel;

        for (Cell c : mapLevel.getCells()) {
            c.getSprite().resize(CASE_SIZE, CASE_SIZE);
            addElement(c);
        }

        stage.setWidth(mapLevel.getWidth() * CASE_SIZE);
        stage.setHeight(mapLevel.getHeight() * CASE_SIZE);

        for (Entity entity : mapLevel.getEntities()) {
            entity.getSprite().resize(CASE_SIZE * entity.getDimension().getWidth(),
                    CASE_SIZE * entity.getDimension().getHeight());
            addElement(entity);
        }
    }

    /**
     * add an element to display
     *
     * @param element element to display
     */
    public void addElement(Element element) {
        if (!elements.contains(element)) {
            elementSpriteMap.put(element, element.getSprite());
            elements.add(element);
            root.getChildren().add(element.getSprite().getNode());
            if (element.getClass() == HUDElement.class) element.getSprite().getNode().setViewOrder(-1.0);
            updateElement(element);
        }
    }

    public void addMapElement(Element element){
        addElement(element);
        mapElements.add(element);
    }

    /**
     * remove an element from the graphic engine and from being displayed
     *
     * @param element an element from the graphic engine
     */
    public void removeElement(Element element) {
        elements.remove(element);
        root.getChildren().remove(element.getSprite().getNode());
    }

    /**
     * Empty the all element from the graphic engine and from the view
     */
    public void clearElements() {
        elements.clear();
        root.getChildren().clear();
    }

    public void setStage(Stage stage) {
        this.stage = stage;

    }
}
