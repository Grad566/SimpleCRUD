package simple.crud.todo.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import simple.crud.todo.dto.TaskCreatedDTO;
import simple.crud.todo.dto.TaskDTO;
import simple.crud.todo.dto.TaskUpdatedDTO;
import simple.crud.todo.model.Task;

@Mapper(
        uses = {JsonNullableMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class TaskMapper {
    public abstract Task map(TaskCreatedDTO dto);
    public abstract TaskDTO map(Task model);
    public abstract void update(TaskUpdatedDTO dto, @MappingTarget Task model);
}
