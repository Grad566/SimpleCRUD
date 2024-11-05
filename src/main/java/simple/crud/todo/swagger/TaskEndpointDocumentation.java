package simple.crud.todo.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import simple.crud.todo.dto.TaskCreatedDTO;
import simple.crud.todo.dto.TaskDTO;
import simple.crud.todo.dto.TaskUpdatedDTO;

import java.util.List;

@Tag(name = "Task Controller", description = "CRUD operations for the task entity")
public interface TaskEndpointDocumentation {
    @Operation(
            summary = "Get all tasks",
            description = "Returns a list of all tasks.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of tasks retrieved successfully")
            }
    )
    List<TaskDTO> getAll();

    @Operation(
            summary = "Get task by ID",
            description = "Returns a task by the specified ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Task found successfully"),
                    @ApiResponse(responseCode = "404", description = "Task not found")
            }
    )
    TaskDTO getById(@Parameter(description = "Task ID", required = true) Long id);

    @Operation(
            summary = "Create a new task",
            description = "Creates a new task.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Task created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid data for task creation")
            }
    )
    TaskDTO create(@Parameter(description = "Data for creating a task", required = true) TaskCreatedDTO dto);

    @Operation(
            summary = "Update task by ID",
            description = "Updates an existing task by the specified ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Task updated successfully"),
                    @ApiResponse(responseCode = "404", description = "Task not found"),
                    @ApiResponse(responseCode = "400", description = "Invalid data for task update")
            }
    )
    TaskDTO updateById(
            @Parameter(description = "Data for updating a task", required = true) TaskUpdatedDTO dto,
            @Parameter(description = "Task ID", required = true) Long id);

    @Operation(
            summary = "Delete task by ID",
            description = "Deletes a task by the specified ID.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Task deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Task not found")
            }
    )
    void deleteById(@Parameter(description = "Task ID", required = true) Long id);
}
