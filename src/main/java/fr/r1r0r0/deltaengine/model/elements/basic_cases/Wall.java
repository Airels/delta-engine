package fr.r1r0r0.deltaengine.model.elements.basic_cases;

import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.elements.UncrossableCase;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * A default Wall case, just to represent simple default Wall.
 */
public class Wall extends UncrossableCase {

    public Wall(int x, int y) {
        super(x, y, () -> {
            Rectangle rectangle = new Rectangle();
            rectangle.setFill(Color.GRAY); // TODO set default wall sprite
            return rectangle;
        });
    }
}
