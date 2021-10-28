package fr.r1r0r0.deltaengine.model.elements;

public class ElementVisitor {

    public static boolean isAnEntity(Element element) {
        return (element instanceof Entity); // TODO PB DE CONCEPTION
    }
}
