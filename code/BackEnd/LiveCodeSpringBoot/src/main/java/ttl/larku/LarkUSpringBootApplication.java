package ttl.larku;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import ttl.larku.domain.Student;
import ttl.larku.service.StudentService;

@SpringBootApplication
public class LarkUSpringBootApplication {
   public static void main(String[] args) {
      SpringApplication.run(LarkUSpringBootApplication.class, args);
   }
}

@Component
class LarkUSpringBootInMemInitializer implements CommandLineRunner
{
   @Autowired
   private StudentService studentService;

   @Override
   public void run(String... args) throws Exception {
      System.out.println("MyRunner called");

      List<Student> students = studentService.getAllStudents();
      System.out.println("students: " + students.size());
      System.out.println(students);
   }
}
