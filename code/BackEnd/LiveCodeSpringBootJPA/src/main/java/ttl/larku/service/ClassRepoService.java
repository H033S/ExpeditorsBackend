package ttl.larku.service;

import java.util.List;
import org.springframework.stereotype.Service;
import ttl.larku.dao.repository.ClassRepo;
import ttl.larku.domain.ScheduledClass;

@Service
public class ClassRepoService {

   private final ClassRepo classRepo;

   public ClassRepoService(ClassRepo classRepo) {
      this.classRepo = classRepo;
   }

   public List<ScheduledClass> getAllClasses() {
      return classRepo.findAllWithCourse();
   }
}
