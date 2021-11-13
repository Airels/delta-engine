package fr.r1r0r0.deltaengine.model.sprites;

import javafx.scene.Node;
import javafx.scene.shape.Rectangle;

/**
 * Invisible Sprite
 */
public final class InvisibleSprite implements Sprite {

    /**
     * used to get an instance Invisible Sprite
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
     * Get an instance Invisible Sprite
     * @return an instance Invisible Sprite
     */
    public static InvisibleSprite getInstance() {
        return INSTANCE;
    }
}
