package fr.r1r0r0.deltaengine.model.sprites;

import javafx.scene.Node;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public final class Image implements Sprite {

    private String path;
    private ImageView imageView;

    public Image(String path) throws FileNotFoundException {
        this.path = path;
        imageView = new ImageView(new javafx.scene.image.Image(new FileInputStream(path)));
    }

    @Override
    public Node getNode() {
        return imageView;
    }

    @Override
    public void resize(double width, double height) {
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
    }
}

