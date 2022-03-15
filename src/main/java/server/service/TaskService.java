package server.service;

import server.entity.Task;

public interface TaskService {
    Task saveTask(String taskName);
}
