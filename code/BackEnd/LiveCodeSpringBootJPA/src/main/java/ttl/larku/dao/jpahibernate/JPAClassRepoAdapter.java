package ttl.larku.dao.jpahibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import ttl.larku.dao.BaseDAO;
import ttl.larku.dao.repository.ClassRepo;
import ttl.larku.domain.ScheduledClass;

@Repository
@Profile("production")
public class JPAClassRepoAdapter implements BaseDAO<ScheduledClass> {

    @Autowired
    private ClassRepo classRepo;

    private String from = "CRA";

    @Override
    public boolean update(ScheduledClass updateObject) {
        if(classRepo.existsById(updateObject.getId())) {
            classRepo.save(updateObject);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(ScheduledClass scheduledClass) {
        if(classRepo.existsById(scheduledClass.getId())) {
            classRepo.delete(scheduledClass);
            return true;
        }
        return false;
    }

    @Override
    public ScheduledClass insert(ScheduledClass newObject) {
        newObject = classRepo.save(newObject);
        return newObject;
    }

    @Override
    public ScheduledClass findById(int id) {
        return classRepo.findById(id).orElse(null);
    }

    @Override
    public List<ScheduledClass> findAll() {
        return classRepo.findAll();
    }

    @Override
    public void deleteStore() {
        classRepo.deleteAll();
    }

    @Override
    public void createStore() {
    }

}
