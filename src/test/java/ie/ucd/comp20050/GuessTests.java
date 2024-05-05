package ie.ucd.comp20050;

import org.junit.jupiter.api.Test;

import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GuessTests {

    void setupGuess()
    {

        KeyEvent g =  new KeyEvent(testHelpers.gp, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_G, 'G');;
        testHelpers.gp.keyReleased(g);
        testHelpers.waitABit(100);
    }

    void guessNumber(Integer num)
    {
        String s = num.toString();
        char c;
        KeyEvent g;
        for (int i= 0;i < s.length();i++)
        {
            c = s.charAt(i);
             g = new KeyEvent(testHelpers.gp, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_4, c);
            testHelpers.gp.keyTyped(g);
            testHelpers.waitABit(100);
        }
        c = 10;
        g = new KeyEvent(testHelpers.gp, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_4, c);
        testHelpers.gp.keyTyped(g);
        testHelpers.waitABit(100);
    }


    void Turn(int numRight)
    {

        setupGuess();
        for (int i= 1; i <= 5;i++) {
        if (i <= numRight)    guessNumber(i);
        else guessNumber(i + 20);
        }
    }

    @Test
    //single unit tests for the score of getting all right
    void AllRight()
    {
        testHelpers.setup(1,2,3,4,5);
        Turn(5);
        assertEquals(0,testHelpers.gp.getPlayerScore(1));
    }

    @Test
    //single unit test for the score of getting all wrong
    void AllWrong()
    {

        testHelpers.setup(1,2,3,4,5);
        Turn(0);
        assertEquals(25,testHelpers.gp.getPlayerScore(1));
    }

    @Test
        //single unit test for the score of getting some wrong
    void SomeRight()
    {

        testHelpers.setup(1,2,3,4,5);
        Turn(3);
        assertEquals(10,testHelpers.gp.getPlayerScore(1));
    }


    @Test
    void Draw()
    {

        testHelpers.setup(1,2,3,4,5);
        Turn(3);
        testHelpers.waitABit(1000);
        Turn(3);
        assertEquals(testHelpers.gp.getPlayerScore(2),testHelpers.gp.getPlayerScore(1));
    }
   @Test
    void PlayerOneWin()
    {

        testHelpers.setup(1,2,3,4,5);
        Turn(3);
        testHelpers.testLaser(3);
        Turn(3);
        assertEquals(10,testHelpers.gp.getPlayerScore(1)  );
        assertTrue(testHelpers.gp.getPlayerScore(2) > testHelpers.gp.getPlayerScore(1));
        assertEquals(testHelpers.gp.getPlayerScore(2),testHelpers.gp.getPlayerScore(1) + 1);

    }

    @Test
    void Player2Win()
    {

        testHelpers.setup(1,2,3,4,5);
        for (int i= 0;i < 7;i++)
        {
            testHelpers.testLaser(3);
        }
        Turn(3);
        testHelpers.testLaser(3);
        Turn(2);
        assertEquals(17,testHelpers.gp.getPlayerScore(1));
        assertEquals(16,testHelpers.gp.getPlayerScore(2));
        assertTrue(testHelpers.gp.getPlayerScore(2) < testHelpers.gp.getPlayerScore(1));

    }


}
