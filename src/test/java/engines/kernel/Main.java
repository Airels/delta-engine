package engines.kernel;

import fr.r1r0r0.deltaengine.model.*;
import fr.r1r0r0.deltaengine.model.Dimension;
import fr.r1r0r0.deltaengine.model.elements.cells.Cell;
import fr.r1r0r0.deltaengine.model.elements.cells.CrossableCell;
import fr.r1r0r0.deltaengine.model.elements.entity.Entity;
import fr.r1r0r0.deltaengine.model.elements.cells.default_cells.Wall;
import fr.r1r0r0.deltaengine.model.engines.DeltaEngine;
import fr.r1r0r0.deltaengine.model.engines.KernelEngine;
import fr.r1r0r0.deltaengine.model.engines.utils.Key;
import fr.r1r0r0.deltaengine.model.events.Event;
import fr.r1r0r0.deltaengine.model.events.InputEvent;
import fr.r1r0r0.deltaengine.model.events.Trigger;
import fr.r1r0r0.deltaengine.model.maplevel.MapLevel;
import fr.r1r0r0.deltaengine.model.maplevel.MapLevelBuilder;
import fr.r1r0r0.deltaengine.model.sprites.shapes.Circle;
import fr.r1r0r0.deltaengine.model.sprites.shapes.Rectangle;
import fr.r1r0r0.deltaengine.view.colors.Color;

import java.awt.*;

public class Main {

    public static void main(String[] args) throws Exception {
        KernelEngine deltaEngine = DeltaEngine.launch();
        deltaEngine.setFrameRate(60);
        deltaEngine.printFrameRate(true);

        MapLevel mapLevel = new MapLevelBuilder("test",30,20).build();
        deltaEngine.addMap(mapLevel);
        //mapLevel = createMapLevelDamier("test2", 10,10);
        mapLevel = createMapLevelPrison("test2", 10,10,6,8,6,8);
        mapLevel.replaceCell(new Wall(7, 7));

        Wall w =new Wall(2,2);
        mapLevel.replaceCell(new CrossableCell(2,2,new Rectangle(Color.RED)));
        deltaEngine.addMap(mapLevel);

        deltaEngine.setCurrentMap("test");


        Entity pacman = new Entity("pacman", new Coordinates<>(5., 5.), new Circle(1, Color.YELLOW), new Dimension(1, 1));
        pacman.setSpeed(0);
        mapLevel.addEntity(pacman);

        Entity red = new Entity("red", new Coordinates<>(8., 8.), new Rectangle(0.5,0.5,Color.RED), new Dimension(0.8, 0.8));
        red.setSpeed(0);


        InputEvent moveUpEvent = new InputEvent(new ChangeMove(pacman, Direction.UP), new ChangeMove(pacman, Direction.IDLE));
        InputEvent moveDownEvent = new InputEvent(new ChangeMove(pacman, Direction.DOWN), new ChangeMove(pacman, Direction.IDLE));
        InputEvent moveLeftEvent = new InputEvent(new ChangeMove(pacman, Direction.LEFT), new ChangeMove(pacman, Direction.IDLE));
        InputEvent moveRightEvent = new InputEvent(new ChangeMove(pacman, Direction.RIGHT), new ChangeMove(pacman, Direction.IDLE));

        InputEvent moveUpEventRed = new InputEvent(new ChangeMove(red, Direction.UP), new ChangeMove(red, Direction.IDLE));
        InputEvent moveDownEventRed = new InputEvent(new ChangeMove(red, Direction.DOWN), new ChangeMove(red, Direction.IDLE));
        InputEvent moveLeftEventRed = new InputEvent(new ChangeMove(red, Direction.LEFT), new ChangeMove(red, Direction.IDLE));
        InputEvent moveRightEventRed = new InputEvent(new ChangeMove(red, Direction.RIGHT), new ChangeMove(red, Direction.IDLE));

        deltaEngine.setInput(Key.Z, moveUpEvent);
        deltaEngine.setInput(Key.Q, moveLeftEvent);
        deltaEngine.setInput(Key.S, moveDownEvent);
        deltaEngine.setInput(Key.D, moveRightEvent);

        deltaEngine.setInput(Key.ARROW_UP, moveUpEventRed);
        deltaEngine.setInput(Key.ARROW_LEFT, moveLeftEventRed);
        deltaEngine.setInput(Key.ARROW_DOWN, moveDownEventRed);
        deltaEngine.setInput(Key.ARROW_RIGHT, moveRightEventRed);

        InputEvent exitEvent = new InputEvent(() -> System.exit(0), null);
        deltaEngine.setInput(Key.ESCAPE, exitEvent);

        InputEvent pauseEvent = new InputEvent(deltaEngine::haltCurrentMap, deltaEngine::resumeCurrentMap);
        deltaEngine.setInput(Key.P, pauseEvent);

        /*
        pacman.setCollisionEvent(red, new Event() {
            @Override
            public void checkEvent() {
                System.out.println("VIDAL E TRO FOR");
            }
        });
        */
        //pacman.setAI(new BasicAI(pacman));

        mapLevel.addEvent(new Timer(300));
        deltaEngine.setCurrentMap("test2");
        deltaEngine.addGlobalEvent(new Timer(1000));

        //Thread.sleep(3000);
        mapLevel.addEntity(red);

        MapLevelBuilder b = new MapLevelBuilder("test3", 10, 30);
        b.setCell(new CrossableCell(0, 0, new Rectangle(Color.RED)));
        b.setCell(new CrossableCell(9, 29, new Rectangle(Color.GREEN)));
        b.setCell(new CrossableCell(0, 29, new Rectangle(Color.BLUE)));
        b.setCell(new CrossableCell(9, 0, new Rectangle(Color.WHITE)));

        deltaEngine.addMap(b.build());
        deltaEngine.setCurrentMap("test3");

        //Thread.sleep(10000);

        /*
        try {
            for (; ; Thread.sleep(2000)) {
                System.out.println(pacman.getCoordinates() + " -- " + red.getCoordinates());
                System.out.println("moveAvailable UP: " + deltaEngine.isAvailableDirection(red,Direction.UP));
                System.out.println("moveAvailable DOWN: " + deltaEngine.isAvailableDirection(red,Direction.DOWN));
                System.out.println("moveAvailable RIGHT: " + deltaEngine.isAvailableDirection(red,Direction.RIGHT));
                System.out.println("moveAvailable LEFT: " + deltaEngine.isAvailableDirection(red,Direction.LEFT));
            }
        } catch (Exception e) {

        }
        */
    }

    private static MapLevel createMapLevelDamier (String name, int width, int height) {
        MapLevelBuilder mapLevelBuilder = new MapLevelBuilder(name,width,height);
        try {
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    mapLevelBuilder.setCell(
                            new CrossableCell(i, j,new Rectangle(((i + j) & 1) == 0 ? Color.BLACK : Color.WHITE))
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mapLevelBuilder.build();
    }

    private static MapLevel createMapLevelPrison (String name, int width, int height,
                                                  int prisonXMin, int prisonXMax,
                                                  int prisonYMin, int prisonYMax) {
        MapLevelBuilder mapLevelBuilder = new MapLevelBuilder(name,width,height);
        try {
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    Cell cell;
                    if (prisonXMin <= i && i <= prisonXMax
                            && prisonYMin <= j && j <= prisonYMax) {
                        cell = new CrossableCell(i, j, new Rectangle(Color.WHITE));
                    } else {
                        cell = new Wall(i, j);
                    }
                    mapLevelBuilder.setCell(cell);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mapLevelBuilder.build();
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
        Coordinates<Double> coordinates = entity.getCoordinates();
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
