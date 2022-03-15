package server.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import server.entity.Task;
import server.entity.User;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {
    Long id;
    String username;
    List<TaskDto> tasksDto;

    public static UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .tasksDto(listEntityToDto(user.getTasks()))
                .build();
    }

    private static List<TaskDto> listEntityToDto(List<Task> taskList) {
        return taskList.stream()
                .map(TaskDto::toDto)
                .collect(Collectors.toList());
    }

    public String toView() {
        StringBuilder taskBuilder = new StringBuilder();
        tasksDto.forEach(taskDto -> taskBuilder.append(taskDto.toView()).append("\n"));
        return  "Пользователь = " + username +
                " (id = " + id +
                "), список задач:\n" + taskBuilder;
    }
}
