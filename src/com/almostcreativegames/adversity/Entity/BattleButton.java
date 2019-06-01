package com.almostcreativegames.adversity.Entity;

import com.almostcreativegames.adversity.Battle.Battle;

import java.util.ArrayList;
import java.util.List;

public class BattleButton extends Entity {
    private List<Entity> subOptions = new ArrayList<>();
    private Battle battle;

    public void setBattle(Battle battle) {
        this.battle = battle;
    }

    @Override
    public void onInteract() {
//        hide();
        for (Entity option : subOptions) {
            battle.addEntity(option);
        }
    }

    public void addSubOption(Entity option) {
        subOptions.add(option);
        int offsetX = 200;
        int offsetY = 700;
        //TODO I'm calculating something wrong here right now, fix it
        option.setPosition(offsetX + subOptions.size() / 2 * 100, offsetY + subOptions.size() % 2 * 100);
    }
}
