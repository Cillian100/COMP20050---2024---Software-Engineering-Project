package ie.ucd.comp20050;

import ie.ucd.comp20050.drawing.GameWindow;
import ie.ucd.comp20050.entity.Atom;

import java.util.ArrayList;


public class Main {

    public static void main(String[] args) {


        // gameWindow will eventually be used to manipulate the elements being drawn
        GameWindow gameWindow = new GameWindow("BlackBox", false);

        // Start game clock
        gameWindow.startGame();

    }

}