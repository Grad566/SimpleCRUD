package simple.crud.todo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "сущность для передачи tusk")
public class TaskDTO {
    private String title;
    private String content;
}
