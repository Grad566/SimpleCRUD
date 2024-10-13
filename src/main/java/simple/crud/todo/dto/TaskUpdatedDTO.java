package simple.crud.todo.dto;

import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

@Getter
@Setter
public class TaskUpdatedDTO {
    private JsonNullable<String> title;
    private JsonNullable<String> content;
}
