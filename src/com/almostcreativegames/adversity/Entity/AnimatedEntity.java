package com.almostcreativegames.adversity.Entity;

import javafx.scene.canvas.GraphicsContext;

import java.util.HashMap;
import java.util.Map;


/**
 * A class for animated entities. Holds multiple animations in a hashmap and sets the current image to the frame from
 * the current animation
 *
 * <h2>Course Info</h2>
 * ICS4U0 with Krasteva V.
 *
 * @author Daniel Voznyy
 * @version 0.1.1
 *
 * <h2>Changelog</h2>
 * <p>0.1.1 - Holds multiple animations in a hashmap and sets the current image to the frame from the current animation
 * and renders it</p>
 */
public class AnimatedEntity extends Entity {
    private Map<String, SpriteAnimation> animations = new HashMap<>();
    private SpriteAnimation currentAnimation;

    public AnimatedEntity() {
        super();
    }

    public AnimatedEntity(int layer) {
        super(layer);
    }

    public void addAnimation(String name, SpriteAnimation animation) {
        animations.put(name, animation);
    }

    public void setCurrentAnimation(String name) {
        currentAnimation = animations.get(name);
        setImage(currentAnimation.getFrame(0));
    }

    @Override
    public void render(GraphicsContext gc, double time) {
        setImage(currentAnimation.getFrame(time));
        super.render(gc, time);
    }
}