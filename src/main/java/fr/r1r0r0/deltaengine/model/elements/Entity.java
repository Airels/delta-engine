package fr.r1r0r0.deltaengine.model.elements;

import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.Direction;
import fr.r1r0r0.deltaengine.model.IA;
import fr.r1r0r0.deltaengine.model.elements.Element;
import fr.r1r0r0.deltaengine.model.sprites.Sprite;

public class Entity implements Element {
    @Override
    public Sprite getSprite() {
        return null;
    }

    @Override
    public void setSprite(Sprite sprite) {

    }

    @Override
    public Coordinates getCoordinates() {
        return null;
    }

    @Override
    public void setCoordinates(Coordinates coordinates) {

    }

    @Override
    public String getName() {
        return null;
    }

    public Direction getDirection() {
        return null;
    }

    public void setDirection(Direction direction) {

    }

    public double getSpeed() {
        return 0;
    }

    public void setSpeed(double speed) {

    }

    public final void setIA(IA ia) {
        ia.setEntity(this);
    }

    public final IA getIA() {
        return null;
    }
}
