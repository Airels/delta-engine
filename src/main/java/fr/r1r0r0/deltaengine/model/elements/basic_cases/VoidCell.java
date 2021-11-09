package fr.r1r0r0.deltaengine.model.elements.basic_cases;

import fr.r1r0r0.deltaengine.model.elements.Cell;
import fr.r1r0r0.deltaengine.model.sprites.shapes.Rectangle;
import javafx.scene.paint.Color;

/**
 * A VoidCell black case. Just to represent nothing.
 */
public class VoidCell extends Cell {

    public VoidCell(int x, int y) {
        super(x, y, new Rectangle(Color.BLACK));
    }
}
