package fr.r1r0r0.deltaengine.model.elements.builders;

import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.elements.builders.ElementBuilder;
import fr.r1r0r0.deltaengine.model.elements.cells.Cell;
import fr.r1r0r0.deltaengine.model.sprites.Sprite;

public class CellBuilder implements ElementBuilder {
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

    public Cell build() {
        return null;
    }
}