package fr.r1r0r0.deltaengine.model;

import fr.r1r0r0.deltaengine.exceptions.MapCaseCoordinateStackingException;
import fr.r1r0r0.deltaengine.exceptions.MapEntityDuplicateException;
import fr.r1r0r0.deltaengine.exceptions.MapEntityNameStackingException;
import fr.r1r0r0.deltaengine.model.elements.Case;
import fr.r1r0r0.deltaengine.model.elements.Entity;
import fr.r1r0r0.deltaengine.model.events.Event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class Map {

    private final String name;
    private final int width;
    private final int height;
    private final List<Case> casesList; //TODO passer par le map.values
    private final java.util.Map<Coordinates,Case> cases;
    private final List<Entity> entitiesList;
    private final java.util.Map<String,Entity> entities;
    private final List<Event> events;

    public Map (String name, int width, int height) {
        this.name = name;
        this.width = width;
        this.height = height;
        cases = new HashMap<>();
        casesList = new ArrayList<>();
        entities = new HashMap<>();
        entitiesList = new ArrayList<>();
        events = new ArrayList<>();
    }

    public String getName () {
        return name;
    }

    public int getWidth () {
        return width;
    }

    public int getHeight () {
        return height;
    }

    public void setCase (Case c) throws MapCaseCoordinateStackingException {
        Coordinates coordinates = c.getCoordinates();
        if (cases.containsKey(coordinates))
            throw new MapCaseCoordinateStackingException(cases.get(coordinates),c);
        cases.put(coordinates,c);
    }

    public Case getCase (int x, int y) {
        return cases.get(new Coordinates(x,y));
    }

    public void addEntity (Entity entity) throws MapEntityDuplicateException, MapEntityNameStackingException {
        if (entitiesList.contains(entity))
            throw new MapEntityDuplicateException(entity);
        if (entities.containsKey(entity.getName()))
            throw new MapEntityNameStackingException(entities.get(entity.getName()),entity);
        entitiesList.add(entity);
        entities.put(entity.getName(),entity);
    }

    public Entity getEntity (String name) {
        return entities.get(name);
    }

    public void removeEntity (Entity entity) {
        entitiesList.remove(entity);
        entities.remove(entity.getName());
    }

    public void clearEntities () {
        entitiesList.clear();
        entities.clear();
    }

    public void addEvent (Event event) {
        if ( ! events.contains(event)) events.add(event);
    }

    public void removeEvent (Event event) {
        events.remove(event);
    }

    public void clearEvents () {
        events.clear();
    }

    public List<Case> getCases () {
        return casesList;
    }

    public List<Entity> getEntities () {
        return entitiesList;
    }

    public List<Event> getEvents () {
        return events;
    }

}
