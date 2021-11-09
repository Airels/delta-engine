package engines.kernel;

import fr.r1r0r0.deltaengine.model.*;
import fr.r1r0r0.deltaengine.model.Dimension;
import fr.r1r0r0.deltaengine.model.elements.Entity;
import fr.r1r0r0.deltaengine.model.elements.basic_cases.Wall;
import fr.r1r0r0.deltaengine.model.engines.DeltaEngine;
import fr.r1r0r0.deltaengine.model.engines.KernelEngine;
import fr.r1r0r0.deltaengine.model.engines.utils.Key;
import fr.r1r0r0.deltaengine.model.events.Event;
import fr.r1r0r0.deltaengine.model.events.InputEvent;
import fr.r1r0r0.deltaengine.model.events.Trigger;
import fr.r1r0r0.deltaengine.model.sprites.shapes.Circle;
import fr.r1r0r0.deltaengine.model.sprites.shapes.Rectangle;
import javafx.scene.paint.Color;

import java.awt.*;

public class Main {

    public static void main(String[] args) throws Exception {
        KernelEngine deltaEngine = DeltaEngine.launch();
        deltaEngine.setFrameRate(60);
        deltaEngine.printFrameRate(true);

        MapLevel mapLevel = new MapLevel("test",30,20);
        deltaEngine.addMap(mapLevel);
        mapLevel = new MapLevel("test2", 10,10);
        mapLevel.addCase(new Wall(2,2));
        deltaEngine.addMap(mapLevel);

        deltaEngine.setCurrentMap("test");


        Entity pacman = new Entity("pacman", new Coordinates(5, 5), new Circle(1, Color.YELLOW), new Dimension(1, 1));
        pacman.setSpeed(0);
        mapLevel.addEntity(pacman);

        Entity red = new Entity("red", new Coordinates(7, 7), new Rectangle(Color.RED), new Dimension(1, 1));
        red.setSpeed(0);
        mapLevel.addEntity(red);

        InputEvent moveUpEvent = new InputEvent(new ChangeMove(pacman, Direction.UP), null);
        InputEvent moveDownEvent = new InputEvent(new ChangeMove(pacman, Direction.DOWN), null);
        InputEvent moveLeftEvent = new InputEvent(new ChangeMove(pacman, Direction.LEFT), null);
        InputEvent moveRightEvent = new InputEvent(new ChangeMove(pacman, Direction.RIGHT), null);

        InputEvent moveUpEventRed = new InputEvent(new ChangeMove(red, Direction.UP), null);
        InputEvent moveDownEventRed = new InputEvent(new ChangeMove(red, Direction.DOWN), null);
        InputEvent moveLeftEventRed = new InputEvent(new ChangeMove(red, Direction.LEFT), null);
        InputEvent moveRightEventRed = new InputEvent(new ChangeMove(red, Direction.RIGHT), null);

        deltaEngine.setInput(Key.Z, moveUpEvent);
        deltaEngine.setInput(Key.Q, moveLeftEvent);
        deltaEngine.setInput(Key.S, moveDownEvent);
        deltaEngine.setInput(Key.D, moveRightEvent);

        deltaEngine.setInput(Key.ARROW_UP, moveUpEventRed);
        deltaEngine.setInput(Key.ARROW_LEFT, moveLeftEventRed);
        deltaEngine.setInput(Key.ARROW_DOWN, moveDownEventRed);
        deltaEngine.setInput(Key.ARROW_RIGHT, moveRightEventRed);

        pacman.setCollisionEvent(red, new Event() {
            @Override
            public void checkEvent() {
                System.out.println("VIDAL E TRO FOR");
            }
        });
        pacman.setAI(new BasicAI(pacman));

        mapLevel.addEvent(new Timer(300));
        deltaEngine.setCurrentMap("test2");
        deltaEngine.addGlobalEvent(new Timer(1000));
    }
}

class ChangeMove implements Trigger {

    private Direction direction;
    private Entity e;

    public ChangeMove(Entity e, Direction direction) {
        this.direction = direction;
        this.e = e;
    }

    @Override
    public void trigger() {
        e.setSpeed(1);
        e.setDirection(direction);
    }
}

class BasicAI extends AI {

    private Entity entity;

    public BasicAI (Entity entity) {
        this.entity = entity;
        this.entity.setDirection(Direction.UP);
        this.entity.setSpeed(5);
    }
    @Override
    public void tick() {
        Coordinates coordinates = entity.getCoordinates();
        if (coordinates.getY() >= 8) {
            entity.setDirection(Direction.UP);
        } else if (coordinates.getY() <= 4) {
            entity.setDirection(Direction.DOWN);
        }
    }
}

class Timer extends Event {

    private long lastTimer, duration;

    public Timer(long duration) {
        this.duration = duration;
        lastTimer = System.currentTimeMillis();
        this.addTrigger(() -> Toolkit.getDefaultToolkit().beep());
    }

    /**
     * Called by the engine, all code of the event. Everything can be implemented here. <br>
     * To activate all attached triggers, runTriggers() method from Event object must be called.
     *
     * @see Event#runTriggers() to run all registered triggers
     */
    @Override
    public void checkEvent() {
        if (System.currentTimeMillis() - lastTimer >= duration) {
            this.runTriggers();
            lastTimer = System.currentTimeMillis();
        }
    }
}
