package ie.ucd.comp20050;

import ie.ucd.comp20050.drawing.GamePanel;
import org.junit.jupiter.api.Test;

import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GuessTests {

    void setupGuess(GamePanel gp)
    {

        KeyEvent g =  new KeyEvent(gp, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_G, 'G');;
        testHelpers.gw.keyReleased(g);
        testHelpers.waitABit(100);
    }

    void guessNumber(Integer num,GamePanel gp)
    {
        String s = num.toString();
        char c;
        KeyEvent g;
        for (int i= 0;i < s.length();i++)
        {
            c = s.charAt(i);
             g = new KeyEvent(gp, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_4, c);
            testHelpers.gw.keyTyped(g);
            testHelpers.waitABit(100);
        }
        c = 10;
        g = new KeyEvent(gp, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_4, c);
        testHelpers.gw.keyTyped(g);
        testHelpers.waitABit(100);
    }


    void Turn(int numRight,GamePanel gp)
    {
        int mod = numRight < 0? -1:1;
        setupGuess(gp);
        for (int i= 1; i <= 5;i++) {
        if (i <= numRight)    guessNumber(i * mod,gp);
        else guessNumber((i + 20)* mod,gp);
        }
        testHelpers.waitABit(500);
    }

    @Test
    //single unit tests for the score of getting all right
    void AllRight()
    {
        GamePanel gp = testHelpers.setup(1,2,3,4,5);
        Turn(5,gp);
        assertEquals(0,gp.getPlayerScore(1));
        testHelpers.EndTest();
    }

    @Test
    //single unit test for the score of getting all wrong
    void AllWrong()
    {

        GamePanel gp =testHelpers.setup(1,2,3,4,5);
        Turn(0,gp);
        //invalid inputs
        Turn(-1,gp);
        Turn(0,gp);
        assertEquals(25,gp.getPlayerScore(1));
        assertEquals(25,gp.getPlayerScore(2));
        testHelpers.EndTest();
    }

    @Test
        //single unit test for the score of getting some wrong
    void SomeRight()
    {

        GamePanel gp= testHelpers.setup(1,2,3,4,5);
        Turn(3,gp);
        assertEquals(10,gp.getPlayerScore(1));
        testHelpers.EndTest();
    }


    @Test
    void Draw()
    {

        GamePanel gp =testHelpers.setup(1,2,3,4,5);
        Turn(3,gp);
        testHelpers.waitABit(1000);
        Turn(3,gp);
        assertEquals(gp.getPlayerScore(2),gp.getPlayerScore(1));
        testHelpers.EndTest();
    }
   @Test
    void PlayerOneWin()
    {

        GamePanel gp = testHelpers.setup(1,2,3,4,5);
        Turn(3,gp);
        testHelpers.testLaser(3,gp);
        Turn(3,gp);
        assertEquals(10,gp.getPlayerScore(1)  );
        assertTrue(gp.getPlayerScore(2) > gp.getPlayerScore(1));
        assertEquals(gp.getPlayerScore(2),gp.getPlayerScore(1) + 1);

        testHelpers.EndTest();
    }

    @Test
    void Player2Win()
    {

        GamePanel gp = testHelpers.setup(1,2,3,4,5);
        for (int i= 0;i < 7;i++)
        {
            testHelpers.testLaser(3,gp);
        }
        Turn(3,gp);
        testHelpers.testLaser(3,gp);
        Turn(2,gp);
        assertEquals(17,gp.getPlayerScore(1));
        assertEquals(16,gp.getPlayerScore(2));
        assertTrue(gp.getPlayerScore(2) < gp.getPlayerScore(1));
        testHelpers.EndTest();
    }


}
