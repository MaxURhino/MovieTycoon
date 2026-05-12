package net.max_rhino.movietycoon;

import net.max_rhino.movietycoon.game.MainApp;

public class Main {
    public static MainApp mainApp;

    static void main() {
        mainApp = new MainApp();
        mainApp.run();
    }
}
