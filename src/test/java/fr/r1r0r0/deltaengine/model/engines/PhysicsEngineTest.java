package fr.r1r0r0.deltaengine.model.engines;

import fr.r1r0r0.deltaengine.model.maplevel.MapLevel;
import fr.r1r0r0.deltaengine.model.maplevel.MapLevelBuilder;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PhysicsEngineTest {

    PhysicsEngine physicsEngine = new PhysicsEngine();

    @Test
    void isMovementAvailable() {
    }

    @Test
    void setMap() {
        MapLevel map = new MapLevelBuilder("Map",0,0).build();

    }

    @Test
    void clearMap() {
    }

}