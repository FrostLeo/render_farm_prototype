package server.dto;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.entity.Task;
import server.entity.User;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static server.entity.TaskState.RENDERING;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDtoTest {
    UserDto userDto;

    @BeforeEach
    void setUp() {
        List<Task> taskList = new ArrayList<>();
        taskList.add(new Task(1L, "Write Java code", RENDERING));
        userDto = UserDto.toDto(new User(5L, "Leo", taskList));
    }

    @Test
    public void testToDto() {
        assertEquals(5L, userDto.getId());
        assertEquals("Leo", userDto.getUsername());

        TaskDto taskDto = userDto.getTasksDto().get(0);
        assertEquals(1L, taskDto.getId());
        assertEquals("Write Java code", taskDto.getName());
        assertEquals(RENDERING, taskDto.getState());
    }

    @Test
    void toViewTest() {
        String expectedView = "Пользователь = Leo (id = 5), список задач:\n" +
                "id = 1, task = Write Java code, state = RENDERING\n";
        assertEquals(userDto.toView(), expectedView);
    }
}

