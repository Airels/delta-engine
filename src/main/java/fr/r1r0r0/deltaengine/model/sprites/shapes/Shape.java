package fr.r1r0r0.deltaengine.model.sprites.shapes;

import fr.r1r0r0.deltaengine.model.sprites.Sprite;
import javafx.scene.Node;

/**
 * TODO
 */
public abstract class Shape implements Sprite {

    /**
     * TODO
     * @return
     */
    public abstract javafx.scene.shape.Shape getFXShape();

    @Override
    public final Node getNode() {
        return getFXShape();
    }
}
