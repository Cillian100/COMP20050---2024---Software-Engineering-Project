package ie.ucd.comp20050;
import ie.ucd.comp20050.drawing.GamePanel;
import org.junit.jupiter.api.Test;
import static  org.junit.jupiter.api.Assertions.assertEquals;

public class CollisionTests{


   @Test
   void OneBounceStandard()
   {
      GamePanel gp = testHelpers.setup(1,2,3,4,5);
      testHelpers.testLaser(3,gp);
      testHelpers.waitABit(1000);
      assertEquals(1,gp.getEndings().size());
      assertEquals(13,gp.getEndings().get(0));
      testHelpers.EndTest();
   }

   @Test
   void TwoAtoms180bounce()
   {

      GamePanel gp =testHelpers.setup(1,3,43,44,45);
      testHelpers.testLaser(35,gp);
      testHelpers.waitABit(1000);
      assertEquals(1,gp.getEndings().size());
      assertEquals(35,gp.getEndings().get(0));

      testHelpers.EndTest();
   }

   @Test
   void TwoAtoms90Bounce()
   {

   GamePanel gp=    testHelpers.setup(1,0,43,44,45);
      testHelpers.testLaser(37,gp);
      testHelpers.waitABit(1000);
      assertEquals(1,gp.getEndings().size());
      assertEquals(44,gp.getEndings().get(0));
      testHelpers.EndTest();
   }

   @Test
   void MultipleBounces(){

      GamePanel gp= testHelpers.setup(5,8,27,23,6);
      testHelpers.testLaser(17,gp);
      //multiple bounces take longer
      testHelpers.waitABit(1000);
      assertEquals(1,gp.getEndings().size());
      assertEquals(19,gp.getEndings().get(0));
      testHelpers.EndTest();
   }


   @Test
   void Absorb(){

      GamePanel gp= testHelpers.setup(1,6,43,44,45);
      testHelpers.testLaser(37,gp);
      testHelpers.waitABit(1000);
      assertEquals(1,gp.getEndings().size());
      assertEquals(100,gp.getEndings().get(0));

      testHelpers.EndTest();
   }


   @Test
   void EdgeMapTests(){

      GamePanel gp = testHelpers.setup(39,6,43,44,45);
      testHelpers.testLaser(3,gp);
      testHelpers.testLaser(4,gp);
      testHelpers.testLaser(5,gp);
      testHelpers.waitABit(1000);
      assertEquals(3,gp.getEndings().size());
      assertEquals(100,gp.getEndings().get(0));
      assertEquals(100,gp.getEndings().get(1));
      assertEquals(5,gp.getEndings().get(2));
      testHelpers.EndTest();
   }


}
