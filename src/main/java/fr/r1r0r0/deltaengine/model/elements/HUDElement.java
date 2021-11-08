package fr.r1r0r0.deltaengine.model.elements;

import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.Dimension;
import fr.r1r0r0.deltaengine.model.elements.Element;
import fr.r1r0r0.deltaengine.model.sprites.Sprite;

/**
 * A graphical element for user. Can be used for HP bar or score text for example.
 * HUDElement is completely detached from current level or anything else
 */
public class HUDElement implements Element {

    private Sprite sprite;
    private Coordinates coordinates;
    private String name;
    private Dimension dimension;

    public HUDElement(String name, Coordinates coordinates, Sprite sprite, Dimension dimension) {
        this.name = name;
        this.coordinates = coordinates;
        this.sprite = sprite;
        this.dimension = dimension;
    }

    @Override
    public Dimension getDimension() {
        return dimension;
    }

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
    public Coordinates getCoordinates() {
        return coordinates;
    }

    @Override
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public String getName() {
        return name;
    }
}
