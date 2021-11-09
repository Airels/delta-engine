package fr.r1r0r0.deltaengine.model.elements;

import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.Dimension;
import fr.r1r0r0.deltaengine.model.sprites.Sprite;
import javafx.geometry.Dimension2D;

/**
 * A Cell in a grid. Atomic element of a MapLevel, used to render background textures.
 */
public class Cell implements Element {

    private final static Dimension dimension = new Dimension(1, 1);
    private Coordinates coords;
    private Sprite sprite;
    private String name;


    /**
     * Default constructor. Set a case sprite and coordinates in the map.
     * @param x the coordinates of the map. Coordinates values are integers. If doubles, it will be truncated.
     * @param sprite the sprite to apply on the case
     */
    public Cell(int x, int y, Sprite sprite) {
        this.coords = new Coordinates(x, y);
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
    public Coordinates getCoordinates() {
        return coords;
    }

    @Override
    public void setCoordinates(Coordinates coordinates) {
        this.coords = coordinates;
    }

    @Override
    public String getName() {
        return name;
    }

    /**
     * If a Cell is crossable. Uncrossable case is not crossable, so this methods will return false. True for normal cases.
     * This method is mainly used by PhysicsEngine.
     * @return true if it's a simple case, false otherwise
     */
    boolean isCrossable() {
        return true;
    }
}
