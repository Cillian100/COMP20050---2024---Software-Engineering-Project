package ie.ucd.comp20050;
import org.junit.jupiter.api.Test;
import static  org.junit.jupiter.api.Assertions.assertEquals;

public class CollisionTests{


   @Test
   void OneBounceStandard()
   {
      testHelpers.setup(1,2,3,4,5);
      testHelpers.testLaser(3);
      testHelpers.waitABit(1000);
      assertEquals(1,testHelpers.gp.getEndings().size());
      assertEquals(13,testHelpers.gp.getEndings().get(0));
   }

   @Test
   void TwoAtoms180bounce()
   {

      testHelpers.setup(1,3,43,44,45);
      testHelpers.testLaser(35);
      testHelpers.waitABit(1000);
      assertEquals(1,testHelpers.gp.getEndings().size());
      assertEquals(35,testHelpers.gp.getEndings().get(0));

   }

   @Test
   void TwoAtoms90Bounce()
   {

      testHelpers.setup(1,0,43,44,45);
      testHelpers.testLaser(37);
      testHelpers.waitABit(1000);
      assertEquals(1,testHelpers.gp.getEndings().size());
      assertEquals(44,testHelpers.gp.getEndings().get(0));
   }

   @Test
   void MultipleBounces(){

      testHelpers.setup(5,8,27,23,6);
      testHelpers.testLaser(17);
      //multiple bounces take longer
      testHelpers.waitABit(1000);
      assertEquals(1,testHelpers.gp.getEndings().size());
      assertEquals(19,testHelpers.gp.getEndings().get(0));
   }


   @Test
   void Absorb(){

      testHelpers.setup(1,6,43,44,45);
      testHelpers.testLaser(37);
      testHelpers.waitABit(1000);
      assertEquals(1,testHelpers.gp.getEndings().size());
      assertEquals(100,testHelpers.gp.getEndings().get(0));

   }


   @Test
   void EdgeMapTests(){

      testHelpers.setup(39,6,43,44,45);
      testHelpers.testLaser(3);
      testHelpers.testLaser(4);
      testHelpers.testLaser(5);
      testHelpers.waitABit(1000);
      assertEquals(3,testHelpers.gp.getEndings().size());
      assertEquals(100,testHelpers.gp.getEndings().get(0));
      assertEquals(100,testHelpers.gp.getEndings().get(1));
      assertEquals(5,testHelpers.gp.getEndings().get(2));

   }


}
