package com.grettastic.tms.services;

import com.grettastic.tms.enums.TaskPriority;
import com.grettastic.tms.enums.TaskStatus;
import com.grettastic.tms.model.Task;
import com.grettastic.tms.model.User;
import com.grettastic.tms.repo.TaskRepository;
import com.grettastic.tms.requests.TaskRequest;
import com.grettastic.tms.responses.TaskResponse;
import com.grettastic.tms.utils.UserUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepo;

    @Mock
    private UserUtils userUtils;

    @InjectMocks
    private TaskService taskService;

    private User author;
    private User executor;
    private TaskRequest taskRequest;
    private Task savedTask;

    @BeforeEach
    void setup() {
        author = new User();
        author.setId(1L);
        author.setEmail("author@example.com");

        executor = new User();
        executor.setId(2L);
        executor.setEmail("executor@example.com");

        taskRequest = new TaskRequest();
        taskRequest.setTitle("Test Task");
        taskRequest.setDescription("Task Description");
        taskRequest.setPriority(TaskPriority.HIGH);
        taskRequest.setExecutorId(2L);

        savedTask = new Task();
        savedTask.setId(100L);
        savedTask.setTitle("Test Task");
        savedTask.setDescription("Task Description");
        savedTask.setPriority(TaskPriority.HIGH);
        savedTask.setStatus(TaskStatus.IN_PROGRESS);
        savedTask.setExecutor(executor);
        savedTask.setAuthor(author);
    }

    @Test
    void shouldReturnAllTasks() {
        Task task1 = new Task();
        task1.setId(1L);
        task1.setTitle("Task 1");
        task1.setDescription("Description 1");
        task1.setPriority(TaskPriority.HIGH);
        task1.setStatus(TaskStatus.IN_PROGRESS);

        Task task2 = new Task();
        task2.setId(2L);
        task2.setTitle("Task 2");
        task2.setDescription("Description 2");
        task2.setPriority(TaskPriority.LOW);
        task2.setStatus(TaskStatus.WAITING);

        List<Task> taskList = Arrays.asList(task1, task2);

        when(taskRepo.findAll()).thenReturn(taskList);

        List<TaskResponse> responseList = taskService.getAllTasks();

        assertNotNull(responseList);
        assertEquals(2, responseList.size());

        assertEquals("Task 1", responseList.get(0).getTitle());
        assertEquals(TaskPriority.HIGH, responseList.get(0).getPriority());

        assertEquals("Task 2", responseList.get(1).getTitle());
        assertEquals(TaskStatus.WAITING, responseList.get(1).getStatus());

        verify(taskRepo, times(1)).findAll();
    }

    @Test
    void shouldCreateNewTask_WhenExecutorIsProvided() {
        when(userUtils.findUserById(taskRequest.getExecutorId())).thenReturn(executor);
        when(taskRepo.save(any(Task.class))).thenReturn(savedTask);

        TaskResponse response = taskService.createNewTask(taskRequest, author);

        assertNotNull(response);
        assertEquals("Test Task", response.getTitle());
        assertEquals(TaskStatus.IN_PROGRESS, response.getStatus());
        assertEquals(TaskPriority.HIGH, response.getPriority());
        assertEquals(executor.getId(), response.getExecutorId());
        assertEquals(author.getId(), response.getAuthorId());

        verify(userUtils).findUserById(2L);
        verify(taskRepo).save(any(Task.class));
    }

    @Test
    void shouldCreateNewTask_WhenNoExecutorIsProvided() {
        taskRequest.setExecutorId(null);
        savedTask.setExecutor(null);
        savedTask.setStatus(TaskStatus.WAITING);

        when(taskRepo.save(any(Task.class))).thenReturn(savedTask);

        TaskResponse response = taskService.createNewTask(taskRequest, author);

        assertNotNull(response);
        assertEquals("Test Task", response.getTitle());
        assertEquals(TaskStatus.WAITING, response.getStatus());
        assertNull(response.getExecutorId());

        verify(taskRepo).save(any(Task.class));
        verifyNoInteractions(userUtils);
    }
}
