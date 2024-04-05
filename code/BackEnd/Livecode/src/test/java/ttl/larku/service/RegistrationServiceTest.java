package ttl.larku.service;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ttl.larku.domain.Course;
import ttl.larku.domain.ScheduledClass;
import ttl.larku.domain.Student;
import ttl.larku.jconfig.LarkUConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {LarkUConfig.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles({"development"})
public class RegistrationServiceTest {

   @Autowired
   private StudentService studentService;
   @Autowired
   private ClassService classService;
   @Autowired
   private RegistrationService registrationService;

   @Test
   public void testRegisterStudentForClass() {

      int studentId = 3;
      Student student = studentService.getStudent(studentId);
      assertNotNull(student);
      assertEquals(0, student.getClasses().size());

      String courseCode = "MATH-101";

      ScheduledClass scheduledClass =
            classService.getScheduledClassesByCourseCode(courseCode).getFirst();

      boolean result = registrationService.registerStudentForClass(student.getId(), courseCode,
            scheduledClass.getStartDate());
      assertTrue(result);

      List<ScheduledClass> classes = student.getClasses();

      System.out.println("student: " + student);

      Student updateStudent = studentService.getStudent(studentId);
      assertEquals(1, student.getClasses().size());
   }
}
