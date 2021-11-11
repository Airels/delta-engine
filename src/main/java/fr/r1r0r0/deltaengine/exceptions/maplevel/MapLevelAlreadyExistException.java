package fr.r1r0r0.deltaengine.exceptions.maplevel;

import fr.r1r0r0.deltaengine.model.MapLevel;

public class MapLevelAlreadyExistException extends MapLevelException {

    public MapLevelAlreadyExistException(String msg) {
        super(msg);
    }

    public MapLevelAlreadyExistException(MapLevel mapLevel) {
        super("MapLevel with name " + mapLevel.getName() + " already exist");
    }
}
