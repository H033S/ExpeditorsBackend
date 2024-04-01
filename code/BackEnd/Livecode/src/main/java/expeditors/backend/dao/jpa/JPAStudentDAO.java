package expeditors.backend.dao.jpa;

import expeditors.backend.dao.StudentDAO;
import expeditors.backend.domain.Student;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JPAStudentDAO implements StudentDAO {

   //private static List<Student> students = new ArrayList<>();
//   private List<Student> students = new ArrayList<>();
//   private Set<Student> students = new HashSet<>();
   private Map<Integer, Student> students = new HashMap<>();
   private int nextId = 1;

   public Student insert(Student student) {
      int id = nextId++;
      student.setId(id);

      student.setName("Jpa:" + student.getName());
      students.put(student.getId(), student);
      return student;
   }

   public boolean update(Student student) {
      return students.replace(student.getId(), student) != null;
   }

   public boolean delete(int id) {
      return students.remove(id) != null;
   }

   public Student findById(int id) {
      return students.get(id);
   }

   public List<Student> findAll() {
      return new ArrayList<>(students.values());
   }
}
