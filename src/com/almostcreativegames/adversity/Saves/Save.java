package com.almostcreativegames.adversity.Saves;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * The class that will create saves of our game
 *
 * <h2>Course Info</h2>
 * ICS4U0 with Krasteva V.
 *
 * @author Daniel Voznyy
 * @version 0.0.1
 */

public class Save {
    private static String saveDir = System.getProperty("user.home") + "\\almostcreative\\dghsaw";
    private static String fileName = "\\save.dat\\";

    public static void saveGame(int day, int score) {
        System.out.println(saveExists());
        try {
            File file = new File(saveDir);
            file.mkdirs();

            PrintWriter output = new PrintWriter((new FileWriter(saveDir+fileName))); //creates a variable to access the file
            output.println(day);
            output.println(score);
            output.flush();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean saveExists() {
        return new File(saveDir+fileName).exists();
    }
}
