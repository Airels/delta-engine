package fr.r1r0r0.deltaengine.model.sprites;

import javafx.scene.Node;

public interface Sprite{
    Node getNode();

    void setZOrder(int z);
    int getZOrder();
}
