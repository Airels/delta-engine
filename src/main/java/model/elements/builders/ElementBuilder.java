package model.elements.builders;

import model.Coordinates;
import model.elements.Element;
import model.sprites.Sprite;

public interface ElementBuilder {

    ElementBuilder setSprite(Sprite sprite);
    ElementBuilder setCoordinates(Coordinates coords);
    ElementBuilder setName(String name);
}
