package com.almostcreativegames.adversity.Entity;

import com.almostcreativegames.adversity.Input.InputListener;

public class Player extends AnimatedEntity {
    public Player(String playerSprite) {
        //TODO is it better to leave this up to GameRunner to create the animations?
        addAnimation("idle", new SpriteAnimation(playerSprite, 0, 0, 11, 15, 2, 1, 5, 5, 1));
        addAnimation("left", new SpriteAnimation(playerSprite, 0, 15, 11, 15, 2, 2, 5, 5, 5));
        addAnimation("right", new SpriteAnimation(playerSprite, 0, 30, 11, 15, 2, 2, 5, 5, 5));
        addAnimation("up", new SpriteAnimation(playerSprite, 0, 15, 11, 15, 2, 2, 5, 5, 5));
        addAnimation("down", new SpriteAnimation(playerSprite, 0, 0, 11, 15, 2, 1, 5, 5, 5));

    }

    @Override
    public void update(double time, double friction) {
        super.update(time, friction);
        // game logic

        if (InputListener.isKeyPressed("LEFT") || InputListener.isKeyPressed("A")) addVelocity(-100, 0);
        if (InputListener.isKeyPressed("RIGHT") || InputListener.isKeyPressed("D")) addVelocity(100, 0);
        if (InputListener.isKeyPressed("UP") || InputListener.isKeyPressed("W")) addVelocity(0, -100);
        if (InputListener.isKeyPressed("DOWN") || InputListener.isKeyPressed("S")) addVelocity(0, 100);

        //TODO player sometimes gets stuck with this system
        collisionCheck(time);

        setCurrentAnimation("idle");
        if (getVelocityX() < -50)
            setCurrentAnimation("left");
        else if (getVelocityX() > 50)
            setCurrentAnimation("right");
        else if (getVelocityY() < -50)
            setCurrentAnimation("up");
        else if (getVelocityY() > 50)
            setCurrentAnimation("down");
    }

    public void collisionCheck(double time) {
        if (room.isColliding(this, time)) {
            double tempVX = getVelocityX();
            setVelocity(0, getVelocityY());
            if (room.isColliding(this, time)) {
                setVelocity(tempVX, 0);
                if (room.isColliding(this, time))
                    setVelocity(0, 0);
            }

        }
    }
}
