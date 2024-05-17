package ttl.larku.dao.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ttl.larku.domain.ScheduledClass;
import ttl.larku.domain.SmallClassDTO;

@Repository
public interface ClassRepo extends JpaRepository<ScheduledClass, Integer> {

   @Query("select sc from ScheduledClass sc join fetch sc.course")
   List<ScheduledClass> findAllWithCourse();

   @Query("select sc.startDate, sc.endDate, c.code from ScheduledClass sc join sc.course c")
   List<Object []> findSmall();

   @Query("select new ttl.larku.domain.SmallClassDTO(sc.startDate, sc.endDate, c.code) from ScheduledClass sc join sc.course c")
   List<SmallClassDTO> findSmallDTO();
}
