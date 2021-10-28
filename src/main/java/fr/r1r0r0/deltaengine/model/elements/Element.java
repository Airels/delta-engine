package fr.r1r0r0.deltaengine.model.elements;

import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.sprites.Sprite;

public interface Element {

    Sprite getSprite();
    void setSprite(Sprite sprite);
    Coordinates getCoordinates();
    void setCoordinates(Coordinates coordinates);
    String getName();
}
