package fr.r1r0r0.deltaengine.model.elements.basic_cases;

import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.elements.UncrossableCase;
import fr.r1r0r0.deltaengine.model.sprites.shapes.Rectangle;
import javafx.scene.paint.Color;

/**
 * A default Wall case, just to represent simple default Wall.
 */
public class Wall extends UncrossableCase {

    public Wall(int x, int y) {
        super(x, y, new Rectangle(Color.GRAY));
    }
}
