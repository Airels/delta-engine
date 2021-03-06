package fr.r1r0r0.deltaengine.model.elements.cells.default_cells;

import fr.r1r0r0.deltaengine.model.elements.cells.CrossableCell;
import fr.r1r0r0.deltaengine.model.sprites.shapes.Rectangle;
import fr.r1r0r0.deltaengine.view.colors.Color;

/**
 * A VoidCell black case. Just to represent nothing.
 */
public final class VoidCell extends CrossableCell {

    public VoidCell(int x, int y) {
        super(x, y, new Rectangle(Color.BLACK));
    }

}
