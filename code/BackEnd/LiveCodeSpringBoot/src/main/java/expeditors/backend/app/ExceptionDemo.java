package expeditors.backend.app;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExceptionDemo {

   private Logger logger = LoggerFactory.getLogger(this.getClass().getPackageName());

   public void main(String[] args) {
      fun(9);
   }

   public void fun(int i) {
      try {
         bar(i);
      }
      catch(Exception ex) {
         logger.warn("Unexpected exception happened"  + ex.getMessage());
      }
//      catch (IOException ex){
//         logger.warn("Exception happened: " + ex.getMessage());
//      }
   }

   public void bar(int i) {
      if (i < 10) {
            throw new RuntimeException("i should not be less that 10");
      }

      String s = null;
      System.out.println(s.length());
   }

}

class BadIValueException extends Exception {
   public BadIValueException(String msg) {
      super(msg);
   }
}
