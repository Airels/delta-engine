package engines.kernel;

import fr.r1r0r0.deltaengine.model.elements.cells.CrossableCell;
import fr.r1r0r0.deltaengine.model.engines.DeltaEngine;
import fr.r1r0r0.deltaengine.model.engines.KernelEngine;
import fr.r1r0r0.deltaengine.model.maplevel.MapLevel;
import fr.r1r0r0.deltaengine.model.maplevel.MapLevelBuilder;
import fr.r1r0r0.deltaengine.model.sprites.shapes.Rectangle;
import fr.r1r0r0.deltaengine.view.colors.Color;

public class CheckerBoard {
    public static void main(String[] args) throws Exception {
        KernelEngine engine = DeltaEngine.launch();

        MapLevelBuilder builder = new MapLevelBuilder("test", 10, 10);

        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                if ((x+y) % 2 == 0)
                    builder.setCell(new WhiteCell(x, y));
                else
                    builder.setCell(new BrownCell(x, y));
            }
        }

        MapLevel map = builder.build();
        engine.addMap(map);
        engine.setCurrentMap(map.getName());
    }
}

class WhiteCell extends CrossableCell {

    /**
     * Default constructor. Set a case sprite and coordinates in the map.
     *
     * @param x      the abscissa coordinate of the cell in the map. Coordinates values are integers.
     * @param y      the ordinate coordinate of the cell in the map. Coordinates values are integers.
     */
    public WhiteCell(int x, int y) {
        super(x, y, new Rectangle(Color.WHITE));
    }
}

class BrownCell extends CrossableCell {

    /**
     * Default constructor. Set a case sprite and coordinates in the map.
     *
     * @param x      the abscissa coordinate of the cell in the map. Coordinates values are integers.
     * @param y      the ordinate coordinate of the cell in the map. Coordinates values are integers.
     */
    public BrownCell(int x, int y) {
        super(x, y, new Rectangle(new Color(222, 184, 135)));
    }
}
