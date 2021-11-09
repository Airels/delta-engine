package fr.r1r0r0.deltaengine.model.sprites;

import javafx.scene.Node;
import javafx.scene.shape.Rectangle;

/**
 * TODO
 */
public final class InvisibleSprite implements Sprite {

    /**
     * TODO
     */
    public final static InvisibleSprite INSTANCE = new InvisibleSprite();

    private final Node node;

    /**
     * Cannot be instanced
     */
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

    /**
     * TODO
     * @return
     */
    public static InvisibleSprite getInstance() {
        return INSTANCE;
    }
}
