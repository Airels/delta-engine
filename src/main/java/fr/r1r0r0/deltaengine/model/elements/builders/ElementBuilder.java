package fr.r1r0r0.deltaengine.model.elements.builders;

import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.sprites.Sprite;

public interface ElementBuilder {

    ElementBuilder setSprite(Sprite sprite);
    ElementBuilder setCoordinates(Coordinates coords);
    ElementBuilder setName(String name);
}
