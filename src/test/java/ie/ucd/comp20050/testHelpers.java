package ie.ucd.comp20050;

import ie.ucd.comp20050.drawing.GamePanel;
import ie.ucd.comp20050.drawing.GameWindow;

public class testHelpers {
    static GamePanel gp;
    static void setup(int pos1,int pos2,int pos3,int pos4,int pos5)
    {

        GameWindow.test = true;
        GamePanel.setTestInput(pos1,pos2,pos3,pos4,pos5);
        GameWindow game = new GameWindow("Test",false);
        gp = game.panel;
        game.startGame();
        waitABit(100);
    }

    static void waitABit(long ms)
    {

        long start = System.currentTimeMillis();
        long curr = System.currentTimeMillis();
        while (curr - start < ms) {
            curr = System.currentTimeMillis();
        }
    }

    static void testLaser(int pos)
    {

        gp.testLaserShoot(pos);
        waitABit(2000);
    }
}