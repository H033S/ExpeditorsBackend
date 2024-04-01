package expeditors.backend.dao.inmemory;

import expeditors.backend.dao.StudentDAO;
import expeditors.backend.domain.Student;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryStudentDAO implements StudentDAO {

   //private static List<Student> students = new ArrayList<>();
//   private List<Student> students = new ArrayList<>();
//   private Set<Student> students = new HashSet<>();
   private Map<Integer, Student> students = new HashMap<>();
   private int nextId = 1;

   public InMemoryStudentDAO() {
      int stop = 0;
   }

   @Override
   public Student insert(Student student) {
      int id = nextId++;
      student.setId(id);

      student.setName("InMem:" + student.getName());
      students.put(student.getId(), student);
      return student;
   }

   @Override
   public boolean update(Student student) {
      return students.replace(student.getId(), student) != null;
   }

   @Override
   public boolean delete(int id) {
      return students.remove(id) != null;
   }

   @Override
   public Student findById(int id) {
      return students.get(id);
   }

   @Override
   public List<Student> findAll() {
      return new ArrayList<>(students.values());
   }
}
