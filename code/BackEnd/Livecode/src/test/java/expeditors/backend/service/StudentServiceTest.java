package expeditors.backend.service;

import expeditors.backend.domain.Student;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StudentServiceTest {


   @Test
   public void testStudentServiceInsert() {
      Student student = new Student("Franky", LocalDate.of(2000, 10, 10));

      StudentService service = new StudentService();

      Student insertedStudent = service.createStudent(student);

      System.out.println("student: " + insertedStudent.toString());
      assertNotNull(insertedStudent);
      assertEquals(1, student.getId());
   }

   @Test
   public void testDeleteExistingStudent() {
      Student student = new Student(10, "Franky", LocalDate.of(2000, 10, 10));

      StudentService service = new StudentService();

      Student insertedStudent = service.createStudent(student);

      System.out.println("student: " + insertedStudent);
      assertNotNull(insertedStudent);

      boolean deleted = service.deleteStudent(insertedStudent.getId());
      assertTrue(deleted);
   }

   @Test
   public void testDeleteNonExistingStudent() {

      StudentService service = new StudentService();

      boolean deleted = service.deleteStudent(9999);
      assertFalse(deleted);
   }
}
