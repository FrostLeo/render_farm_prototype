package server.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import server.entity.Task;
import server.entity.TaskState;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskDto {
    Long id;
    String name;
    TaskState state;

    public static TaskDto toDto(Task task) {
        return TaskDto.builder()
                .id(task.getId())
                .name(task.getName())
                .state(task.getState())
                .build();
    }

    public String toView() {
        return  "id = " + id +
                ", task = " + name +
                ", state = " + state;
    }
}
