package ttl.larku.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ttl.larku.domain.Course;

@Repository
public interface CourseRepo extends JpaRepository<Course, Integer> {
}
