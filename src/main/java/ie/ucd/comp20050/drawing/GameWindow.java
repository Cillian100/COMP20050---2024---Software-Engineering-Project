package ie.ucd.comp20050.drawing;

import javax.swing.*;

/*
This class will contain the APIs for interacting with the game's window.
This will amalgamate many features from GameFrame and GamePanel, although much will be divested.
*/
public class GameWindow extends JFrame {

    public GameWindow(String title, boolean resizable) {
        this.add(new GamePanel());
        this.setTitle(title);
        this.setResizable(resizable);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

}
