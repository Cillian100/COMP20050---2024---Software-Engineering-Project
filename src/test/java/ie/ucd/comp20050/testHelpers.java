package ie.ucd.comp20050;

import ie.ucd.comp20050.drawing.GamePanel;
import ie.ucd.comp20050.drawing.GameWindow;

public class testHelpers {

    static GameWindow gw;

    static GamePanel setup(int pos1,int pos2,int pos3,int pos4,int pos5)
    {
        int a;
        while (GameWindow.test == true)a =  1+1;
        GameWindow.test = true;
        GamePanel.setTestInput(pos1,pos2,pos3,pos4,pos5);
        gw = new GameWindow("Test",false);
        GamePanel gp = gw.panel;
        gw.startGame();
        waitABit(100);
        return gp;
    }

    static void waitABit(long ms)
    {

        long start = System.currentTimeMillis();
        long curr = System.currentTimeMillis();
        while (curr - start < ms) {
            curr = System.currentTimeMillis();
        }
    }

    static void testLaser(int pos,GamePanel gp)
    {

        gp.testLaserShoot(pos);
        waitABit(2000);
    }

    static void EndTest()
    {
        GameWindow.test = false;
    }

}
