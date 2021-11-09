package fr.r1r0r0.deltaengine.model.sprites;

import javafx.scene.Node;
import javafx.scene.paint.Color;

/**
 * TODO
 */
public final class Text implements Sprite {

    private int size = 12;
    private String font;
    private Color color;
    private javafx.scene.text.Text jfxText;

    /**
     * TODO
     * @param text
     */
    public Text(String text) {
        jfxText = new javafx.scene.text.Text();
        jfxText.setText(text);
    }

    /**
     * TODO
     * @param text
     */
    public void setText(String text) {
        jfxText.setText(text);
    }

    /**
     * TODO
     * @param size
     */
    public void setSize(int size) {
        String style = String.format("-fx-font: %s %s;", font, size);
        this.size = size;
        jfxText.setStyle(style);
    }

    /**
     * TODO
     * @param font
     */
    public void setFont(String font) {
        String style = String.format("-fx-font: %s %s;", font, size);
        this.font = font;
        jfxText.setStyle(style);
    }

    /**
     * TODO
     * @param color
     */
    public void setColor(Color color) {
        this.color = color;
        jfxText.setFill(color);
    }

    @Override
    public Node getNode() {
        return jfxText;
    }

    @Override
    public void resize(double width, double height) {
        // TODO
    }
}
