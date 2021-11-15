package fr.r1r0r0.deltaengine.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;

class BufferedHashMapTest {
    BufferedHashMap<String,String> hashMap;

    @BeforeEach
    void init() {
        hashMap = new BufferedHashMap();
    }
    @Test
    void put() {
        hashMap.put("Bonjour", "Au revoir");
        assertTrue(hashMap.getAndResetAddedElements().contains("Au revoir"));
        assertEquals(hashMap.get("Bonjour"), "Au revoir");
        assertSame(hashMap.get("Bonjour"), ("Au revoir"));
    }

    @Test
    void remove() {
        hashMap.put("Bonjour", "Au revoir");
        hashMap.remove("Bonjour");
        Collection temp = hashMap.getAndResetRemovedElements();
        assertTrue(temp.contains("Au revoir"));
        assertFalse(temp.contains("Bonjour"));
        assertFalse(hashMap.containsKey("Bonjour"));
    }

    @Test
    void clear() {

        hashMap.put("Bonjour", "Au revoir");
        hashMap.put("Yohan", "Bg");
        hashMap.put("GV", "NUL");
        hashMap.put("tom", "incapable de faire une interface graphique");
        int min = hashMap.size();

        hashMap.clear();
        int actualSize = 0;
        Collection temp = hashMap.getAndResetRemovedElements();
        assertTrue(temp.contains("Au revoir"));
        assertTrue(temp.contains("Bg"));
        assertTrue(temp.contains("NUL"));
        assertFalse(temp.contains("giga bg"));
        assertEquals(temp.size(), min);
        assertEquals(hashMap.size(), actualSize);

    }

    @Test
    void getAndResetAddedElements() {

        int size = hashMap.getAndResetAddedElements().size();
        assertEquals(size,0);
        hashMap.put("Bonjour","Yohan c lourd");
        Collection temp = hashMap.getAndResetAddedElements();
        size = temp.size();
        assertEquals(size,1);
        assertTrue(temp.contains("Yohan c lourd"));

        hashMap.getAndResetAddedElements().add("LOOOL");
        temp = hashMap.getAndResetAddedElements();
        assertEquals(temp.size(), 0);
    }

    @Test
    void getAndResetRemovedElements() {
        int size = hashMap.getAndResetRemovedElements().size();
        assertEquals(size,0);
        hashMap.put("Bonjour","Guillaume c mieux");
        hashMap.remove("Bonjour");

        Collection temp = hashMap.getAndResetRemovedElements();
        assertEquals(temp.size(),1);
        assertTrue(temp.contains("Guillaume c mieux"));

        hashMap.getAndResetRemovedElements().add("LOOOL");
        temp = hashMap.getAndResetRemovedElements();
        assertEquals(temp.size(), 0);
    }

}