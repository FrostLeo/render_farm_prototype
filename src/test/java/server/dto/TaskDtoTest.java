package server.dto;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.entity.Task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static server.entity.TaskState.COMPLETE;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskDtoTest {
    TaskDto taskDto;

    @BeforeEach
    void setUp() {
        taskDto = TaskDto.toDto(new Task(2L, "Perform test task", COMPLETE));
    }

    @Test
    public void testToDto() {
        assertEquals(2L, taskDto.getId());
        assertEquals("Perform test task", taskDto.getName());
        assertEquals(COMPLETE, taskDto.getState());
    }

    @Test
    void toViewTest() {
        String expectedView = "id = 2, task = Perform test task, state = COMPLETE";
        assertEquals(taskDto.toView(), expectedView);
    }
}