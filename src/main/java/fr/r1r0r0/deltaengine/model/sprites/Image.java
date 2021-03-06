package fr.r1r0r0.deltaengine.model.sprites;

import javafx.scene.Node;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Image Sprite to display image
 */
public final class Image implements Sprite {

    private String path;
    private ImageView imageView;

    /**
     * Instantiate an Image Sprite with an image
     * @param path to the image file
     * @throws FileNotFoundException
     */
    public Image(String path) throws FileNotFoundException {
        this.path = path;
        imageView = new ImageView(new javafx.scene.image.Image(getClass().getResourceAsStream(path)));
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

    /**
     * Path to image file
     * @return String path to file
     */
    public String getPath() {
        return path;
    }
}

