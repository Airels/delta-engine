package fr.r1r0r0.deltaengine.model.engines;

import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.Map;
import fr.r1r0r0.deltaengine.model.elements.*;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * Graphic engine takes care of maintaining the view updated
 */
final class GraphicsEngine implements Engine {

    private ArrayList<Element> elements;
    private final int CASE_SIZE = 40;
    private Stage stage;
    private Group root;
    private Scene scene;
    private double z;

    private boolean initialized;
    private boolean started;

    /**
     * Constructor to instantiate the engine but not initialising its components
     */
    public GraphicsEngine(){
        initialized = false;
        started = false;
    }

    /**
     * initialising the graphic engine components
     */
    @Override
    public void init() {
        elements = new ArrayList<>();
        z = 0.0;

        root = new Group();
        scene = new Scene(root,10,10);
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
        for (Element e : elements){
            updateElement(e);
        }
        started = false;

    }

    /**
     * Update an element's graphical view
     * @param e the element to be updated
     */
    private void updateElement(Element e){
        if(!elements.contains(e)) throw new NoSuchElementException();

        Node n = e.getSprite().getNode();
        e.getSprite().getNode().setLayoutX(e.getCoordinates().getX()*CASE_SIZE);
        e.getSprite().getNode().setLayoutY(e.getCoordinates().getY()*CASE_SIZE);

    }

    public void setMap(Map map) {
        // setCases(map.getCases(), map.getWidth(), map.getHeight());

        for (int i = 0; i < map.getWidth(); i++) {
            for (int j = 0; j < map.getHeight(); j++) {
                Case c = map.getCase(i, j);

                c.getSprite().resize(CASE_SIZE, CASE_SIZE);

                addElement(c);
            }
        }

        stage.setWidth(map.getWidth()*CASE_SIZE);
        stage.setHeight(map.getHeight()*CASE_SIZE);

        for (Entity entity : map.getEntities()) {
            entity.getSprite().resize(CASE_SIZE*entity.getDimension().getWidth(),
                    CASE_SIZE*entity.getDimension().getHeight());
            addElement(entity);
        }
    }

    /**
     * add an element to display
     * @param element element to display
     */
    public void addElement(Element element) {
        if (!elements.contains(element)){
            elements.add(element);
            root.getChildren().add(element.getSprite().getNode());
            if (element.getClass() == HUDElement.class) element.getSprite().getNode().setViewOrder(-1.0);
            updateElement(element);
        }
    }

    /**
     * remove an element from the graphic engine and from being displayed
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
