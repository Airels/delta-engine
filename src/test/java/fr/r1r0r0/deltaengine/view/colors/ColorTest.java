package fr.r1r0r0.deltaengine.view.colors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ColorTest {

    @Test
    public void testConstructor() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Color(256, 0, 0, 0));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Color(0, 256, 0, 0));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Color(0, 0, 256, 0));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Color(0, 0, 0, 256));
        Assertions.assertDoesNotThrow(() -> new Color(0, 0, 0, 0));

        Color c = new Color(255, 255, 255, 255);
        Color c2 = new Color(255, 0, 0, 0);

        Assertions.assertEquals(1, c.getJFXColor().getRed());
        Assertions.assertEquals(1, c.getJFXColor().getGreen());
        Assertions.assertEquals(1, c.getJFXColor().getBlue());
        Assertions.assertEquals(1, c.getJFXColor().getOpacity());

        Assertions.assertEquals(1, c2.getJFXColor().getRed());
        Assertions.assertEquals(0, c2.getJFXColor().getGreen());
        Assertions.assertEquals(0, c2.getJFXColor().getBlue());
        Assertions.assertEquals(0, c2.getJFXColor().getOpacity());
    }
}