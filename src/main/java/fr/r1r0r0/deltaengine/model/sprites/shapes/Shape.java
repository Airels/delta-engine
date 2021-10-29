package fr.r1r0r0.deltaengine.model.sprites.shapes;

import fr.r1r0r0.deltaengine.model.sprites.Sprite;
import javafx.scene.Node;

public abstract class Shape implements Sprite {

    public abstract javafx.scene.shape.Shape getFXShape();

    @Override
    public final Node getNode() {
        return getFXShape();
    }
}
