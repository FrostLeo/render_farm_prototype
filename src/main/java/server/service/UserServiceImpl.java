package server.service;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import server.dto.UserDto;
import server.entity.Task;
import server.entity.TaskState;
import server.entity.User;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.exception.UserNotFoundedException;
import server.exception.UsernameIsAlreadyTaken;
import server.repository.TaskRepository;
import server.repository.UserRepository;

import java.util.ArrayList;

@Log
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    TaskRepository taskRepository;
    TaskService taskService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, TaskRepository taskRepository, TaskService taskService) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.taskService = taskService;
    }

    @Override
    public void saveUser(String username) {
        User userFromDB = userRepository.findByUsername(username);

        if (userFromDB != null) {
            throw new UsernameIsAlreadyTaken();
        }

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setTasks(new ArrayList<>());
        userRepository.save(newUser);
        log.info("Зарегистрирован новый пользователь: " + username);
    }

    @Override
    public UserDto getUser(String username) {
        User currentUser = getUserFromDatabase(username);
        return UserDto.toDto(currentUser);
    }

    @Override
    public UserDto updateUser(String username, String taskName) {
        User currentUser = getUserFromDatabase(username);
        Task newTask = taskService.saveTask(taskName);
        currentUser.getTasks().add(newTask);
        userRepository.saveAndFlush(currentUser);

        startExecutorThread(newTask);
        log.info("Задача: " + taskName + ", принята для исполнения пользователем: " + username);
        return UserDto.toDto(currentUser);
    }

    private User getUserFromDatabase(String username) {
        User currentUser = userRepository.findByUsername(username);
        if (currentUser == null) {
            throw new UserNotFoundedException();
        }
        return currentUser;
    }

    private void startExecutorThread(Task executingTask) {
        Thread newExecutorThread = new ExecutorTaskThread(executingTask);
        newExecutorThread.setDaemon(true);
        newExecutorThread.start();
    }

    @AllArgsConstructor
    private class ExecutorTaskThread extends Thread {
        private Task executingTask;

        @Override
        public void run() {
            int minNum = 60000; // = 1 минута
            int maxNum = 300000; // = 5 минут
            int randomTimeInterval = minNum + (int) (Math.random() * maxNum);

            try {
                Thread.sleep(randomTimeInterval);
                log.info("Задача " + executingTask.getName() + " выполнена.");
            } catch (InterruptedException e) {
                log.info("Задача " + executingTask.getName() + " выполнена досрочно");
            }
            executingTask.setState(TaskState.COMPLETE);
            taskRepository.saveAndFlush(executingTask);
        }
    }
}
