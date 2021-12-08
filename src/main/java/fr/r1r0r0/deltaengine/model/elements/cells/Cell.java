package fr.r1r0r0.deltaengine.model.elements.cells;

import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.Dimension;
import fr.r1r0r0.deltaengine.model.elements.Element;
import fr.r1r0r0.deltaengine.model.elements.entity.Entity;
import fr.r1r0r0.deltaengine.model.sprites.Sprite;

/**
 * A Cell in a grid. Atomic element of a MapLevel, used to render background textures.
 */
public abstract class Cell implements Element<Integer> {

    private final static Dimension dimension = new Dimension(1, 1);
    private Coordinates<Integer> coords;
    private Sprite sprite;
    private String name;

    /**
     * Default constructor. Set a case sprite and coordinates in the map.
     *
     * @param x      the abscissa coordinate of the cell in the map. Coordinates values are integers.
     * @param y      the ordinate coordinate of the cell in the map. Coordinates values are integers.
     * @param sprite the sprite to apply on the case
     */
    public Cell(int x, int y, Sprite sprite) {
        this.coords = new Coordinates<>(x, y);
        this.sprite = sprite;
        name = String.format("Cell(%s;%s)", coords.getX(), coords.getY());
        sprite.setZOrder(1000);
    }


    @Override
    public final Dimension getDimension() {
        return dimension;
    }

    @Override
    public void setDimension(Dimension dimension) {
        throw new IllegalCallerException("Cannot resize a Cell");
    }

    @Override
    public Sprite getSprite() {
        return sprite;
    }

    @Override
    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    @Override
    public Coordinates<Integer> getCoordinates() {
        return coords;
    }

    @Override
    public void setCoordinates(Coordinates<Integer> coordinates) {
        this.coords = coordinates;
    }

    @Override
    public String getName() {
        return name;
    }

    /**
     * Checks if given entity can cross this cell. If entity cannot cross cell, then it will be blocked.
     *
     * @param entity entity to check if he can cross
     * @return boolean true if given entity can cross the cell, false otherwise.
     */
    public abstract boolean isCrossableBy(Entity entity);
}
