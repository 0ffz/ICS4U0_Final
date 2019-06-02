package com.almostcreativegames.adversity.Entity;

import com.almostcreativegames.adversity.Battle.Battle;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

public class BattleButton extends Button {
    private List<Button> subOptions = new ArrayList<>();
    private Battle battle;

    public BattleButton setText(String text) {
        this.text = text;
        return this;
    }

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

    public void addSubOption(Button option) {
        subOptions.add(option);
        int offsetX = 200;
        int offsetY = 700;
        //TODO I'm calculating something wrong here right now, fix it
        option.setPosition(offsetX + (subOptions.size() - 1) % 2 * 100, offsetY + (subOptions.size() - 1) / 2 * 100);
    }
}
