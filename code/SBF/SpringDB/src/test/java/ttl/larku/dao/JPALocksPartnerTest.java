package ttl.larku.dao;

import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.FlushModeType;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceUnit;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ttl.larku.controllers.rest.RestResultWrapper;
import ttl.larku.controllers.rest.RestResultWrapper.Status;
import ttl.larku.domain.Course;
import ttl.larku.domain.ScheduledClass;
import ttl.larku.domain.Student;
import ttl.larku.domain.StudentVersioned;
import ttl.larku.service.StudentDaoService;
import ttl.larku.sql.SqlScriptBase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Tag("dao")
public class JPALocksPartnerTest { // extends {

	@PersistenceUnit
	private EntityManagerFactory emf;

	@PersistenceContext
	private EntityManager entityManager;

	@Resource(name = "studentDaoService")
	private StudentDaoService studentService;



   /**
    * This test should be run in conjunction with the one in
	 * JPALocksTest::testLockModesWithOurOwnEntitManager.  Run that to the
	 * break point at the flush, then run this with a different lock mode to see
	 * how far it progresses.
	 */
	@Test
	public void testLockModesWithOurOwnEntityManger2() {
		EntityManager myManager = emf.createEntityManager();
		myManager.getTransaction().begin();

		TypedQuery<StudentVersioned> query = myManager.createQuery("select s from StudentVersioned s", StudentVersioned.class);
		List<StudentVersioned> students = query.getResultList();
		StudentVersioned s = myManager.find(StudentVersioned.class, 1, LockModeType.PESSIMISTIC_READ);
//		StudentVersioned s = myManager.find(StudentVersioned.class, 1, LockModeType.PESSIMISTIC_WRITE);
//		StudentVersioned s = myManager.find(StudentVersioned.class, 1, LockModeType.OPTIMISTIC);
		s.setName("Myrtle");

		//Put a Break point here at the flush.
		//You should be able to read the data from psql for student with id 1, even though
		//we have the PESSIMISTIC_READ lock.
		//If you try and update the Student from psql, you should block till this
		//transaction commits.
		myManager.flush();
		myManager.getTransaction().commit();
		myManager.clear();

		s = myManager.find(StudentVersioned.class, 1);
		assertEquals("Myrtle", s.getName());
		System.out.println("student at end is " + s);

		myManager.close();
	}
}
