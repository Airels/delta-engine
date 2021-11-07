package fr.r1r0r0.deltaengine.model.engines;

import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.elements.Case;
import fr.r1r0r0.deltaengine.model.elements.Element;
import fr.r1r0r0.deltaengine.model.elements.Entity;
import fr.r1r0r0.deltaengine.model.sprites.Sprite;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

class GraphicsEngine implements Engine {

    private ArrayList<Element> elements;
    private Stage stage;
    private Pane pane;
    private Scene scene;
    private double z;

    @Override
    public void init() {
        elements = new ArrayList<>();
        z = 0.0;

        pane = new Pane();
        scene = new Scene(pane,600,600);
        pane.setVisible(true);

        stage = new Stage();
        stage.setScene(scene);
    }

    @Override
    public void run() {
        for (Element e:elements){
            updateElement(e);
        }

    }

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

    public void setCases(List<Case> cases, int width, int height) {
        stage.setWidth(width);
        stage.setHeight(height);
    }

    public void addElement(Element element) {
        if (!elements.contains(element)){
            elements.add(element);
            pane.getChildren().add(element.getSprite().getNode());
        }
    }

    public void removeElement(Element element) {
        elements.remove(element);
        pane.getChildren().remove(element.getSprite().getNode());

    }

    public void clearElements() {
        elements.clear();
        pane.getChildren().clear();

    }
}
