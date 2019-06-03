package com.almostcreativegames.adversity.Entity.Characters;

import com.almostcreativegames.adversity.Battle.Battle;
import com.almostcreativegames.adversity.Dialog.Dialog;
import com.almostcreativegames.adversity.Entity.Behaviours.Battleable;
import com.almostcreativegames.adversity.Entity.Menu.Button;
import com.almostcreativegames.adversity.Entity.Entity;
import com.almostcreativegames.adversity.Entity.EntityAnimated;
import com.almostcreativegames.adversity.Entity.SpriteAnimation;

import java.util.Arrays;
import java.util.List;

/**
 *
 */
public class Wire extends EntityAnimated implements Battleable {
    {
        setName("Sparking wire");
    }

    @Override
    public Entity getBattleSprite() {
        EntityAnimated sprite = new EntityAnimated();
        sprite.addAnimation("idle", new SpriteAnimation("Entities/Wire-spritesheet.png", 0, 0, 32, 16, 2, 1, 20, 20, 1));
        sprite.setCurrentAnimation("idle");
        return sprite;
    }

    @Override
    public void onInteract() {
        Battle battle = new Battle("Battle/Battle", this, room, room.getGame());
        room.getGame().getRenderer().loadRoom(battle);
    }

    @Override
    public List<Button> getActOptions(Battle battle) {
        Button cut = new Button("Cut wire");
        Button inspect = new Button("Inspect") {
            @Override
            public void onInteract() {
                startDialog(new Dialog(Arrays.asList(
                        "The wire seems to have electrical\nsparks coming off of it",
                        "You probably shouldn't touch it\nbare handed")));
            }
        };
        Button more = new Button("Talk to wire");
        Button options = new Button("Talk to wire");
        Button talk = new Button("Talk to wire");

        return Arrays.asList(cut, inspect, talk, more, options);
    }

    @Override
    protected void registerAnimations() {
        addAnimation("idle", new SpriteAnimation("Entities/Wire-spritesheet.png", 0, 0, 32, 16, 2, 1, 5, 5, 1));
        setCurrentAnimation("idle");
    }
}
