package ie.ucd.comp20050;
import ie.ucd.comp20050.drawing.GamePanel;
import ie.ucd.comp20050.drawing.GameWindow;
import org.junit.jupiter.api.Test;
import static  org.junit.jupiter.api.Assertions.assertEquals;

public class CollisionTests{
   GamePanel gp;
   void setup(int pos1,int pos2,int pos3,int pos4,int pos5)
   {

      GameWindow.test = true;
      GamePanel.setTestInput(pos1,pos2,pos3,pos4,pos5);
      GameWindow game = new GameWindow("Test",false);
       gp = game.panel;
      game.startGame();
      waitABit(100);
   }

   void waitABit(long ms)
   {

      long start = System.currentTimeMillis();
      long curr = System.currentTimeMillis();
      while (curr - start < ms) {
         curr = System.currentTimeMillis();
      }
   }

   void testCollision(int pos)
   {

      gp.testLaserShoot(pos);
      waitABit(2000);
   }



   @Test
   void OneBounceStandard()
   {
      setup(1,2,3,4,5);
      testCollision(3);
      assertEquals(1,gp.getEndings().size());
      assertEquals(13,gp.getEndings().get(0));
   }

   @Test
   void TwoAtoms180bounce()
   {

      setup(1,3,43,44,45);
      testCollision(35);
      assertEquals(1,gp.getEndings().size());
      assertEquals(35,gp.getEndings().get(0));

   }

   @Test
   void TwoAtoms90Bounce()
   {

      setup(1,0,43,44,45);
      testCollision(37);
      assertEquals(1,gp.getEndings().size());
      assertEquals(44,gp.getEndings().get(0));
   }

   @Test
   void MultipleBounces(){

      setup(5,8,27,23,6);
      testCollision(17);
      //multiple bounces take longer
      waitABit(1000);
      assertEquals(1,gp.getEndings().size());
      assertEquals(19,gp.getEndings().get(0));
   }


   @Test
   void Absorb(){

      setup(1,6,43,44,45);
      testCollision(37);
      assertEquals(1,gp.getEndings().size());
      assertEquals(100,gp.getEndings().get(0));

   }


   @Test
   void EdgeMapTests(){

      setup(39,6,43,44,45);
      testCollision(3);
      testCollision(4);
      testCollision(5);
      assertEquals(3,gp.getEndings().size());
      assertEquals(100,gp.getEndings().get(0));
      assertEquals(100,gp.getEndings().get(1));
      assertEquals(5,gp.getEndings().get(2));

   }


}
