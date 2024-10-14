package simple.crud.todo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

@Getter
@Setter
@Schema(description = "сущность для обновления tusk")
public class TaskUpdatedDTO {
    private JsonNullable<String> title;
    private JsonNullable<String> content;
}
