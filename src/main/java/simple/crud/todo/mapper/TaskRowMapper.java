package simple.crud.todo.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import simple.crud.todo.model.Task;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TaskRowMapper implements RowMapper<Task> {

    @Override
    public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
        Task task = new Task();
        task.setId(rs.getLong("id"));
        task.setTitle(rs.getString("title"));
        task.setContent(rs.getString("content"));

        return task;
    }

}
