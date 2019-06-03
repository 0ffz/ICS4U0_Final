package com.almostcreativegames.adversity.Dialog;

import java.util.List;

/**
 * A class for determining the output of the dialog
 *
 * <h2>Course Info</h2>
 * ICS4U0 with Krasteva V.
 *
 * @author Enfei Zhang & Daniel Voznyy
 * @version 0.2.1
 *
 * <h2>Changelog</h2>
 * <p>0.2.1 -
 * Enfei: Added basic premise on how to return dialog
 * Daniel: Added nextMessage() method and index to go through messages one by one</p>
 */

public class Dialog {
    private List<String> messages;
    private int index = 0;

    public Dialog(List<String> messages) {
        this.messages = messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public String nextMessage() {
        if (index == messages.size())
            return null;
        return messages.get(index++);
    }
}
