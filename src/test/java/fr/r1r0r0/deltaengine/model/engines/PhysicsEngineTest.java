package fr.r1r0r0.deltaengine.model.engines;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import fr.r1r0r0.deltaengine.exceptions.maplevel.MapLevelBuilderCellCoordinatesStackingException;
import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.Dimension;
import fr.r1r0r0.deltaengine.model.Direction;
import fr.r1r0r0.deltaengine.model.elements.cells.UncrossableCell;
import fr.r1r0r0.deltaengine.model.elements.entity.Entity;
import fr.r1r0r0.deltaengine.model.maplevel.MapLevel;
import fr.r1r0r0.deltaengine.model.maplevel.MapLevelBuilder;
import fr.r1r0r0.deltaengine.model.sprites.InvisibleSprite;

class PhysicsEngineTest {

    PhysicsEngine physicsEngine = new PhysicsEngine();

    @Test
    void isAvailableDirection() throws MapLevelBuilderCellCoordinatesStackingException {
        MapLevel map;
        MapLevelBuilder builder = new MapLevelBuilder("Map",10,10);
        UncrossableCell cell = new UncrossableCell(8,8,InvisibleSprite.getInstance());
        builder.setCell(cell);
        map = builder.build();
        physicsEngine.setMap(map);
        physicsEngine.setMaxRunDelta(60);
        Coordinates<Double> pukCoords1 = new Coordinates<>(2.0,2.0);

        Dimension pukDim = new Dimension(0.99,0.99);
        Entity entity = new Entity("Puk", pukCoords1,InvisibleSprite.getInstance(),pukDim);

        Coordinates<Double> topleft = new Coordinates<>(0.0,0.0);
        entity.setCoordinates(topleft);
        entity.setSpeed(1);

        assertFalse(physicsEngine.isAvailableDirection(entity,Direction.UP));
        assertFalse(physicsEngine.isAvailableDirection(entity,Direction.LEFT));
        assertTrue(physicsEngine.isAvailableDirection(entity, Direction.DOWN));
        assertTrue(physicsEngine.isAvailableDirection(entity, Direction.RIGHT));

        entity.setSpeed(0); // should be all true as we have a speed of 0
        assertTrue(physicsEngine.isAvailableDirection(entity,Direction.UP));
        assertTrue(physicsEngine.isAvailableDirection(entity,Direction.LEFT));
        assertTrue(physicsEngine.isAvailableDirection(entity, Direction.DOWN));
        assertTrue(physicsEngine.isAvailableDirection(entity, Direction.RIGHT));

        Coordinates<Double> bottomRight = new Coordinates<>(9.0,9.0);
        entity.setCoordinates(bottomRight);
        entity.setSpeed(1);

        assertTrue(physicsEngine.isAvailableDirection(entity,Direction.UP));
        assertTrue(physicsEngine.isAvailableDirection(entity,Direction.LEFT));
        assertFalse(physicsEngine.isAvailableDirection(entity, Direction.DOWN));
        assertFalse(physicsEngine.isAvailableDirection(entity, Direction.RIGHT));

        Coordinates<Double> leftFromUncrossable = new Coordinates<>(7.0,8.0);
        entity.setCoordinates(leftFromUncrossable);

        assertFalse(physicsEngine.isAvailableDirection(entity,Direction.RIGHT));
        assertTrue(physicsEngine.isAvailableDirection(entity, Direction.LEFT));
        assertTrue(physicsEngine.isAvailableDirection(entity, Direction.DOWN));
        assertTrue(physicsEngine.isAvailableDirection(entity, Direction.UP));

        Coordinates<Double> rightFromUncrossable = new Coordinates<>(9.0,8.0);
        entity.setCoordinates(rightFromUncrossable);

        assertFalse(physicsEngine.isAvailableDirection(entity,Direction.LEFT));
        assertFalse(physicsEngine.isAvailableDirection(entity, Direction.RIGHT)); //limit of map
        assertTrue(physicsEngine.isAvailableDirection(entity, Direction.DOWN));
        assertTrue(physicsEngine.isAvailableDirection(entity, Direction.UP));

        Coordinates<Double> aboveUncrossable = new Coordinates<>(8.0,7.0);
        entity.setCoordinates(aboveUncrossable);

        assertFalse(physicsEngine.isAvailableDirection(entity,Direction.DOWN));
        assertTrue(physicsEngine.isAvailableDirection(entity, Direction.RIGHT));
        assertTrue(physicsEngine.isAvailableDirection(entity, Direction.LEFT));
        assertTrue(physicsEngine.isAvailableDirection(entity, Direction.UP));

        Coordinates<Double> underUncrossable = new Coordinates<>(8.0,9.0);
        entity.setCoordinates(underUncrossable);

        assertFalse(physicsEngine.isAvailableDirection(entity,Direction.UP));
        assertTrue(physicsEngine.isAvailableDirection(entity, Direction.RIGHT));
        assertTrue(physicsEngine.isAvailableDirection(entity, Direction.LEFT));
        assertFalse(physicsEngine.isAvailableDirection(entity, Direction.DOWN)); // out of map

    }

    @Test
    void canGoToNextCell() throws MapLevelBuilderCellCoordinatesStackingException {
        MapLevel map;
        MapLevelBuilder builder = new MapLevelBuilder("Map with walls",10,10);
        UncrossableCell cell = new UncrossableCell(8,8,InvisibleSprite.getInstance());
        builder.setCell(cell);
        map = builder.build();
        physicsEngine.setMap(map);
        physicsEngine.setMaxRunDelta(60);

        Coordinates<Double> pukCoords1 = new Coordinates<>(2.0,2.0);

        Dimension pukDim = new Dimension(0.99,0.99); //
        Entity entity = new Entity("Puk", pukCoords1,InvisibleSprite.getInstance(),pukDim);

        assertTrue(physicsEngine.canGoToNextCell(entity, Direction.LEFT));
        assertTrue(physicsEngine.canGoToNextCell(entity, Direction.RIGHT));
        assertTrue(physicsEngine.canGoToNextCell(entity, Direction.DOWN));
        assertTrue(physicsEngine.canGoToNextCell(entity, Direction.UP));

        Coordinates<Double> notInCell = new Coordinates<>(2.1,2.0);
        entity.setCoordinates(notInCell);
        assertFalse(physicsEngine.canGoToNextCell(entity, Direction.DOWN));
        assertFalse(physicsEngine.canGoToNextCell(entity, Direction.UP));
        assertFalse(physicsEngine.canGoToNextCell(entity, Direction.LEFT));
        assertFalse(physicsEngine.canGoToNextCell(entity, Direction.RIGHT));

        Coordinates<Double> topleft = new Coordinates<>(0.0,0.0);
        entity.setCoordinates(topleft);

        assertTrue(physicsEngine.canGoToNextCell(entity, Direction.DOWN));
        assertFalse(physicsEngine.canGoToNextCell(entity, Direction.UP));
        assertFalse(physicsEngine.canGoToNextCell(entity, Direction.LEFT));
        assertTrue(physicsEngine.canGoToNextCell(entity, Direction.RIGHT));

        Coordinates<Double> bottomRight = new Coordinates<>(9.0,9.0);
        entity.setCoordinates(bottomRight);

        assertTrue(physicsEngine.canGoToNextCell(entity, Direction.UP));
        assertFalse(physicsEngine.canGoToNextCell(entity, Direction.DOWN));
        assertFalse(physicsEngine.canGoToNextCell(entity, Direction.RIGHT));
        assertTrue(physicsEngine.canGoToNextCell(entity, Direction.LEFT));

        Coordinates<Double> leftFromUncrossable = new Coordinates<>(7.0,8.0);
        entity.setCoordinates(leftFromUncrossable);

        assertFalse(physicsEngine.canGoToNextCell(entity,Direction.RIGHT));
        assertTrue(physicsEngine.canGoToNextCell(entity, Direction.LEFT));
        assertTrue(physicsEngine.canGoToNextCell(entity, Direction.DOWN));
        assertTrue(physicsEngine.canGoToNextCell(entity, Direction.UP));

        Coordinates<Double> rightFromUncrossable = new Coordinates<>(9.0,8.0);
        entity.setCoordinates(rightFromUncrossable);

        assertFalse(physicsEngine.canGoToNextCell(entity,Direction.LEFT));
        assertFalse(physicsEngine.canGoToNextCell(entity, Direction.RIGHT)); //limit of map
        assertTrue(physicsEngine.canGoToNextCell(entity, Direction.DOWN));
        assertTrue(physicsEngine.canGoToNextCell(entity, Direction.UP));

        Coordinates<Double> aboveUncrossable = new Coordinates<>(8.0,7.0);
        entity.setCoordinates(aboveUncrossable);

        assertFalse(physicsEngine.canGoToNextCell(entity,Direction.DOWN));
        assertTrue(physicsEngine.canGoToNextCell(entity, Direction.RIGHT));
        assertTrue(physicsEngine.canGoToNextCell(entity, Direction.LEFT));
        assertTrue(physicsEngine.canGoToNextCell(entity, Direction.UP));

        Coordinates<Double> underUncrossable = new Coordinates<>(8.0,9.0);
        entity.setCoordinates(underUncrossable);

        assertFalse(physicsEngine.canGoToNextCell(entity,Direction.UP));
        assertTrue(physicsEngine.canGoToNextCell(entity, Direction.RIGHT));
        assertTrue(physicsEngine.canGoToNextCell(entity, Direction.LEFT));
        assertFalse(physicsEngine.canGoToNextCell(entity, Direction.DOWN)); // out of map
    }

}