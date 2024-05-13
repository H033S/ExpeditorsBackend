package ttl.larku.controllers;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ttl.larku.domain.ScheduledClass;
import ttl.larku.service.ClassRepoService;

@RestController
@RequestMapping("/classrepo")
public class ClassRepoController {

   private ClassRepoService classRepoService;

   public ClassRepoController(ClassRepoService classRepoService) {
      this.classRepoService = classRepoService;
   }

   @GetMapping
   public List<ScheduledClass> getAllClasses() {
      return classRepoService.getAllClasses();
   }
}
