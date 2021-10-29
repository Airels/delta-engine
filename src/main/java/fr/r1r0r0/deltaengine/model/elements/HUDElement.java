package fr.r1r0r0.deltaengine.model.elements;

import fr.r1r0r0.deltaengine.model.Coordinates;
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
