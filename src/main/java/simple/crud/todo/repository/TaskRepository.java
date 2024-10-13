package simple.crud.todo.repository;

import lombok.RequiredArgsConstructor;
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
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private final TaskRowMapper taskRowMapper;

    @Autowired
    public TaskRepository(JdbcTemplate jdbcTemplate, TaskRowMapper taskRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("tasks")
                .usingGeneratedKeyColumns("ID");
        this.taskRowMapper = taskRowMapper;
    }


    public Task save(Task task) {
        Map<String, String> params = new HashMap<>();
        params.put("title", task.getTitle());
        params.put("content", task.getContent());

        Number id = jdbcInsert.executeAndReturnKey(params);
        task.setId((Long) id);

        return task;
    }

    @Cacheable(value = "tasks", key = "#id")
    public Task getById(Long id) {
        String sql = "SELECT * FROM tasks WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, taskRowMapper, id);
    }

    @Cacheable(value = "tasks")
    public List<Task> getAll() {
        String sql = "SELECT * FROM tasks";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Task task = new Task();
            task.setId(rs.getLong("id"));
            task.setTitle(rs.getString("title"));
            task.setContent(rs.getString("content"));
            return task;
        });
    }

    @CachePut(value = "tasks", key = "#updatedTask.id")
    public Task updateTask(Task updatedTask) {
        String sql = "UPDATE tasks SET title = ?, content = ? WHERE id = ?";
        jdbcTemplate.update(sql, updatedTask.getTitle(), updatedTask.getContent(), updatedTask.getId());
        return updatedTask;
    }

    @CacheEvict(value = "tasks", key = "#id")
    public void deleteById(Long id) {
        String sql = "DELETE FROM tasks WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public Task getByTaskName(String name) {
        String sql = "SELECT * FROM tasks WHERE title = ?";
        return jdbcTemplate.queryForObject(sql, taskRowMapper, name);
    }
}
