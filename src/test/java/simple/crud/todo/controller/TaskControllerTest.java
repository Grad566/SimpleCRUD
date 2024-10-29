package simple.crud.todo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.testcontainers.containers.PostgreSQLContainer;
import simple.crud.todo.dto.TaskCreatedDTO;
import simple.crud.todo.dto.TaskUpdatedDTO;
import simple.crud.todo.model.Task;
import simple.crud.todo.repository.TaskRepository;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ObjectMapper om;

    private static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("springjdbc")
            .withUsername("admin")
            .withPassword("qwerty");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        postgresContainer.start();
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresContainer::getUsername);
        registry.add("spring.datasource.password", postgresContainer::getPassword);

        registry.add("hibernate.url", postgresContainer::getJdbcUrl);
        registry.add("hibernate.username", postgresContainer::getUsername);
        registry.add("hibernate.password", postgresContainer::getPassword);
    }

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetById() throws Exception {
        Task task = new Task();
        task.setContent("test");
        task.setTitle("test");
        taskRepository.save(task);

        mockMvc.perform(get("/api/tasks/" + task.getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreate() throws Exception {
        TaskCreatedDTO data = new TaskCreatedDTO();
        data.setTitle("title");
        data.setContent("content");

        MockHttpServletRequestBuilder request = post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(data));

        mockMvc.perform(request).andExpect(status().isCreated());

        Task task = taskRepository.getByTaskName(data.getTitle());

        assertNotNull(task);
        assertThat(task.getTitle()).isEqualTo(data.getTitle());
        assertThat(task.getContent()).isEqualTo(data.getContent());
    }

    @Test
    public void testUpdate() throws Exception {
        Task task = new Task();
        task.setContent("test2");
        task.setTitle("test2");
        taskRepository.save(task);

        var data = new TaskUpdatedDTO();
        data.setContent(JsonNullable.of("fbi"));
        data.setTitle(JsonNullable.of("oop"));

        MockHttpServletRequestBuilder request = put("/api/tasks/" + task.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(data));

        mockMvc.perform(request);

        Task updatedTask = taskRepository.getById(task.getId());

        assertNotNull(updatedTask);
        assertThat(updatedTask.getContent()).isEqualTo(data.getContent().get());
        assertThat(updatedTask.getTitle()).isEqualTo(data.getTitle().get());
    }

    @Test
    public void testDelete() throws Exception {
        Task task = new Task();
        task.setContent("test3");
        task.setTitle("test3");
        taskRepository.save(task);

        mockMvc.perform(delete("/api/tasks/" + task.getId()))
                .andExpect(status().isNoContent());
    }


}