package fr.r1r0r0.deltaengine.model.sprites;

import fr.r1r0r0.deltaengine.view.colors.Color;
import javafx.scene.Node;

/**
 * Text Sprite, used to display text
 */
public final class Text implements Sprite {

    private int size = 12;
    private String font;
    private Color color;
    private javafx.scene.text.Text jfxText;

    /**
     * Instantiate Text Sprite, used to display text
     * @param text to be displayed
     */
    public Text(String text) {
        jfxText = new javafx.scene.text.Text();
        jfxText.setText(text);
    }

    /**
     * set the text to be displayed
     * @param text the new text to be displayed
     */
    public void setText(String text) {
        jfxText.setText(text);
    }

    /**
     * set the size of the text to be displayed
     * @param size the new size of the text
     */
    public void setSize(int size) {
        String style = String.format("-fx-font: %s \"%s\";", size, font);
        this.size = size;
        jfxText.setStyle(style);
    }

    /**
     * set the font of the text to be displayed
     * @param font the new font of the text
     */
    public void setFont(String font) {
        String style = String.format("-fx-font: %s \"%s\";", size, font);
        this.font = font;
        jfxText.setStyle(style);
    }

    /**
     * set the color of the text to be displayed
     * @param color the new color of the text
     */
    public void setColor(Color color) {
        this.color = color;
        jfxText.setFill(color.getFXColor());
    }

    @Override
    public Node getNode() {
        return jfxText;
    }

    @Override
    @Deprecated
    /**
     * Use setSize instead, do nothing
     */
    public void resize(double width, double height) {
    }
}
