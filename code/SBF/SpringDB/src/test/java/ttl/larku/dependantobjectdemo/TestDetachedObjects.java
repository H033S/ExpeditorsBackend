package ttl.larku.dependantobjectdemo;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import ttl.larku.dao.repository.ClassRepo;
import ttl.larku.dao.repository.CourseRepo;
import ttl.larku.domain.Course;
import ttl.larku.domain.ScheduledClass;

@SpringBootTest
public class TestDetachedObjects {

   @Autowired
   private ClassRepo classRepo;

   @Autowired
   private CourseRepo courseRepo;

   @Test
   @Transactional
   @Rollback(false)
   public void testDependantObject() {
      Course course = new Course("KNIT-101", "Intro to Knitting");
      course.setId(5);

      ScheduledClass sc = new ScheduledClass(course, LocalDate.of(2024, 10, 10), LocalDate.of(2025, 10, 10));
      classRepo.save(sc);

//      ScheduledClass sc2 = new ScheduledClass(course, LocalDate.of(2024, 10, 10), LocalDate.of(2025, 10, 10));
//      classRepo.save(sc2);
   }

   @Test
   public void testDeleteCourse() {
       courseRepo.deleteById(4);
   }
}
