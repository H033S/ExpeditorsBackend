package ttl.larku.dao.repository;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ttl.larku.domain.ScheduledClass;
import ttl.larku.domain.SmallClassDTO;

import static java.lang.System.out;

@SpringBootTest
public class ClassRepoTest {

   @Autowired
   private ClassRepo classRepo;

   @Test
   public void testFindAllClassesWithFindAll() {
      List<ScheduledClass> classes =  classRepo.findAll();

      out.println("classes: " + classes.size());
      classes.forEach(out::println);

   }

   @Test
   public void testFindAllClassesWithFindWithCourse() {
      List<ScheduledClass> classes =  classRepo.findAllWithCourse();

      out.println("classes: " + classes.size());
      classes.forEach(out::println);

   }

   @Test
   public void testGetSmall() {
      //List<Object []> result = classRepo.findSmall();
      List<SmallClassDTO> result = classRepo.findSmallDTO();

      out.println("result: " + result.size());
      result.forEach(out::println);

      int stop = 0;
   }

}
