package simple.crud.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import simple.crud.todo.model.Task;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> findByTitle(String title);
}
