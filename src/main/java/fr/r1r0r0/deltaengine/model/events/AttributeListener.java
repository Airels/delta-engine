package fr.r1r0r0.deltaengine.model.events;

/**
 * An attribute listener, used to execute code when an attribute of an object is updated.
 * For example: when Direction of an Entity is updated, it will call attributeUpdated giving old and new attribute value
 * @param <T>
 */
public interface AttributeListener<T> {

    /**
     * Called when attribute is updated.
     * @param oldValue old attribute value
     * @param newValue new attribute value
     */
     void attributeUpdated(T oldValue, T newValue);
}
