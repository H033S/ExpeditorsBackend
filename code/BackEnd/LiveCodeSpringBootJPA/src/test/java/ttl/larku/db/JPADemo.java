package ttl.larku.db;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ttl.larku.domain.Course;
import ttl.larku.domain.ScheduledClass;

import static java.lang.System.out;

@SpringBootTest
public class JPADemo {

   private EntityManagerFactory emf;

   @BeforeEach
   public void beforeEach() {
      String pw = System.getenv("DB_PASSWORD");

      var props = Map.of(
            //"jakarta.persistence.jdbc.url", "jdbc:postgresql://localhost:5432/larku",
            "jakarta.persistence.jdbc.url", "jdbc:postgresql://localhost:5433/larku",
            "jakarta.persistence.jdbc.user", "larku",
            "jakarta.persistence.jdbc.password", pw,
            "hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect",

            "jakarta.persistence.spi.PersistenceProvider", "org.hibernate.jpa.HibernatePersistenceProvider"
      );

      //emf = Persistence.createEntityManagerFactory("LarkUPU_SE");
      emf = Persistence.createEntityManagerFactory("LarkUPU_SE", props);
   }

   @Test
   public void dumpAllCourses() {
      try(EntityManager em = emf.createEntityManager();) {

         TypedQuery<Course> query = em.createQuery("select c from Course c", Course.class);

         List<Course> result = query.getResultList();

         out.println("result: " + result.size());
         result.forEach(out::println);
      }
   }

   @Test
   public void addACourse() {
      try(EntityManager em = emf.createEntityManager();) {

         em.getTransaction().begin();

         Course course = new Course("BOT-505", "Lots of Botany", 2.5F);

         em.persist(course);

         em.getTransaction().commit();

         dumpAllCourses();
      }
   }

   @Test
   public void updateCourse() {
      try(EntityManager em = emf.createEntityManager();) {
         Course course = em.find(Course.class, 1);

         course.setCode("XXX-30303");

         em.getTransaction().begin();
         em.getTransaction().commit();

         int stop = 10;
      }
   }

   @Test
   public void dumpScheduledClasses() {
      try(EntityManager em = emf.createEntityManager();) {

         //TypedQuery<ScheduledClass> query = em.createQuery("select c from ScheduledClass c", ScheduledClass.class);
         TypedQuery<ScheduledClass> query =
               em.createQuery("select sc from ScheduledClass sc join fetch sc.course", ScheduledClass.class);

         List<ScheduledClass> result = query.getResultList();

         out.println("result: " + result.size());
         result.forEach(out::println);
      }
   }
}
