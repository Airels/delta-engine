package fr.r1r0r0.deltaengine.view.colors;

/**
 * A Color, using RGBA to coding it. You can code all colors you want with.
 * Converts your Color into JavaFX color.
 */
public class Color {

    private javafx.scene.paint.Color jfxColor;

    /**
     * Default constructor, allowing to create your color.
     * @param red red component ranging from 0 to 255
     * @param green green component ranging from 0 to 255
     * @param blue blue component ranging from 0 to 255
     * @param opacity opacity ranging from 0 to 255
     */
    public Color(int red, int green, int blue, int opacity) {
        if (red < 0 || red > 255) throw new IllegalArgumentException("Color's red value (" + red + ") must be in the range 0-255");
        if (green < 0 || green > 255) throw new IllegalArgumentException("Color's green value (" + green + ") must be in the range 0-255");
        if (blue < 0 || blue > 255) throw new IllegalArgumentException("Color's blue value (" + blue + ") must be in the range 0-255");
        if (opacity < 0 || opacity > 255) throw new IllegalArgumentException("Opacity value (" + opacity + ") must be in the range 0-255");

        jfxColor = new javafx.scene.paint.Color(red/255.0, green/255.0, blue/255.0, opacity/255.0);
    }

    /**
     * Returns you JavaFX color converted.
     * @return JavaFX Color
     */
    public javafx.scene.paint.Color getJFXColor() {
        return jfxColor;
    }
}
