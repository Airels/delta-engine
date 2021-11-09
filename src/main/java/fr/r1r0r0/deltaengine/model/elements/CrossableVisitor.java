package fr.r1r0r0.deltaengine.model.elements;

/**
 * A modified Visitor pattern, to access isCrossable method by the engine.
 * Used to avoid to override isCrossable method in Cell (because it is overriden by UncrossableCell)
 */
public class CrossableVisitor {

    /**
     * Check if a given case is crossable (equivalent to "c instanceof Cell")
     * @param c case to check
     * @return true if case is crossable, false otherwise
     */
    public static boolean isCaseCrossable(Cell c) {
        return c.isCrossable();
    }
}
