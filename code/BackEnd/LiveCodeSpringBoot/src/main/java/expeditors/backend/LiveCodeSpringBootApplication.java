package expeditors.backend;

import expeditors.backend.domain.Course;
import expeditors.backend.domain.Student;
import expeditors.backend.service.CourseService;
import expeditors.backend.service.StudentService;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@SpringBootApplication
//@Configuration
//@ComponentScan  //({"expeditors.backend", "com.blah.livecodespringboot"})
//@EnableAutoConfiguration
public class LiveCodeSpringBootApplication {

   public static void main(String[] args) {
      SpringApplication.run(LiveCodeSpringBootApplication.class, args);
   }
}

@Component
class StudentsInitializer implements CommandLineRunner
{
   @Autowired
   private StudentService studentService;

   @Override
   public void run(String... args) throws Exception {
      System.out.println("MyRunner called");

      List<Student> students = studentService.getStudents();
      System.out.println("students: " + students.size());
      System.out.println(students);
   }
}

@Component
class CourseInitializer implements CommandLineRunner
{
   @Autowired
   private CourseService courseService;

   @Override
   public void run(String... args) throws Exception {
      System.out.println("Initializing Courses ");

      var courses = List.of(
            new Course("Math-101", "Intro to Math"),
            new Course("Astro-202", "Intro to Astronomy")
      );

      courses.forEach(courseService::createCourse) ;

      courses = courseService.getAllCourses();
      System.out.println("courses: " + courses.size());
      System.out.println(courses);
   }
}
