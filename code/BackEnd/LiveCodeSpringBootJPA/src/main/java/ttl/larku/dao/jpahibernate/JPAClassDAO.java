package ttl.larku.dao.jpahibernate;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import ttl.larku.dao.BaseDAO;
import ttl.larku.domain.ScheduledClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class JPAClassDAO implements BaseDAO<ScheduledClass> {

    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public boolean update(ScheduledClass updateObject) {
        entityManager.merge(updateObject);
        return true;
    }

    @Override
    public boolean delete(ScheduledClass sc) {
        entityManager.remove(sc);
        return true;
    }

    @Override
    public ScheduledClass insert(ScheduledClass newObject) {
        entityManager.persist(newObject);
        return newObject;
    }

    @Override
    public ScheduledClass findById(int id) {
        return entityManager.find(ScheduledClass.class, id);
    }

    @Override
    public List<ScheduledClass> findAll() {
        String queryString = "select sc from ScheduledClass sc join fetch sc.course";
        TypedQuery<ScheduledClass> query = entityManager.createQuery(queryString, ScheduledClass.class);
        List<ScheduledClass> result = query.getResultList();

        return result;
    }

    @Override
    public void deleteStore() {
//        entityManager.re
    }

    @Override
    public void createStore() {
    }
}
