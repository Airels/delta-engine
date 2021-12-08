package fr.r1r0r0.deltaengine.model.sprites.shapes;

import fr.r1r0r0.deltaengine.model.sprites.Sprite;
import javafx.scene.Node;

/**
 * Shape Sprite
 * abstract class for closed shapes
 */
public abstract class Shape implements Sprite {

    /**
     * more often than not, Javafx is used for shapes
     * @return the javafx shape used if any
     */
    public abstract javafx.scene.shape.Shape getFXShape();

    @Override
    public final Node getNode() {
        return getFXShape();
    }
}
