package com.tasks.taskmanager.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tasks.taskmanager.persistence.Task;
import com.tasks.taskmanager.service.TaskManagerService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = TaskManagerController.class)
public class TaskManagerControllerIntTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TaskManagerService taskManagerService;

    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @AfterEach
    public void reset() {
        Mockito.reset(taskManagerService);
    }

    @Test
    public void getAllTasksShouldReturn200() throws Exception {
        String uri = "/tasks";

        Task task = new Task("TSK-343434", "Test task", "Some description",Status.OPEN);
        List<Task> tasklist = new ArrayList<Task>();
        tasklist.add(task);

        when(taskManagerService.getAllTasks(null)).thenReturn(tasklist);
        mockMvc.perform(MockMvcRequestBuilders.get(uri)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].title").value(tasklist.get(0).getTitle()))
                .andExpect(jsonPath("$[0].description").value(tasklist.get(0).getDescription()))
                .andExpect(jsonPath("$[0].status").value(tasklist.get(0).getStatus().toString()));
    }

    @Test
    public void getAllTasksFilterUnknownStatusShouldReturnEmptyResponse() throws Exception {
        String uri = "/tasks";


        List<Task> tasklist = new ArrayList<Task>();
        when(taskManagerService.getAllTasks("something")).thenReturn(tasklist);
        mockMvc.perform(MockMvcRequestBuilders.get(uri+"?status=something")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(content().string("[]"));
    }

    @Test
    public void getAllTasksFilteredByStatusShouldReturn200() throws Exception {
        String uri = "/tasks";


        Task task = new Task("TSK-343434", "Test task", "Some description",Status.OPEN);
        List<Task> tasklist = new ArrayList<Task>();
        tasklist.add(task);
        when(taskManagerService.getAllTasks("open")).thenReturn(tasklist);
        mockMvc.perform(MockMvcRequestBuilders.get(uri+"?status=open")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$[0].title").value(tasklist.get(0).getTitle()))
                .andExpect(jsonPath("$[0].description").value(tasklist.get(0).getDescription()))
                .andExpect(jsonPath("$[0].status").value(tasklist.get(0).getStatus().toString()));
    }

    @Test
    public void addTaskShouldReturn201() throws Exception {
        String uri = "/tasks";

        Task task = new Task("TSK-343434", "Test task", "Some description",Status.OPEN);
        String jsonRequestString = "{\"title\" : \""+task.getTitle()+"\" , \"description\" : \""+task.getDescription()+"\", \"status\" : \""+task.getStatus()+"\"}";

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(jsonRequestString))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated());
    }

    @Test
    public void addTaskWithWrongStatusShouldReturn404() throws Exception {
        String uri = "/tasks";

        Task task = new Task("TSK-343434", "Test task", "Some description",Status.OPEN);
        String jsonRequestString = "{\"title\" : \""+task.getTitle()+"\" , \"description\" : \""+task.getDescription()+"\", \"status\" : \"wrong status\"}";

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(jsonRequestString))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest());
    }


    @Test
    public void updateTaskShouldReturn200() throws Exception {
        String uri = "/tasks";

        Task task = new Task("TSK-343434", "Test task", "Some description",Status.OPEN);

        String jsonRequestString = "[{\"title\" : \""+task.getTitle()+"\" , \"description\" : \""+task.getDescription()+"\", \"status\" : \""+task.getStatus()+"\"}]";

        mockMvc.perform(MockMvcRequestBuilders.put(uri)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(jsonRequestString))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }


    @Test
    public void deleteTaskShouldReturn200() throws Exception {
        String uri = "/tasks";

        Task task = new Task("TSK-343434", "Test task", "Some description",Status.OPEN);

        mockMvc.perform(MockMvcRequestBuilders.delete(uri+"/"+task.getId())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }


}

