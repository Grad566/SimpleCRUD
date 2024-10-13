package simple.crud.todo.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import simple.crud.todo.dto.TaskCreatedDTO;
import simple.crud.todo.dto.TaskDTO;
import simple.crud.todo.dto.TaskUpdatedDTO;
import simple.crud.todo.mapper.TaskMapper;
import simple.crud.todo.model.Task;
import simple.crud.todo.repository.TaskRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Getter
@Setter
public class TaskService {
    private final TaskMapper taskMapper;
    private final TaskRepository taskRepository;

    public List<TaskDTO> getAll() {
        return taskRepository.getAll().stream().map(taskMapper::map).toList();
    }

    public TaskDTO getById(Long id) {
        return taskMapper.map(taskRepository.getById(id));
    }

    public TaskDTO create(TaskCreatedDTO dto) {
        Task task = taskMapper.map(dto);
        return taskMapper.map(taskRepository.save(task));
    }

    public TaskDTO updateById(TaskUpdatedDTO dto, Long id) {
        Task task = taskRepository.getById(id);
        taskMapper.update(dto, task);
        return taskMapper.map(taskRepository.updateTask(task));
    }

    public void deleteById(Long id) {
        taskRepository.deleteById(id);
    }
}
