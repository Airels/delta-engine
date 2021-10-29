package fr.r1r0r0.deltaengine.model.elements.basic_cases;

import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.elements.Case;
import fr.r1r0r0.deltaengine.model.sprites.Sprite;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * A Void black case. Just to represent nothing.
 */
public class Void extends Case {

    public Void(Coordinates coordinates) {
        super(coordinates, () -> {
            Rectangle rectangle = new Rectangle();
            rectangle.setFill(Color.BLACK); // TODO set default wall sprite
            return rectangle;
        });
    }
}
