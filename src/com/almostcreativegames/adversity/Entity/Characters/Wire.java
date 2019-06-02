package com.almostcreativegames.adversity.Entity.Characters;

import com.almostcreativegames.adversity.Battle.Battle;
import com.almostcreativegames.adversity.Entity.*;
import com.almostcreativegames.adversity.Entity.Behaviours.Battleable;

import java.util.Arrays;
import java.util.List;

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
    public List<Button> getActOptions(Battle battle) {
        Button cut = new Button();
        cut.setText("Cut wire");
        cut.setImage("DialogBox.png");

        Button inspect = new Button();
        inspect.setText("Inspect");
        inspect.setImage("DialogBox.png");

        Button talk = new Button();
        talk.setText("Talk to wire");
        talk.setImage("DialogBox.png");
        return Arrays.asList(cut, inspect, talk);
    }

    @Override
    protected void registerAnimations() {
        String playerSprite = "Entities/Player/Player-spritesheet.png";
        addAnimation("idle", new SpriteAnimation(playerSprite, 0, 0, 11, 15, 2, 1, 20, 20, 1));
        setCurrentAnimation("idle");
    }
}
