package fr.r1r0r0.deltaengine.model.sprites;

import javafx.scene.Node;

/**
 * TODO
 */
public interface Sprite{
    /**
     * TODO
     * @return
     */
    Node getNode();

    /**
     * TODO
     * @param width
     * @param height
     */
    void resize(double width, double height);

    default void setLayout(Double x, Double y){
        this.getNode().setLayoutX(x);
        this.getNode().setLayoutY(y);
    }

    /**
     * TODO
     * @param z
     */
    default void setZOrder(double z){
        this.getNode().setViewOrder(z);
    };

    /**
     * TODO
     * @return
     */
    default double getZOrder(){
        return this.getNode().getViewOrder();
    }
}
