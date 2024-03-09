package ie.ucd.comp20050;

import ie.ucd.comp20050.drawing.GameWindow;
import ie.ucd.comp20050.entity.Atom;

import java.util.ArrayList;

import static ie.ucd.comp20050.Logic.generateAtoms;

public class Main {

    public static void main(String[] args) {

        // Generate Board should be before this, making an arraylist of hexagons.
        // '61' should then be replaced with that arraylist's size.
        // We may want to consider a global variable for Atoms, to unify generation+drawing+logic
        ArrayList<Atom> atoms = generateAtoms(5, 61);

        // gameWindow will eventually be used to manipulate the elements being drawn
        GameWindow gameWindow = new GameWindow("BlackBox", false);

        // Add Atoms
        gameWindow.setAtoms(atoms);

        // Start game clock
        gameWindow.startGame();

    }

}