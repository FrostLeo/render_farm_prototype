package server.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import server.entity.Task;
import server.entity.TaskState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.repository.TaskRepository;

@Log
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaskServiceImpl implements TaskService {
    TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task saveTask(String taskName) {
        Task newTask = createTask(taskName);
        taskRepository.saveAndFlush(newTask);
        return newTask;
    }

    private Task createTask(String taskName) {
        Task newTask = new Task();
        newTask.setName(taskName);
        newTask.setState(TaskState.RENDERING);
        return newTask;
    }
}

