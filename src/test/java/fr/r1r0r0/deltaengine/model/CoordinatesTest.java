package fr.r1r0r0.deltaengine.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CoordinatesTest {
    Coordinates<Integer> coords;
    Coordinates<Double> coordDouble;

    int xi = 1, yi = 2;
    double xd = 1.0, yd = 2.0;
    @BeforeEach
    void init(){
        coords = new Coordinates(xi, yi);
        coordDouble = new Coordinates<>(xd,yd);

    }
    @Test
    void getX() {
        assertEquals(coords.getX(), xi);;
        assertEquals(coordDouble.getX(),xd);


    }

    @Test
    void getY() {
        assertEquals(coords.getY(), yi);;
        assertEquals(coordDouble.getY(),yd);
    }

    @Test
    void getNextCoordinates() {
        Direction direct = Direction.IDLE;
        double delta = 1.2;
        Coordinates<Double>  nexts = coords.getNextCoordinates(direct,delta);
        double x = coords.getX() + delta * 0; // 0 due to IDLE Direction
        double y = coords.getY() + delta * 0; // 0 due to IDLE Direction

        assertEquals(nexts.getX(), x);
        assertEquals(nexts.getY(), y);
        assertEquals(nexts.getX(), coords.getX().doubleValue()); // should be equal bcs direction is IDLE
        assertEquals(nexts.getY(), coords.getY().doubleValue()); // should be equal bcs direction is IDLE

        direct = Direction.UP;
        delta = 1.2;
        nexts = coords.getNextCoordinates(direct,delta);
        x = coords.getX() + delta * 0;      // Direction.UP.getCoordinates.getX() = 0;
        y = coords.getY() + delta * (-1);   // Direction.UP.getCoordinates.getY() = -1;


        assertEquals(nexts.getX(), x);
        assertEquals(nexts.getY(), y);
        assertEquals(nexts.getX(),coords.getX().doubleValue()); // should be equal bcs direction is up so only y changes
        assertNotEquals(nexts.getY(),coords.getY().doubleValue());

        // Changing direction
        direct = Direction.LEFT;
        delta = 1.2;
        nexts = coords.getNextCoordinates(direct,delta);
        x = coords.getX() + delta * (-1); // Direction.LEFT.getCoordinates.getX() = -1;
        y = coords.getY() + delta * 0;    // Direction.LEFT.getCoordinates.getY() = 0;

        assertEquals(nexts.getX(), x);
        assertEquals(nexts.getY(), y);
        assertNotEquals(nexts.getX(),coords.getX().doubleValue());
        assertEquals(nexts.getY(),coords.getY().doubleValue()); // should be equal bcs direction is Left so only x changes

    }
}