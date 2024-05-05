package ie.ucd.comp20050;

import ie.ucd.comp20050.drawing.GameWindow;

public class Main {

    public static void main(String[] args) {

        GameWindow gameWindow = new GameWindow("BlackBox", false);

        // Start game clock
        gameWindow.startGame();
    }

}