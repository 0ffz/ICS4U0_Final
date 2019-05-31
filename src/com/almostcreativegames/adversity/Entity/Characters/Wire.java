package com.almostcreativegames.adversity.Entity.Characters;

import com.almostcreativegames.adversity.Entity.Behaviours.Battleable;
import com.almostcreativegames.adversity.Entity.Entity;
import com.almostcreativegames.adversity.Entity.EntityAnimated;
import com.almostcreativegames.adversity.Entity.SpriteAnimation;

/**
 *
 */
public class Wire extends EntityAnimated implements Battleable {
    @Override
    public Entity getBattleSprite() {
        EntityAnimated sprite = new EntityAnimated();
        String playerSprite = "Entities/Player/Player-spritesheet.png";
        sprite.addAnimation("idle", new SpriteAnimation(playerSprite, 0, 0, 11, 15, 2, 1, 20, 20, 1));
        sprite.setCurrentAnimation("idle");
        return sprite;
    }

    @Override
    protected void registerAnimations() {
        String playerSprite = "Entities/Player/Player-spritesheet.png";
        addAnimation("idle", new SpriteAnimation(playerSprite, 0, 0, 11, 15, 2, 1, 20, 20, 1));
        setCurrentAnimation("idle");
    }
}
