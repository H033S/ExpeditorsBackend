package expeditors.backend.controller;

import expeditors.backend.domain.Student;
import expeditors.backend.service.StudentService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class StudentControllerUnitTest {

   @Mock
   private StudentService studentService;

   @Mock
   private UriCreator uriCreator;

   @InjectMocks
   private StudentServiceController controller;

   List<Student> students = List.of(
         new Student("Nancy", LocalDate.parse("2000-10-10")),
         new Student("Joe", LocalDate.parse("1998-10-10"))
   );

   @Test
   public void testGetAll() {

      Mockito.when(studentService.getStudents()).thenReturn(students);

      List<Student> result = controller.getAll();
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

      //Make the call
      ResponseEntity<?> response = controller.addStudent(students.getFirst());

      //Do assertions
      assertEquals(HttpStatus.CREATED, response.getStatusCode());


      String locHeader = response.getHeaders().getFirst("Location");
      assertEquals(expectedHeader, locHeader);


      Mockito.verify(studentService).createStudent(students.getFirst());

   }


}
