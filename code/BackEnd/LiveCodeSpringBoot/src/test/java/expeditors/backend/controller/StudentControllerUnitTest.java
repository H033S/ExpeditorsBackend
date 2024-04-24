package expeditors.backend.controller;

import expeditors.backend.domain.Student;
import expeditors.backend.service.StudentService;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
public class StudentControllerUnitTest {

   @Mock
   private StudentService studentService;

   @Mock
   private UriCreator uriCreator;

   @Mock
   private Validator validator;

   @InjectMocks
   private StudentServiceController controller;

   List<Student> students = List.of(
         new Student("Nancy", LocalDate.parse("2000-10-10")),
         new Student("Joe", LocalDate.parse("1998-10-10"))
   );

   @Test
   public void testGetAll() {

      Mockito.when(studentService.getStudents()).thenReturn(students);

      try {
         List<Student> result = controller.getAll();
      } catch (Exception e) {
         throw new RuntimeException(e);
      }
      assertEquals(2, students.size());

      Mockito.verify(studentService).getStudents();
   }

   @Test
   public void testAddStudent() throws URISyntaxException {
      String expectedHeader = "http://localhost:8080/student/0";
      URI expecedURI = new URI(expectedHeader);

      //set up Mock behaviours
      Mockito.when(studentService.createStudent(students.getFirst())).thenReturn(students.getFirst());
      Mockito.when(uriCreator.getURI(students.getFirst().getId())).thenReturn(expecedURI);

      Student student = new Student(null, LocalDate.parse("2000-10-10"));

      Validator validator = new LocalValidatorFactoryBean();
      //Empty Errors object
      Errors errors = new BeanPropertyBindingResult(students.getFirst(), "student");
      ResponseEntity<?> response = controller.addStudent(students.getFirst(), errors);

      //Do assertions
      assertEquals(HttpStatus.CREATED, response.getStatusCode());


      String locHeader = response.getHeaders().getFirst("Location");
      assertEquals(expectedHeader, locHeader);


      Mockito.verify(studentService).createStudent(students.getFirst());

   }

   @Test
   public void testAddInvalidStudent() throws URISyntaxException {
      String expectedHeader = "http://localhost:8080/student/0";
      URI expecedURI = new URI(expectedHeader);
      Student student = new Student(null, LocalDate.parse("2000-10-10"));

      ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
      jakarta.validation.Validator hibValidator = factory.getValidator();

      SpringValidatorAdapter springValidator = new SpringValidatorAdapter(hibValidator);
      BindingResult bindingResult= new BeanPropertyBindingResult(student, "student");
      springValidator.validate(student, bindingResult);

//      Errors errors = validator.validateObject(student);
      //Make the call
      ResponseEntity<?> response = controller.addStudent(student, bindingResult);

      //Do assertions
      assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());


      Mockito.verify(studentService, never()).createStudent(student);

   }


}
