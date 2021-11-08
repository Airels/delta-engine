package fr.r1r0r0.deltaengine.model.engines;

import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.Map;
import fr.r1r0r0.deltaengine.model.elements.Case;
import fr.r1r0r0.deltaengine.model.elements.Element;
import fr.r1r0r0.deltaengine.model.elements.HUDElement;
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
        // setCases(map.getCases(), map.getWidth(), map.getHeight());

        for (int i = 0; i < map.getWidth(); i++) {
            for (int j = 0; j < map.getHeight(); j++) {
                Case c = map.getCase(i, j);
                if (!c.getSprite().getNode().isResizable())
                    //System.exit(69);

                // c.getSprite().getNode().resize(CASE_SIZE,CASE_SIZE)
                c.getSprite().getNode().setLayoutX(c.getCoordinates().getX()*CASE_SIZE);
                c.getSprite().getNode().setLayoutY(c.getCoordinates().getY()*CASE_SIZE);
                c.getSprite().getNode().minWidth(CASE_SIZE);
                c.getSprite().getNode().minHeight(CASE_SIZE);
                c.getSprite().getNode().maxWidth(CASE_SIZE);
                c.getSprite().getNode().maxHeight(CASE_SIZE);

                // System.out.println("c.getCoordinates().getX()*CASE_SIZE = " + c.getCoordinates().getX() * CASE_SIZE);
                System.out.println("c.getSprite().getNode() = " + c.getSprite().getNode());

                Rectangle r = ((Rectangle) c.getSprite().getNode());

                root.getChildren().add(c.getSprite().getNode());
                // addElement(c);
            }
        }

        stage.setWidth(map.getWidth()*CASE_SIZE);
        stage.setHeight(map.getHeight()*CASE_SIZE);
    }

    /**
     * set up the cases matrix
     * @param cases to set up
     * @param width is the number of case used in the map's width
     * @param height is the number of case used in the map's height
     */
    public void setCases(Collection<Case> cases, int width, int height) {
        for(Case c:cases){
            c.getSprite().getNode().resize(CASE_SIZE,CASE_SIZE);
            c.getSprite().getNode().setTranslateX(c.getCoordinates().getX()*CASE_SIZE);
            c.getSprite().getNode().setTranslateY(c.getCoordinates().getY()*CASE_SIZE);

            addElement(c);
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
            root.getChildren().add(element.getSprite().getNode());
            if (element.getClass() == HUDElement.class) element.getSprite().getNode().setViewOrder(-1.0);
            // updateElement(element);
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
