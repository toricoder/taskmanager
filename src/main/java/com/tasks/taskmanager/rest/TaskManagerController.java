package com.tasks.taskmanager.rest;

import com.tasks.taskmanager.persistence.Task;
import com.tasks.taskmanager.service.TaskManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/tasks", produces = MediaType.APPLICATION_JSON_VALUE)
public class TaskManagerController {

    @Autowired
    private TaskManagerService taskManagerService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Task> getAllTasks(@RequestParam(required = false) String status) {
        return taskManagerService.getAllTasks(status);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Task addTask(@RequestBody Task task) {
        return taskManagerService.createTask(task);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateTask(@RequestBody List<Task> taskList) {
        taskManagerService.updateTasks(taskList);
    }

    @DeleteMapping("/{taskId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable("taskId") String taskId) {
        taskManagerService.deleteTask(taskId);
    }
}
