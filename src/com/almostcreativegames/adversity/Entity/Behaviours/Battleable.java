package com.almostcreativegames.adversity.Entity.Behaviours;

import com.almostcreativegames.adversity.Battle.Battle;
import com.almostcreativegames.adversity.Entity.Button;
import com.almostcreativegames.adversity.Entity.Entity;
import javafx.scene.image.Image;

import java.util.List;

public interface Battleable {
    Entity getBattleSprite();

    List<Button> getActOptions(Battle battle);
}
