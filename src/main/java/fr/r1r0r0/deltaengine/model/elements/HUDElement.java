package fr.r1r0r0.deltaengine.model.elements;

import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.Dimension;
import fr.r1r0r0.deltaengine.model.elements.Element;
import fr.r1r0r0.deltaengine.model.sprites.Sprite;

/**
 * A graphical element for user. Can be used for HP bar or score text for example.
 * HUDElement is completely detached from current level or anything else
 */
public class HUDElement implements Element<Double> {

    private Sprite sprite;
    private Coordinates<Double> coordinates;
    private String name;
    private Dimension dimension;

    /**
     * Default constructor.
     * @param name String representing the name of the Element
     * @param coordinates Coordinates of the element
     * @param sprite Sprite of the element
     * @param dimension Dimension of the element
     */
    public HUDElement(String name, Coordinates<Double> coordinates, Sprite sprite, Dimension dimension) {
        this.name = name;
        this.coordinates = coordinates;
        this.sprite = sprite;
        this.dimension = dimension;
        sprite.setZOrder(0);
    }

    @Override
    public Dimension getDimension() {
        return dimension;
    }

    @Override
    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
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
    public Coordinates<Double> getCoordinates() {
        return coordinates;
    }

    @Override
    public void setCoordinates(Coordinates<Double> coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public String getName() {
        return name;
    }
}
