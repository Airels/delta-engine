package fr.r1r0r0.deltaengine.model.sprites;

import javafx.scene.Node;

public interface Sprite{
    Node getNode();
    void resize(double width, double height);

    default void setZOrder(double z){
        this.getNode().setViewOrder(z);
    };
    default double getZOrder(){
        return this.getNode().getViewOrder();
    }
}
