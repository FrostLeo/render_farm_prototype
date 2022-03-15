package server.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import server.dto.TaskDto;
import server.dto.UserDto;
import server.entity.Task;
import server.entity.User;
import server.exception.UserNotFoundedException;
import server.exception.UsernameIsAlreadyTaken;
import server.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static server.entity.TaskState.RENDERING;

@SpringBootTest
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserServiceTest {
    @Autowired
    private UserService userService;
    @MockBean
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        List<Task> taskList = new ArrayList<>();
        taskList.add(new Task(1L,"Perform a test task", RENDERING));
        when(userRepository.findByUsername("Leo"))
                .thenReturn(new User(5L,"Leo", taskList));
    }

    @Test
    public void getUserTest() {
        UserDto user = userService.getUser("Leo");
        assertThat(user.getId()).isEqualTo(5);
        assertThat(user.getUsername()).isEqualTo("Leo");

        TaskDto task = user.getTasksDto().get(0);
        assertThat(task.getId()).isEqualTo(1);
        assertThat(task.getName()).isEqualTo("Perform a test task");
        assertThat(task.getState()).isEqualTo(RENDERING);
    }

    @Test
    public void updateUserTest() {
        UserDto user = userService.updateUser("Leo", "Write Java code");
        assertThat(user.getId()).isEqualTo(5);
        assertThat(user.getUsername()).isEqualTo("Leo");

        TaskDto firstTask = user.getTasksDto().get(0);
        assertThat(firstTask.getId()).isEqualTo(1);
        assertThat(firstTask.getName()).isEqualTo("Perform a test task");
        assertThat(firstTask.getState()).isEqualTo(RENDERING);

        TaskDto secondTask = user.getTasksDto().get(1);
        // без проверки id, поскольку id присваивается при записи в БД
        assertThat(secondTask.getName()).isEqualTo("Write Java code");
        assertThat(secondTask.getState()).isEqualTo(RENDERING);
    }

    @Test
    public void saveUserIsAlreadyTakenExceptionTest() {
        Throwable thrown = assertThrows(UsernameIsAlreadyTaken.class, () ->{
            userService.saveUser("Leo");
        });
        assertThat(thrown.getMessage()).isEqualTo("Пользователь с таким именем уже существует");
    }

    @Test
    public void getUserNotFoundedExceptionTest() {
        Throwable thrown = assertThrows(UserNotFoundedException.class, () ->{
            userService.getUser("Egor");
        });
        assertThat(thrown.getMessage()).isEqualTo("Пользователь с таким именем не зарегистрирован");
    }

    @Test
    public void updateUserNotFoundedExceptionTest() {
        Throwable thrown = assertThrows(UserNotFoundedException.class, () ->{
            userService.updateUser("Egor", "Nothing");
        });
        assertThat(thrown.getMessage()).isEqualTo("Пользователь с таким именем не зарегистрирован");
    }
}
