package server.controller;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.dto.UserDto;
import server.exception.UserNotFoundedException;
import server.exception.UsernameIsAlreadyTaken;
import server.service.UserServiceImpl;

@RestController
@RequestMapping("/user")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RenderFarmRestController {
    UserServiceImpl userServiceImpl;

    @Autowired
    public RenderFarmRestController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @PostMapping("/registration")
    public String registration(@RequestBody String username) {
        userServiceImpl.saveUser(username);
        return "Зарегистрирован новый пользователь";
    }

    @PostMapping("/{username}/new_task")
    public UserDto addTask(@PathVariable String username,
                           @RequestBody String taskName) {
        return userServiceImpl.updateUser(username, taskName);
    }

    @GetMapping(path = "{username}")
    public UserDto getUserTasks(@PathVariable String username) {
        return userServiceImpl.getUser(username);
    }

    @ExceptionHandler({UserNotFoundedException.class, UsernameIsAlreadyTaken.class})
    public ResponseEntity<String> handleConstraintViolationException(RuntimeException e) {
        return new ResponseEntity<>("Запрос не может быть выполнен: " + e.getMessage(),
                HttpStatus.BAD_REQUEST);
    }
}
