package net.max_rhino.movietycoon;

import net.max_rhino.movietycoon.game.MainApp;
import org.joml.Vector2f;
import org.joml.Vector2i;

public class Main {
    public static MainApp mainApp;

    static void main() {
        mainApp = new MainApp();
        mainApp.run();
    }
}
