package expeditors.backend.service;

import expeditors.backend.dao.StudentDAO;
import expeditors.backend.domain.Student;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
StudentService should allow users to perform basic create, update, delete operations on Students to a store.
2. StudentService should allow users to retrieve a Student by ID
3. StudentService should allow users to retrieve all Students.
 */
@Service
public class StudentService {

   List<String> lstr = new ArrayList<>();

   @Autowired
   private StudentDAO studentDAO;

   private int numCalls;
   private AtomicInteger betterCounter = new AtomicInteger(1);

   int xyz = 10;

   public StudentService() {
   }

   public Student createStudent(Student student) {
//      int y = numCalls++;
      xyz++;
      int x = betterCounter.getAndIncrement();
      //Validate DOB
      //Other business logic

      //Save the student to a Store == what kind of store?
      Student insertedStudent = studentDAO.insert(student);

      return insertedStudent;
      //Return a result
   }

   public boolean deleteStudent(int id) {
      return studentDAO.delete(id);
   }

   public boolean updateStudent(Student student) {
      return studentDAO.update(student);
   }

   public Student getStudent(int id) {
      return studentDAO.findById(id);
   }

   public List<Student> getStudents() {
      return studentDAO.findAll();
   }

   public List<Student> getStudentsByName(String name) {
      return studentDAO.findByName(name);
   }

   //   public JPAStudentDAO getStudentDAO() {
//   public InMemoryStudentDAO getStudentDAO() {
   public StudentDAO getStudentDAO() {
      return studentDAO;
   }

   public void setStudentDAO(StudentDAO studentDAO) {
      this.studentDAO = studentDAO;
   }

   public List<Student> getByParameters(Map<String, String> queryStrings) {
      Predicate<Student> finalPred = null;

      for(var entry: queryStrings.entrySet()) {
        var key = entry.getKey();
        var value = entry.getValue();

        switch(key) {
           case "name" -> {
              //Predicate<Student> pred = (s) -> s != null && s.getName().equals(value);
              Predicate<Student> pred = (s) -> s != null && s.getName().contains(value);

              finalPred = finalPred == null ? pred : finalPred.or(pred);
           }
           case "dob" -> {
              var dobValue = LocalDate.parse(value);
              Predicate<Student> pred = (s) -> s != null && s.getDob().equals(dobValue);

              finalPred = finalPred == null ? pred : finalPred.or(pred);
           }
           case "status" -> {
              var eStatus = Student.Status.valueOf(value);

              Predicate<Student> pred = (s) -> s != null && s.getStatus().equals(eStatus);

              finalPred = finalPred == null ? pred : finalPred.or(pred);
           }
        }
      }

      finalPred = finalPred != null ? finalPred : (s) -> true;

      List<Student> result = studentDAO.findAll().stream()
            .filter(finalPred)
            .toList();

      return result;
   }
}