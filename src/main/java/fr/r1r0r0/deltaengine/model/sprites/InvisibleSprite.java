package fr.r1r0r0.deltaengine.model.sprites;

import javafx.scene.Node;
import javafx.scene.shape.Rectangle;

public class InvisibleSprite implements Sprite {

    public final static InvisibleSprite instance = new InvisibleSprite();

    private final Node node;

    private InvisibleSprite() {
        node = new Rectangle();
        node.setVisible(false);
    }

    @Override
    public Node getNode() {
        return node;
    }

    @Override
    public void resize(double width, double height) {

    }
}
