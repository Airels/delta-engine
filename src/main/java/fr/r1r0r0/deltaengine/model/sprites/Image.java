package fr.r1r0r0.deltaengine.model.sprites;

import javafx.scene.Node;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public final class Image implements Sprite {

    private String path;

    public Image(String path) {
        this.path = path;
    }

    @Override
    public Node getNode() {
        try {
            javafx.scene.image.Image img = new javafx.scene.image.Image(new FileInputStream(path));

            return new ImageView(img);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
