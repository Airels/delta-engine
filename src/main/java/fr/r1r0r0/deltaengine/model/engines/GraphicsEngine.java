package fr.r1r0r0.deltaengine.model.engines;

import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.Map;
import fr.r1r0r0.deltaengine.model.elements.Case;
import fr.r1r0r0.deltaengine.model.elements.Element;
import fr.r1r0r0.deltaengine.model.elements.Entity;
import fr.r1r0r0.deltaengine.model.elements.HUDElement;
import fr.r1r0r0.deltaengine.model.sprites.Sprite;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Graphic engine takes care of maintaining the view updated
 */
final class GraphicsEngine implements Engine {

    private ArrayList<Element> elements;
    private final int CASE_SIZE = 40;
    private Stage stage;
    private Pane pane;
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

        pane = new Pane();
        scene = new Scene(pane,600,600);
        pane.setVisible(true);

        stage = new Stage();
        stage.setScene(scene);

        initialized = true;
    }

    /**
     * Graphic engine loop
     */
    @Override
    public void run() {
        if (started)
            throw new RuntimeException("Graphic Engine is already running");
        started = true;
        for (Element e:elements){
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
        if (e.getSprite().getZOrder() >= z) {

            Coordinates c = e.getCoordinates();

            if (c.getX() != n.getLayoutX()) n.setLayoutX(c.getX());
            if (c.getY() != n.getLayoutY()) n.setLayoutY(c.getY());
            if (!n.isVisible()) n.setVisible(true);

        }
        else if(n.isVisible())n.setVisible(false);
    }

    public void setMap(Map map) {
        setCases(map.getCases(), map.getWidth(), map.getHeight());
    }

    /**
     * set up the cases matrix
     * @param cases to set up
     * @param width ?? TODO
     * @param height ??
     */
    public void setCases(List<Case> cases, int width, int height) {
        for (int i = 0; i < height ; i++){
            for (int j = 0; j < width; j++) {
                Case e = cases.get(i*width+j);
                e.getSprite().getNode().resize(CASE_SIZE,CASE_SIZE);
                e.getSprite().getNode().setLayoutX(i*CASE_SIZE);
                e.getSprite().getNode().setLayoutY(j*CASE_SIZE);
                addElement(e);
            }
        }
        stage.setWidth(width*CASE_SIZE);
        stage.setHeight(height*CASE_SIZE);
    }

    /**
     * add an element to display
     * @param element element to display
     */
    public void addElement(Element element) {
        if (!elements.contains(element)){
            elements.add(element);
            pane.getChildren().add(element.getSprite().getNode());
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
        pane.getChildren().remove(element.getSprite().getNode());
    }

    /**
     * Empty the all element from the graphic engine and from the view
     */
    public void clearElements() {
        elements.clear();
        pane.getChildren().clear();
    }
}
