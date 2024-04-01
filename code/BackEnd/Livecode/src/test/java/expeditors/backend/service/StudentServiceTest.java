package expeditors.backend.service;

import expeditors.backend.domain.Student;
import expeditors.backend.jconfig.LarkUConfig;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {LarkUConfig.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class StudentServiceTest {

   @Autowired
   private StudentService studentService;

   @Autowired
   private ApplicationContext context;

   @BeforeEach
   public void beforeEach() {
//     studentService = new StudentService();
   }

   @Test
   public void testStudentServiceInsert() {
      Student student = new Student("Franky", LocalDate.of(2000, 10, 10));

      Student insertedStudent = studentService.createStudent(student);

      System.out.println("student: " + insertedStudent.toString());
      assertNotNull(insertedStudent);
      assertEquals(1, student.getId());
   }

   @Test
   public void testDeleteExistingStudent() {
      Student student = new Student(10, "Franky", LocalDate.of(2000, 10, 10));


      Student insertedStudent = studentService.createStudent(student);

      System.out.println("student: " + insertedStudent);
      assertNotNull(insertedStudent);

      boolean deleted = studentService.deleteStudent(insertedStudent.getId());
      assertTrue(deleted);
   }

   @Test
   public void testDeleteNonExistingStudent() {


      boolean deleted = studentService.deleteStudent(9999);
      assertFalse(deleted);
   }
}
