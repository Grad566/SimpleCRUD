package simple.crud.todo.repository;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import simple.crud.todo.mapper.TaskRowMapper;
import simple.crud.todo.model.Task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class TaskRepository {
    private final SessionFactory sessionFactory;

    public Task save(Task task) {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(task);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
        return task;
    }

    @Cacheable(value = "tasks", key = "#id")
    public Task getById(Long id) {
        try(Session session = sessionFactory.openSession()) {
            return session.get(Task.class, id);
        }
    }

    @Cacheable(value = "tasks")
    public List<Task> getAll() {
        try(Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Tasks", Task.class).list();
        }
    }

    @CachePut(value = "tasks", key = "#updatedTask.id")
    public Task updateTask(Task updatedTask) {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(updatedTask);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }

        return updatedTask;
    }

    @CacheEvict(value = "tasks", key = "#id")
    public void deleteById(Long id) {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Task task = session.get(Task.class, id);
            if (task != null) {
                session.remove(task);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public Task getByTaskName(String name) {
        try(Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Tasks WHERE title = :title", Task.class)
                    .setParameter("title", name)
                    .uniqueResult();
        }
    }
}
