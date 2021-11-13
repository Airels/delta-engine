package fr.r1r0r0.deltaengine.view.colors;

public class Color {

    private javafx.scene.paint.Color jfxColor;

    public Color(int red, int green, int blue, int opacity) {
        jfxColor = new javafx.scene.paint.Color(red, green, blue, opacity);
    }

    public javafx.scene.paint.Color getJFXColor() {
        return jfxColor;
    }
}
