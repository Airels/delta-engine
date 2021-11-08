package fr.r1r0r0.deltaengine.model.sprites;

import javafx.scene.Node;
import javafx.scene.paint.Color;

public final class Text implements Sprite {

    private int size = 12;
    private String font;
    private Color color;
    private javafx.scene.text.Text jfxText;

    public Text(String text) {
        jfxText = new javafx.scene.text.Text();
        jfxText.setText(text);
    }

    public void setText(String text) {
        jfxText.setText(text);
    }

    public void setSize(int size) {
        String style = String.format("-fx-font: %s %s;", font, size);
        this.size = size;
        jfxText.setStyle(style);
    }

    public void setFont(String font) {
        String style = String.format("-fx-font: %s %s;", font, size);
        this.font = font;
        jfxText.setStyle(style);
    }

    public void setColor(Color color) {
        this.color = color;
        jfxText.setFill(color);
    }

    @Override
    public Node getNode() {
        return jfxText;
    }
}
