package model.elements;

import model.Coordinates;
import model.sprites.Sprite;

public interface Element {

    Sprite getSprite();
    void setSprite(Sprite sprite);
    Coordinates getCoordinates();
    void setCoordinates(Coordinates coordinates);
    String getName();
}
