package expeditors.backend.jconfig;

import expeditors.backend.dao.StudentDAO;
import expeditors.backend.dao.inmemory.InMemoryStudentDAO;
import expeditors.backend.service.StudentService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"expeditors.backend"})
public class LarkUConfig {
   /*
    <bean id="inMemoryStudentDAO" class="expeditors.backend.dao.inmemory.InMemoryStudentDAO"/>
    */

   @Bean
   public StudentDAO studentDAO() {
      var dao = new InMemoryStudentDAO();
      return dao;
   }

   /*
    <bean id="studentService" class="expeditors.backend.service.StudentService" >
    <property name="studentDAO" ref="inMemoryStudentDAO"/>
    </bean>
    */

   @Bean
   public StudentService studentService() {
      StudentService newService = new StudentService();

      StudentDAO dao = studentDAO();

      newService.setStudentDAO(dao);
      return newService;
   }
}
