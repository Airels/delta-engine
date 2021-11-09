package fr.r1r0r0.deltaengine.model.elements;

import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.Dimension;
import fr.r1r0r0.deltaengine.model.sprites.Sprite;
import javafx.geometry.Dimension2D;

/**
 * A graphical element. Could be a Case, an Entity or anything graphical.
 * Possess coordinates, a Sprite and a Name.
 */
public interface Element {

    Dimension getDimension();
    void setDimension (Dimension dimension);

    /**
     * Returns sprite of the element
     * @return Sprite of the element
     */
    Sprite getSprite();

    /**
     * Set sprite of an element
     * @param sprite sprite to set
     */
    void setSprite(Sprite sprite);

    /**
     * Coordinates of the element in the game.
     * @return Coordinates of the element
     */
    Coordinates getCoordinates();

    /**
     * Allows setting new coordinates for the element.
     * @param coordinates new coordinates
     */
    void setCoordinates(Coordinates coordinates);

    /**
     * Name getter of the element
     * @return String name of the element
     */
    String getName();
}
