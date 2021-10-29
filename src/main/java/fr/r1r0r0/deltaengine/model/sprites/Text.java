package fr.r1r0r0.deltaengine.model.sprites;

import javafx.scene.Node;
import javafx.scene.paint.Color;

public final class Text implements Sprite {

    private String text;
    private int size = 12;
    private String font;
    private Color color;

    public Text(String text) {
        this.text = text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setSize(int size) {

    }

    public void setFont(String font) {

    }

    public void setColor(Color color) {

    }

    @Override
    public Node getNode() {
        javafx.scene.text.Text jfxText = new javafx.scene.text.Text();

        jfxText.setText(text);
        jfxText.setFill(color);
        String style = String.format("-fx-font: %s %s;", font, size);
        jfxText.setStyle(style);

        return jfxText;
    }
}
