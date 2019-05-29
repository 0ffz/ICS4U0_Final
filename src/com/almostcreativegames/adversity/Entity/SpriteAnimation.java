package com.almostcreativegames.adversity.Entity;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

import java.util.ArrayList;

/**
 * A class holding frames that can be used to animate an Entity
 *
 * <h2>Course Info</h2>
 * ICS4U0 with Krasteva V.
 *
 * @author Daniel Voznyy
 * @version 0.1.1
 *
 * <h2>Changelog</h2>
 * <p>0.1.1 - Image can be resized, on creation. The getFrame() method is assumed to be called every frame with the
 * amount of time that has passed</p>
 * <p>0.2.2 - Reduced number of parameters needed to be passed in constructor</p>
 */
public class SpriteAnimation {
    private ArrayList<Image> frames;
    private double fps;
    private double progress;

    public SpriteAnimation(String spriteSheetPath, int topX, int topY, int width, int height, int columns, int numFrames, double sizeX, double sizeY, double speed) {
        fps = speed;
        width *= sizeX;
        topX *= sizeX;
        height *= sizeY;
        topY *= sizeY;

        Image beforeResize = new Image(spriteSheetPath);
        Image spriteSheet = new Image(spriteSheetPath, beforeResize.getWidth() * sizeX, beforeResize.getHeight() * sizeY, false, false);
        frames = new ArrayList<Image>();

        PixelReader reader = spriteSheet.getPixelReader();
        int rows = ((numFrames - (numFrames % columns)) / columns) + 1; //TODO make sure this is correct
        System.out.println(numFrames + " " + (numFrames % columns));
        for (int row = 0; row < rows; row++)
            for (int col = 0; col < columns; col++) {
                if (row * columns + col == numFrames) //if on last frame
                    return;
                System.out.println("Loading: " + row + " " + col);
                WritableImage newImage = new WritableImage(reader, col * (width) + topX, row * height + topY, width, height);
                frames.add(newImage);
            }
    }

    /**
     *
     * @param time the amount of time passed
     * @return the frame that should currently be played
     */
    public Image getFrame(double time){
        progress += time;

        if ((int) (progress * fps) >= frames.size()) //if looped through all frames
            progress = 0; //start from the beginning
        return frames.get((int) (progress * fps));
    }
}