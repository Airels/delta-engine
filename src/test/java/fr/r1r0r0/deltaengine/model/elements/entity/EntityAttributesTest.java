package fr.r1r0r0.deltaengine.model.elements.entity;

import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.Dimension;
import fr.r1r0r0.deltaengine.model.Direction;
import fr.r1r0r0.deltaengine.model.sprites.Sprite;
import fr.r1r0r0.deltaengine.model.sprites.shapes.Circle;
import fr.r1r0r0.deltaengine.view.colors.Color;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicReference;

class EntityAttributesTest {

    private static Entity entity;
    private static EntityAttributes attributes;

    @BeforeAll
    static void prepare() {
        entity = new Entity("test", new Coordinates<>(0.0, 0.0), new Circle(Color.RED), Dimension.DEFAULT_DIMENSION);
        attributes = entity.getAttributes();
    }

    @Test
    void coordinates() {
        Coordinates<Double> initial = entity.getCoordinates();
        Coordinates<Double> updated = new Coordinates<>(1.0, 1.0);

        AtomicReference<Coordinates<Double>> before = new AtomicReference<>();
        AtomicReference<Coordinates<Double>> after = new AtomicReference<>();
        attributes.addCoordinatesListener((oldValue, newValue) -> {
            before.set(oldValue);
            after.set(newValue);
        });

        entity.setCoordinates(updated);

        Assertions.assertEquals(initial, before.get());
        Assertions.assertEquals(updated, after.get());
    }

    @Test
    void sprite() {
        Sprite initial = entity.getSprite();
        Sprite updated = new Circle(Color.GREEN);

        AtomicReference<Sprite> before = new AtomicReference<>();
        AtomicReference<Sprite> after = new AtomicReference<>();
        attributes.addSpriteListener((oldValue, newValue) -> {
            before.set(oldValue);
            after.set(newValue);
        });

        entity.setSprite(updated);

        Assertions.assertEquals(initial, before.get());
        Assertions.assertEquals(updated, after.get());
    }

    @Test
    void dimension() {
        Dimension initial = entity.getDimension();
        Dimension updated = new Dimension(0, 0);

        AtomicReference<Dimension> before = new AtomicReference<>();
        AtomicReference<Dimension> after = new AtomicReference<>();
        attributes.addDimensionListener((oldValue, newValue) -> {
            before.set(oldValue);
            after.set(newValue);
        });

        entity.setDimension(updated);

        Assertions.assertEquals(initial, before.get());
        Assertions.assertEquals(updated, after.get());
    }

    @Test
    void direction() {
        Direction initial = entity.getDirection();
        Direction updated = Direction.UP;

        AtomicReference<Direction> before = new AtomicReference<>();
        AtomicReference<Direction> after = new AtomicReference<>();
        attributes.addDirectionListener((oldValue, newValue) -> {
            before.set(oldValue);
            after.set(newValue);
        });

        entity.setDirection(updated);

        Assertions.assertEquals(initial, before.get());
        Assertions.assertEquals(updated, after.get());
    }

    @Test
    void speed() {
        double initial = entity.getSpeed();
        double updated = 5.0;

        AtomicReference<Double> before = new AtomicReference<>();
        AtomicReference<Double> after = new AtomicReference<>();
        attributes.addSpeedListener((oldValue, newValue) -> {
            before.set(oldValue);
            after.set(newValue);
        });

        entity.setSpeed(updated);

        Assertions.assertEquals(initial, before.get());
        Assertions.assertEquals(updated, after.get());
    }
}