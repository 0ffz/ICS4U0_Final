package com.almostcreativegames.adversity.Dialog;

import java.util.Arrays;
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

    /**
     * Looked at what Arrays.asList() does and realized we can avoid having to call that method every time by
     * replicating how it works and accepting a list of Strings
     *
     * @param messages a list of messages
     */
    public Dialog(String... messages) {
        this.messages = Arrays.asList(messages);
    }

    public Dialog(List<String> messages) {
        this.messages = messages;
    }

    public void onEnd() {
    }

    /**
     * Gets the current message being displayed
     *
     * @return the message in the list that is being displayed
     */
    public String getCurrentMessage() {
        return messages.get(index - 1);
    }

    /**
     * Goes to the next message
     *
     * @return the next message
     */
    public String nextMessage() {
        if (index == messages.size()) {
            onEnd();
            return null;
        }
        return messages.get(index++);
    }
}
