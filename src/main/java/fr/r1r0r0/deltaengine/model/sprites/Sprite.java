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
