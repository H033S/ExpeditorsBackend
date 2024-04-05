package expeditors.backend.service;

import expeditors.backend.domain.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

   @Autowired
   private StudentService studentService;
   public void registerStudentForClass(int studentId, int classId) {
      Student student = studentService.getStudent(studentId);
      if(student != null) {

      }

   }
}
