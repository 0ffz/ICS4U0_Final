package com.almostcreativegames.adversity.Entity.Behaviours;

import com.almostcreativegames.adversity.Battle.Battle;
import com.almostcreativegames.adversity.Entity.Button.Button;
import com.almostcreativegames.adversity.Entity.Entity;

import java.util.List;

public interface Battleable {
    Entity getBattleSprite();

    List<Button> getActOptions(Battle battle);
}
