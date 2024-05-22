package ttl.larku.dao.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ttl.larku.domain.Student;

@Repository
public interface StudentRepo extends JpaRepository<Student, Integer> {

   @Query("select s from Student s join fetch s.phoneNumbers p where s.id = :id")
   Optional<Student> selectAllOfStudent(int id);
}
