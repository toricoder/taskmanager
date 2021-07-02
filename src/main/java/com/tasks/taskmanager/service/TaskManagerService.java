package com.tasks.taskmanager.service;

import com.tasks.taskmanager.common.errorhandling.TaskNotFoundException;
import com.tasks.taskmanager.persistence.TaskRepository;
import com.tasks.taskmanager.rest.Status;
import com.tasks.taskmanager.persistence.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class TaskManagerService {

    Logger logger = LogManager.getLogger(TaskManagerService.class);

    @Autowired
    private TaskRepository taskRepository;

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public List<Task> getAllTasks(String status) {
        if(status != null) {
            Optional<Status> statusFound = Arrays.stream(Status.values()).filter(e -> e.name().equalsIgnoreCase(status)).findFirst();
            if(statusFound.isPresent()) {
                return taskRepository.findByStatus(statusFound.get());
            }
            logger.info("Task not found, retrieving empty list");
            return Collections.emptyList();
        }
        return taskRepository.findAll();
    }

    public void updateTasks(List<Task> tasks) {
        for (Task task: tasks) {
            if(taskRepository.existsById(task.getId())) {
                taskRepository.save(task);
            } else {
                logger.error("Task not found {}", task);
                throw new TaskNotFoundException("Task not found " + task.getId());
            }
        }
    }

    public void deleteTask(String taskId) {
       if(taskId != null) {
           if(taskRepository.existsById(taskId)) {
               Task task = taskRepository.findById(taskId);
               taskRepository.delete(task);
               logger.info("ID Task deleted {}", taskId);
           }  else {
               logger.info("Task not found, nothing to delete");
               throw new TaskNotFoundException("Task not found " + taskId);
           }
       }
    }
}
