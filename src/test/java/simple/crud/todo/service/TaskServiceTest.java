package simple.crud.todo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.test.context.ActiveProfiles;
import simple.crud.todo.dto.TaskCreatedDTO;
import simple.crud.todo.dto.TaskDTO;
import simple.crud.todo.dto.TaskUpdatedDTO;
import simple.crud.todo.mapper.TaskMapper;
import simple.crud.todo.model.Task;
import simple.crud.todo.repository.TaskRepository;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class TaskServiceTest {
    @Mock
    private TaskMapper taskMapper;

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private Task task;
    private TaskDTO taskDTO;
    private TaskCreatedDTO taskCreatedDTO;
    private TaskUpdatedDTO taskUpdatedDTO;

    @BeforeEach
    public void setUp() {
        task = new Task();
        task.setId(1L);
        task.setTitle("Test Task");
        task.setContent("Test Content");

        taskDTO = new TaskDTO();
        taskDTO.setTitle("Test Task");
        taskDTO.setContent("Test Content");

        taskCreatedDTO = new TaskCreatedDTO();
        taskCreatedDTO.setTitle("New Task");
        taskCreatedDTO.setContent("New Content");

        taskUpdatedDTO = new TaskUpdatedDTO();
        taskUpdatedDTO.setTitle(JsonNullable.of("Updated Task"));
        taskUpdatedDTO.setContent(JsonNullable.of("Updated Content"));
    }

    @Test
    public void testGetAll() {
        when(taskRepository.getAll()).thenReturn(Collections.singletonList(task));
        when(taskMapper.map(task)).thenReturn(taskDTO);

        List<TaskDTO> result = taskService.getAll();

        assertEquals(1, result.size());
        assertEquals(taskDTO, result.get(0));
        verify(taskRepository).getAll();
        verify(taskMapper).map(task);
    }

    @Test
    public void testGetById() {
        when(taskRepository.getById(anyLong())).thenReturn(task);
        when(taskMapper.map(task)).thenReturn(taskDTO);

        TaskDTO result = taskService.getById(1L);

        assertEquals(taskDTO, result);
        verify(taskRepository).getById(1L);
        verify(taskMapper).map(task);
    }

    @Test
    public void testCreate() {
        when(taskMapper.map(taskCreatedDTO)).thenReturn(task);
        when(taskRepository.save(task)).thenReturn(task);
        when(taskMapper.map(task)).thenReturn(taskDTO);

        TaskDTO result = taskService.create(taskCreatedDTO);

        assertEquals(taskDTO, result);
        verify(taskMapper).map(taskCreatedDTO);
        verify(taskRepository).save(task);
        verify(taskMapper).map(task);
    }

    @Test
    public void testUpdateById() {
        when(taskRepository.getById(anyLong())).thenReturn(task);
        when(taskMapper.map(task)).thenReturn(taskDTO);

        taskService.updateById(taskUpdatedDTO, 1L);

        verify(taskRepository).getById(1L);
        verify(taskMapper).update(taskUpdatedDTO, task);
        verify(taskRepository).updateTask(task);
        verify(taskMapper).map(task);
    }

    @Test
    public void testDeleteById() {
        taskService.deleteById(1L);

        verify(taskRepository).deleteById(1L);
    }
}