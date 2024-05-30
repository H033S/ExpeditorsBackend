package ttl.larku.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import ttl.larku.dao.BaseDAO;
import ttl.larku.domain.Course;

@Transactional
public interface CourseService {
   Course createCourse(String code, String title);

   Course createCourse(Course course);

   boolean deleteCourse(int id);

   boolean updateCourse(Course newCourse);

   Course getCourseByCode(String code);

   Course getCourse(int id);

   List<Course> getAllCourses();

   BaseDAO<Course> getCourseDAO();

   void setCourseDAO(BaseDAO<Course> courseDAO);

   void clear();
}
