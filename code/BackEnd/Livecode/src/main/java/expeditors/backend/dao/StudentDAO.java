package expeditors.backend.dao;

import expeditors.backend.domain.Student;
import java.util.List;

public interface StudentDAO {
   Student insert(Student student);

   boolean update(Student student);

   boolean delete(int id);

   Student findById(int id);

   List<Student> findAll();
}
