package com.almostcreativegames.adversity.Dialog;

import com.almostcreativegames.adversity.Entity.*;

public class Dialog {
    private String message;
    private Entity player;

    public Dialog(Entity e){
        player = e;
    }
    private void determineMessage(){
        if (player.getX() == 10 && player.getY() == 20)
            message = "Oh no!";
        else
            message = "I wonder what to do!";
    }

    public String getMessage(){
        determineMessage();
        return message;
    }
}
