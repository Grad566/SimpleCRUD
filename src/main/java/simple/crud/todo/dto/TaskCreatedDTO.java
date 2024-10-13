package simple.crud.todo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskCreatedDTO {
    private String title;
    private String content;
}
