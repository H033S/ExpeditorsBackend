package expeditors.backend.app;

import expeditors.backend.domain.Student;
import java.time.LocalDate;

public class StudentApp {

   public static void main(String[] args) {

      Student student = new Student();
      student.setId(10);
      student.setName("Joey");
      student.setDob(LocalDate.of(2000, 10, 7));
      student.setEmail("blah@com");

      Student student2 = new Student();

      System.out.println("id: " + student.getId());
      System.out.println("name: " + student.getName().length());
      System.out.println("dob: " + student.getDob());
      System.out.println("email: " + student.getEmail());


   }
}
