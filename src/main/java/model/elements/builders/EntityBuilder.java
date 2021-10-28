package model.elements.builders;

import model.Coordinates;
import model.elements.entities.Entity;
import model.sprites.Sprite;

public class EntityBuilder implements ElementBuilder {

    @Override
    public ElementBuilder setSprite(Sprite sprite) {
        return null;
    }

    @Override
    public ElementBuilder setCoordinates(Coordinates coords) {
        return null;
    }

    @Override
    public ElementBuilder setName(String name) {
        return null;
    }

    public Entity build() {
        return null;
    }
}
