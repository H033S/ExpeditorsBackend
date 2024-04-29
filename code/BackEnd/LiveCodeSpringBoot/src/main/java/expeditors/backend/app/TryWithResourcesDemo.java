package expeditors.backend.app;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TryWithResourcesDemo {
   private Logger logger = LoggerFactory.getLogger(this.getClass().getPackageName());

   public void main(String[] args) throws Exception {
//      doWork();
      muchBetterWayToHandleException();
   }

   public void doWork() {

      FileInputStream fis = null;
      try {
         fis = new FileInputStream("pom.xml");
         //Open up a file  - acquire a resource

         int val = fis.read();

         //Close the file - release the resource

         String s = null;
         int i = s.length();

      } catch (IOException e) {
         throw new RuntimeException(e);
      } catch (Exception e) {
         logger.warn("Unexpected Exception: " + e);
      } finally {
         try {
            fis.close();
         } catch (IOException ex) {
            throw new RuntimeException(ex);
         }

      }
      //read/write the file
   }

   public void muchBetterWayToHandleException() throws Exception {

      //Acquire resource
      try (FileInputStream fis = new FileInputStream("pom");
           MyClass mc = new MyClass();) {
         int val = fis.read();
         //Close the file - release the resource

         String s = null;
         int i = s.length();
      }
//      catch (IOException e) {
//         throw new RuntimeException(e);
//      } catch (Exception e) {
//         logger.warn("Unexpected Exception: " + e);
//      }
      //Resource is automatically closed.
   }

   class MyClass implements AutoCloseable{

      @Override
      public void close() throws Exception {
         System.out.println("Close called on MyClass");
      }
   }
}
