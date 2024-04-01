package ttl;

import org.junit.jupiter.api.Test;

public class StaticTest {

   private static int x;

   static {
      x = 25; //From DB
   }
   public int instanceVar;

   public StaticTest() {
      this.instanceVar = 100;  //Read from DB
   }


   @Test
   public void testStatic() {
      int i = 10;
   }
}
