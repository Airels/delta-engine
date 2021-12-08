package fr.r1r0r0.deltaengine.model.elements.cells.default_cells;

import fr.r1r0r0.deltaengine.model.elements.cells.UncrossableCell;
import fr.r1r0r0.deltaengine.model.sprites.shapes.Rectangle;
import fr.r1r0r0.deltaengine.view.colors.Color;

/**
 * A default Wall case, just to represent simple default Wall.
 */
public final class Wall extends UncrossableCell {

    public final static Color DEFAULT_WALL_COLOR = Color.GRAY;

    public Wall(int x, int y) {
        super(x, y, new Rectangle(DEFAULT_WALL_COLOR));
    }

}
