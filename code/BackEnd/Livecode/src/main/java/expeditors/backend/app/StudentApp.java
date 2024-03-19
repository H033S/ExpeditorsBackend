package expeditors.backend.app;

import expeditors.backend.domain.Student;
import java.time.LocalDate;

public class StudentApp {

   public static void main(String[] args) {

      String name = "Joey";
      String email = "a@B.com";

      String name2 = "Joey";

      Student student = new Student(10, "Joey",
            LocalDate.of(2000, 10, 7), "blah@b.com");

      student.setName("OtherName");

      Student student2 = new Student(11, "Frank", LocalDate.of(2000, 10, 7), "blah@b.com");


      System.out.println("id: " + student.getId());
      System.out.println("name: " + student.getName().length());
      System.out.println("dob: " + student.getDob());
      System.out.println("email: " + student.getEmail());

   }
}
