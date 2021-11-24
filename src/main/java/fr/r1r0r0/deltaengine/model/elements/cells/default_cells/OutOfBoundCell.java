package fr.r1r0r0.deltaengine.model.elements.cells.default_cells;

import fr.r1r0r0.deltaengine.model.elements.cells.UncrossableCell;
import fr.r1r0r0.deltaengine.model.sprites.shapes.Rectangle;
import fr.r1r0r0.deltaengine.view.colors.Color;

/**
 * A cell to represents out of bounds cells. Used to avoid null values.
 */
public class OutOfBoundCell extends UncrossableCell {

    public OutOfBoundCell (int x, int y) {
        super(x, y, new Rectangle(Color.BLACK));
    }

}
