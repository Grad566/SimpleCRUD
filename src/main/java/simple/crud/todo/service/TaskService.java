package simple.crud.todo.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import simple.crud.todo.dto.TaskCreatedDTO;
import simple.crud.todo.dto.TaskDTO;
import simple.crud.todo.dto.TaskUpdatedDTO;
import simple.crud.todo.exception.ResourceNotFoundException;
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

    @Cacheable(value = "tasks")
    public List<TaskDTO> getAll() {
        return taskRepository.findAll().stream().map(taskMapper::map).toList();
    }

    @Cacheable(value = "tasks", key = "#id")
    public TaskDTO getById(Long id) {
        return taskMapper.map(taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task with id: " + id + " not found")));
    }

    public TaskDTO create(TaskCreatedDTO dto) {
        Task task = taskMapper.map(dto);
        return taskMapper.map(taskRepository.save(task));
    }

    @CachePut(value = "tasks", key = "#id")
    public TaskDTO updateById(TaskUpdatedDTO dto, Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task with id: " + id + " not found"));
        taskMapper.update(dto, task);
        return taskMapper.map(taskRepository.save(task));
    }

    @CacheEvict(value = "tasks", key = "#id")
    public void deleteById(Long id) {
        taskRepository.deleteById(id);
    }
}
