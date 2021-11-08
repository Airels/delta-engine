package fr.r1r0r0.deltaengine.model.sprites;

import javafx.scene.Node;

public interface Sprite{
    Node getNode();

    default void setZOrder(double z){
        this.getNode().setViewOrder(z);
    };
    default double getZOrder(){
        return this.getNode().getViewOrder();
    }
}
