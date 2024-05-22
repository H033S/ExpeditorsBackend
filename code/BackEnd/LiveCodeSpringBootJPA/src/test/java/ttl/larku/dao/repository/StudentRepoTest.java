package ttl.larku.dao.repository;

import jakarta.persistence.Transient;
import jakarta.persistence.TypedQuery;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ttl.larku.domain.PhoneNumber;
import ttl.larku.domain.Student;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class StudentRepoTest {

   @Autowired
   private StudentRepo studentRepo;

   @Test
   @Transactional
   public void testInsertStudentAndPhoneNumber() {
      Student student = new Student("Franky", "383 9 9339", Student.Status.HIBERNATING);

      student = studentRepo.save(student);

      Student foundStudent = studentRepo.findById(student.getId()).orElse(null);
//      Student foundStudent = studentRepo.selectAllOfStudent(student.getId()).orElse(null);

      assertNotNull(foundStudent);

      System.out.println(foundStudent);

      List<PhoneNumber> phoneNumbers = foundStudent.getPhoneNumbers();
      System.out.println("phoneNumbers: " + phoneNumbers);
   }

   @Test
   @Transactional
   public void testGetStudentWithNplus1() {
      Student foundStudent = studentRepo.findById(1).orElse(null);
//      Student foundStudent = studentRepo.selectAllOfStudent(student.getId()).orElse(null);

      assertNotNull(foundStudent);

      System.out.println(foundStudent);

      List<PhoneNumber> phoneNumbers = foundStudent.getPhoneNumbers();
      System.out.println("phoneNumbers: " + phoneNumbers);
   }

}

