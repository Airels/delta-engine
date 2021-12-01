package fr.r1r0r0.deltaengine.model.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.Dimension;

class CollisionPositionsTest {


    @Test
    void isInHitBoxTest() {
        Coordinates<Double> entity = new Coordinates<>(1.0, 1.0);
        Dimension entityDim1 = new Dimension(1, 1);

        // in collision with itself
        assertTrue(CollisionPositions.isInHitBox(entity, entityDim1, entity));

        Coordinates<Double> collPoint1 = new Coordinates<>(3.0, 3.0);
        Coordinates<Double> collPoint2 = new Coordinates<>(2.0, 2.0);


        Dimension wallDim = new Dimension(1, 1);

        assertFalse(CollisionPositions.isInHitBox(entity, entityDim1, collPoint1));
        assertTrue(CollisionPositions.isInHitBox(entity, entityDim1, collPoint2));

        Dimension entityDim05 = new Dimension(0.5, 0.5);
        Coordinates<Double> collPoint3 = new Coordinates<>(1.5, 1.5);
        Coordinates<Double> collPoint4 = new Coordinates<>(1.51, 1.51);

        assertFalse(CollisionPositions.isInHitBox(entity, entityDim05, collPoint2));
        assertTrue(CollisionPositions.isInHitBox(entity, entityDim05, collPoint3));
        assertFalse(CollisionPositions.isInHitBox(entity, entityDim05, collPoint4));


        CollisionPositions[] POSITIONS_CHECK = new CollisionPositions[]{
                CollisionPositions.LEFT_TOP, CollisionPositions.RIGHT_TOP,
                CollisionPositions.LEFT_BOT, CollisionPositions.RIGHT_BOT};
        double margin = 0.2;


        Coordinates<Double> position =
                POSITIONS_CHECK[0].calcPosition(collPoint3, wallDim);
        assertEquals(position.getX(),position.getY());
        assertTrue(CollisionPositions.isInHitBox(entity,entityDim1,position));

        position = POSITIONS_CHECK[1].calcPosition(collPoint3, wallDim);
        assertNotEquals(position.getX(),position.getY());
        assertFalse(CollisionPositions.isInHitBox(entity,entityDim1,position));


        // test with same entity but reduced by margin
        Coordinates<Double> position2 =
                POSITIONS_CHECK[0].calcPosition(entity, entityDim1);
        assertEquals(position2.getX(),position2.getY());
        assertTrue(CollisionPositions.isInHitBox(entity,entityDim1,position2));

        position2 = POSITIONS_CHECK[1].calcPosition(entity, entityDim1);
        assertNotEquals(position2.getX(),position2.getY());
        assertTrue(CollisionPositions.isInHitBox(entity,entityDim1,position2));

        position2 = POSITIONS_CHECK[2].calcPosition(entity, entityDim1);
        assertNotEquals(position2.getX(),position2.getY());
        assertTrue(CollisionPositions.isInHitBox(entity,entityDim1,position2));

        position2 = POSITIONS_CHECK[3].calcPosition(entity, entityDim1);
        assertEquals(position2.getX(),position2.getY());
        assertTrue(CollisionPositions.isInHitBox(entity,entityDim1,position2));


    }

}