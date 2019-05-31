package com.almostcreativegames.adversity.Entity;

import com.almostcreativegames.adversity.Battle.Battle;

import java.util.List;

public class BattleButton extends Entity {
    private List<Entity> subOptions;
    private Battle battle;

    public void setBattle(Battle battle) {
        this.battle = battle;
    }

    @Override
    public void onInteract() {
        hide();
        for (Entity option : subOptions) {
            battle.addEntity(option);
        }
    }

    public void addSubOption(Entity option){
        subOptions.add(option);
        //TODO set option's position based on how many there are in the List
    }
}
