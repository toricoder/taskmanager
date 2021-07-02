package com.tasks.taskmanager.service;

import com.tasks.taskmanager.common.errorhandling.TaskNotFoundException;
import com.tasks.taskmanager.persistence.Task;
import com.tasks.taskmanager.persistence.TaskRepository;
import com.tasks.taskmanager.rest.Status;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskManagerServiceUnitTest {

    @InjectMocks
    private TaskManagerService taskManagerService;

    @Mock
    private TaskRepository taskRepository;


    @Test
    public void getAllTasksNoFilterSuccess() {

        List<Task> tasks = Arrays.asList(new Task("TSK-1234", "Test Title", "Test description", Status.CLOSED));
        when(taskRepository.findAll()).thenReturn(tasks);
        List<Task> response = taskManagerService.getAllTasks(null);
        assertTrue(response.size() > 0);
    }

    @Test
    public void getAllTaskFilteringByStatus() {

        Task closedTask = new Task("TSK-1234", "Test Title", "Test description", Status.CLOSED);
        when(taskRepository.findByStatus(Status.CLOSED)).thenReturn(List.of(closedTask));
        List<Task> response = taskManagerService.getAllTasks("closed");
        assertTrue(response.size() > 0);
        assertTrue(response.get(0).getId().equals("TSK-1234"));
    }

    @Test
    public void getAllTaskFilteringByWrongStatusRetrievesEmptyList() {
        List<Task> response = taskManagerService.getAllTasks("wrong");

        assertTrue(response.size() == 0);
        verifyNoInteractions(taskRepository);
    }

    @Test
    public void updateTaskSuccess() {
        Task closedTask = new Task("TSK-1234", "Test Title", "Test description", Status.CLOSED);
        Task openTask = new Task("TSK-4567", "Another Title", "Open Test description", Status.OPEN);
        List<Task> tasks = List.of(closedTask, openTask);
        when(taskRepository.existsById(closedTask.getId())).thenReturn(true);
        when(taskRepository.existsById(openTask.getId())).thenReturn(true);

        taskManagerService.updateTasks(tasks);

        verify(taskRepository).save(closedTask);
        verify(taskRepository).save(openTask);
    }

    @Test
    public void updateTaskIsNotFound() {
        Task closedTask = new Task("TSK-1234", "Test Title", "Test description", Status.CLOSED);
        List<Task> tasks = List.of(closedTask);
        when(taskRepository.existsById(closedTask.getId())).thenReturn(false);

        TaskNotFoundException ex = assertThrows(
                TaskNotFoundException.class,
                () -> taskManagerService.updateTasks(tasks)
        );

        assertTrue(ex.getMessage().contains("Task not found TSK-1234"));
    }

    @Test
    public void deleteTaskSuccess() {
        Task closedTask = new Task("TSK-1234", "Test Title", "Test description", Status.CLOSED);

        when(taskRepository.existsById(closedTask.getId())).thenReturn(true);
        when(taskRepository.findById(closedTask.getId())).thenReturn(closedTask);

        taskManagerService.deleteTask(closedTask.getId());

        verify(taskRepository).delete(closedTask);
    }

    @Test
    public void deleteTaskNotFound() {
        Task closedTask = new Task("TSK-1234", "Test Title", "Test description", Status.CLOSED);

        when(taskRepository.existsById(closedTask.getId())).thenReturn(false);

        TaskNotFoundException ex = assertThrows(
                TaskNotFoundException.class,
                () -> taskManagerService.deleteTask(closedTask.getId())
        );

        assertTrue(ex.getMessage().contains("Task not found TSK-1234"));
    }
}
