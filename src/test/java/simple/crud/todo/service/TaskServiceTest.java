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
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doAnswer;
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
        when(taskRepository.findAll()).thenReturn(Collections.singletonList(task));
        when(taskMapper.map(task)).thenReturn(taskDTO);

        List<TaskDTO> result = taskService.getAll();

        assertEquals(1, result.size());
        assertEquals(taskDTO, result.get(0));
        verify(taskRepository).findAll();
        verify(taskMapper).map(task);
    }

    @Test
    public void testGetById() {
        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(task));
        when(taskMapper.map(task)).thenReturn(taskDTO);

        TaskDTO result = taskService.getById(1L);

        assertEquals(taskDTO, result);
        verify(taskRepository).findById(1L);
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
        Task task1 = new Task();
        task1.setId(1L);
        task1.setTitle("Test Task");
        task1.setContent("Test Content");

        TaskDTO taskDTO1 = new TaskDTO();
        taskDTO1.setTitle("Updated Task");
        taskDTO1.setContent("Updated Content");

        TaskUpdatedDTO taskUpdatedDTO1 = new TaskUpdatedDTO();
        taskUpdatedDTO1.setTitle(JsonNullable.of("Updated Task"));
        taskUpdatedDTO1.setContent(JsonNullable.of("Updated Content"));

        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(task1));

        doAnswer(invocation -> {
            TaskUpdatedDTO dto = invocation.getArgument(0);
            Task t = invocation.getArgument(1);
            t.setTitle(dto.getTitle().get());
            t.setContent(dto.getContent().get());
            return null;
        }).when(taskMapper).update(any(TaskUpdatedDTO.class), any(Task.class));

        when(taskRepository.save(task1)).thenReturn(task1);
        when(taskMapper.map(task1)).thenReturn(taskDTO1);

        TaskDTO result = taskService.updateById(taskUpdatedDTO1, 1L);

        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("Updated Task");
        assertThat(result.getContent()).isEqualTo("Updated Content");

        verify(taskRepository).findById(1L);
        verify(taskMapper).update(taskUpdatedDTO1, task1);
        verify(taskRepository).save(task1);
        verify(taskMapper).map(task1);
    }


    @Test
    public void testDeleteById() {
        taskService.deleteById(1L);

        verify(taskRepository).deleteById(1L);
    }
}

