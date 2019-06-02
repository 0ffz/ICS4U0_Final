package com.almostcreativegames.adversity.Entity.Characters;

import com.almostcreativegames.adversity.Dialog.Dialog;
import com.almostcreativegames.adversity.Entity.Behaviours.Talkable;
import com.almostcreativegames.adversity.Entity.Entity;

import java.util.Arrays;

public class Mom extends Entity implements Talkable {

    @Override
    public Dialog getDialog() {
        Dialog intro = new Dialog();
        intro.setMessages(Arrays.asList("You should be going to work honey.", "And another message!"));
        return intro;
    }
}
