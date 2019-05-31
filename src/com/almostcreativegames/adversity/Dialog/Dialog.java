package com.almostcreativegames.adversity.Dialog;

import java.util.ArrayList;
import java.util.List;

/**
 * A class for determining the output of the dialog
 *
 * <h2>Course Info</h2>
 * ICS4U0 with Krasteva V.
 *
 * @author Enfei Zhang
 * @version 0.1.1
 *
 * <h2>Changelog</h2>
 * <p>0.1.1 - Added basic premise on how to return dialog</p>
 */

public class Dialog {
    private List<String> responses = new ArrayList<>();
    private int index = 0;

    public List<String> getResponses() {
        return responses;
    }

    public Dialog setResponses(List<String> responses) {
        this.responses = responses;
        return this;
    }

    public void nextMessage() {
        index++;
    }

    public String getMessage() {
        return responses.get(index);
    }
}
