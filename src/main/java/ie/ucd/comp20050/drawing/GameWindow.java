package ie.ucd.comp20050.drawing;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static ie.ucd.comp20050.MathUtils.calculateWindowModifier;

/**
 * APIs for interacting with the Game Window.
 * Implements ActionListener for redrawing, KeyListener for handling input.
 */
public class GameWindow extends JFrame implements ActionListener, KeyListener {

    /**
     * Adjusted height of user's screen. Used to create a box window for the game.
     */
    private final int WINDOW_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height - 80;

    /**
     * Modifier used to dynamically adjust elements sizing based on user's resolution.
     */
    private final double WINDOW_MODIFIER = calculateWindowModifier(WINDOW_HEIGHT);

    /**
     * Window dimension. May become unnecessary and deprecated in a future version.
     */
    private final Dimension WINDOW_SIZE = new Dimension(WINDOW_HEIGHT, WINDOW_HEIGHT);


    public GamePanel panel;
    public static boolean test = false;
    public GameWindow(String title, boolean resizable) {
        GamePanel.test = GameWindow.test;
        panel = new GamePanel(WINDOW_SIZE, WINDOW_MODIFIER);
        this.add(panel);
        this.setPreferredSize(WINDOW_SIZE);
        this.setTitle(title);
        this.setResizable(resizable);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        panel.addKeyListener(this);
    }


    /**
     * Causes the game clock to start, triggers actionPerformed every 50ms.
     * {@link #actionPerformed(ActionEvent)}
     */
    public void startGame() {
        new Timer(50, this).start();
    }

    /**
     * Causes the window to repaint when fired.
     * @param event the event to be processed
     * {@link #startGame()} triggers this method.
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        panel.repaint();
        if(panel.zip) panel.laser.move();
    }

    /**
     * Handles user input for moving pointer position.
     * User can use 'A' or 'Left arrow' to move counter-clockwise around grid.
     * User can use 'D' or 'Right arrow' to move clockwise around grid.
     * User position wraps around at indexes 0 and 53.
     * @param event event to be processed
     */
    @Override
    public void keyPressed(KeyEvent event) {
        /* Ensures user pressed a movement key */
        switch(event.getKeyCode()) {
            case KeyEvent.VK_A, KeyEvent.VK_LEFT -> panel.posPointer--;
            case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> panel.posPointer++;
        }

        /* Resets pointer position if it moves out of bounds */
        if(panel.posPointer < 0) panel.posPointer = 53;
        else if(panel.posPointer > 53) panel.posPointer = 0;
    }

    /**
     * Handles user input for ray spawning.
     * User can use 'W' or 'Space' to spawn a ray at his current pointer position.
     * @param event event to be processed
     */
    @Override
    public void keyReleased(KeyEvent event) {
        /* Ensures user pressed W or space */
        if(!panel.guessing && !panel.zip){
            switch(event.getKeyCode()) {
                case KeyEvent.VK_W, KeyEvent.VK_SPACE -> panel.spawnRay();
                case KeyEvent.VK_G ->{ panel.guessing = true; return;}
                default -> { return; }
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent event){
        panel.setterInput = event.getKeyChar();
    }

}
