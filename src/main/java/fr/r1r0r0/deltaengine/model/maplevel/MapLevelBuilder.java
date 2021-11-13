package fr.r1r0r0.deltaengine.model.maplevel;

import fr.r1r0r0.deltaengine.model.Builder;
import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.elements.Cell;
import fr.r1r0r0.deltaengine.model.elements.CellBuilder;
import fr.r1r0r0.deltaengine.model.elements.basic_cases.VoidCell;

public class MapLevelBuilder implements Builder<MapLevel> {

    private static final CellBuilder VOID_CELL_BUILDER = new CellBuilder() {
        private int x;
        private int y;
        public CellBuilder setX (int x) {
            this.x = x;
            return this;
        }
        public CellBuilder setY (int y) {
            this.y = y;
            return this;
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
    private CellBuilder nullCellBuilder;

    /**
     * TODO
     * @param name
     * @param width
     * @param height
     */
    public MapLevelBuilder (String name, int width, int height) {
        if (width < 0) throw new IllegalArgumentException();
        if (height < 0) throw new IllegalArgumentException();
        this.name = name;
        this.width = width;
        this.height = height;
        cells = new Cell[width][height];
        nullCellBuilder = VOID_CELL_BUILDER;
    }

    /**
     * TODO
     * @param nullCellBuilder
     * @return
     */
    public Builder<MapLevel> setNullCellBuilder (CellBuilder nullCellBuilder) {
        this.nullCellBuilder = nullCellBuilder;
        return this;
    }

    /**
     * TODO
     * @param cell
     * @return
     */
    public Builder<MapLevel> setCell (Cell cell) {
        Coordinates<Integer> coordinates = cell.getCoordinates();
        cells[coordinates.getX()][coordinates.getY()] = cell;
        return this;
    }

    @Override
    public MapLevel build() {
        for (int x = 0 ; x < width ; x++) {
            nullCellBuilder.setX(x);
            for (int y = 0 ; y < height ; y++) {
                if (cells[x][y] == null) cells[x][y] = nullCellBuilder.setY(y).build();
            }
        }
        return new MapLevel(name,cells);
    }

}
