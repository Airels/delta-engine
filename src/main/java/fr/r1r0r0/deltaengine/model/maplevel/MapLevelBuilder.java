package fr.r1r0r0.deltaengine.model.maplevel;

import fr.r1r0r0.deltaengine.exceptions.maplevel.MapLevelBuilderCellCoordinatesStackingException;
import fr.r1r0r0.deltaengine.model.Builder;
import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.elements.cells.Cell;
import fr.r1r0r0.deltaengine.model.elements.cells.CellBuilder;
import fr.r1r0r0.deltaengine.model.elements.cells.default_cells.VoidCell;
import fr.r1r0r0.deltaengine.model.sprites.Sprite;

/**
 * A Builder of MapLevel
 * @see MapLevel
 * @see Builder
 * @see CellBuilder
 */
public final class MapLevelBuilder implements Builder<MapLevel> {

    /**
     * A CellBuilder of VoidCell
     */
    private static final CellBuilder VOID_CELL_BUILDER = new CellBuilder() {
        private int x;
        private int y;
        @Override
        public CellBuilder setX (int x) {
            this.x = x;
            return this;
        }
        @Override
        public CellBuilder setY (int y) {
            this.y = y;
            return this;
        }
        @Override
        public CellBuilder setSprite (Sprite sprite) {
            throw new RuntimeException("Proxy");
        }
        @Override
        public Cell build(int x, int y) {
            return new VoidCell(x,y);
        }
        @Override
        public Cell build() {
            return new VoidCell(x,y);
        }
    };

    private final String name;
    private final int width;
    private final int height;
    private final Cell[][] cells;
    private final CellBuilder defaultCellBuilder;

    /**
     * Constructor
     * @param name name of the map
     * @param width with of the map
     * @param height height of the map
     * @param defaultCellBuilder defaultCellBuilder for the cell not difined in the map
     */
    public MapLevelBuilder (String name, int width, int height, CellBuilder defaultCellBuilder) {
        if (width < 0) throw new IllegalArgumentException();
        if (height < 0) throw new IllegalArgumentException();
        this.name = name;
        this.width = width;
        this.height = height;
        cells = new Cell[width][height];
        this.defaultCellBuilder = defaultCellBuilder;
    }

    /**
     * Constructor
     * The attribute defaultCellBuilder is set to VOID_CELL_BUILDER
     * @param name name of the map
     * @param width with of the map
     * @param height height of the map
     */
    public MapLevelBuilder (String name, int width, int height) {
        this(name,width,height,VOID_CELL_BUILDER);
    }

    /**
     * Setter for the cell at the coordinate given by the cell coordinates
     * @param cell a cell
     * @return the current object
     * @throws MapLevelBuilderCellCoordinatesStackingException throw of the cell given replace an other cell
     *  with the same coordinates
     */
    public Builder<MapLevel> setCell (Cell cell) throws MapLevelBuilderCellCoordinatesStackingException {
        Coordinates<Integer> coordinates = cell.getCoordinates();
        Cell oldCell = cells[coordinates.getX()][coordinates.getY()];
        if (oldCell != null && oldCell != cell)
            throw new MapLevelBuilderCellCoordinatesStackingException(this,oldCell,cell);
        cells[coordinates.getX()][coordinates.getY()] = cell;
        return this;
    }

    @Override
    public MapLevel build() {
        for (int x = 0 ; x < width ; x++) {
            for (int y = 0 ; y < height ; y++) {
                if (cells[x][y] == null) cells[x][y] = defaultCellBuilder.build(x,y);
            }
        }
        return new MapLevel(name,cells);
    }

    @Override
    public String toString () {
        return name;
    }

}
