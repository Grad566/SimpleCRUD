package simple.crud.todo.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import simple.crud.todo.dto.TaskCreatedDTO;
import simple.crud.todo.dto.TaskDTO;
import simple.crud.todo.dto.TaskUpdatedDTO;
import simple.crud.todo.service.TaskService;
import simple.crud.todo.swagger.TaskEndpointDocumentation;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/tasks")
public class TaskController implements TaskEndpointDocumentation {
    private final TaskService taskService;

    @GetMapping
    public List<TaskDTO> getAll() {
        return taskService.getAll();
    }

    @GetMapping(path = "/{id}")
    public TaskDTO getById(@PathVariable Long id) {
        return taskService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDTO create(@RequestBody TaskCreatedDTO dto) {
        return taskService.create(dto);
    }

    @PutMapping(path = "/{id}")
    public TaskDTO updateById(@RequestBody TaskUpdatedDTO dto, @PathVariable Long id) {
        return taskService.updateById(dto, id);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        taskService.deleteById(id);
    }
}
