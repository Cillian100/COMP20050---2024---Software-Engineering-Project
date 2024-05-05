package ie.ucd.comp20050.drawing;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static ie.ucd.comp20050.MathUtils.calculateWindowModifier;

/**
 * APIs for interacting with the Game Window.
 * Implements ActionListener for redrawing.
 */
public class GameWindow extends JFrame implements ActionListener {

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
    }


    /**
     * Causes the game clock to start, triggers actionPerformed every 50ms.
     * {@link #actionPerformed(ActionEvent)}
     */
    public void startGame() { // May need to become async if non-blockable functionality added. @TODO Revisit startGame
        new Timer(50, this).start();
    }

    /**
     * Causes the window to repaint when fired.
     * @param event the event to be processed
     * {@link #startGame()} triggers this method.
     */
    @Override
    public void actionPerformed(ActionEvent event) { //@TODO encapsulate 'zip' and 'zipzap'
        panel.repaint();
        if(panel.zip) panel.laser.move();
    }


}
