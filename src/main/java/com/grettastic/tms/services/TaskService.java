package com.grettastic.tms.services;

import com.grettastic.tms.enums.TaskPriority;
import com.grettastic.tms.enums.TaskStatus;
import com.grettastic.tms.model.Task;
import com.grettastic.tms.model.User;
import com.grettastic.tms.repo.TaskRepository;
import com.grettastic.tms.requests.TaskRequest;
import com.grettastic.tms.responses.TaskResponse;
import com.grettastic.tms.utils.TaskMapper;
import com.grettastic.tms.utils.UserUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class TaskService {
    private final TaskRepository taskRepo;
    private final UserUtils userUtils;

    public List<TaskResponse> getAllTasks() {
        return TaskMapper.toTaskResponseList(taskRepo.findAll());
    }

    public TaskResponse getTaskById(Long id) {
        return TaskMapper.toTaskResponse(getTask(id));
    }
    public TaskResponse createNewTask(TaskRequest task, User author) {
        Task savedTask = new Task();
        savedTask.setTitle(task.getTitle());
        savedTask.setDescription(task.getDescription());
        savedTask.setPriority(task.getPriority() != null ? task.getPriority() : TaskPriority.LOW);
        savedTask.setStatus(task.getExecutorId() != null ? TaskStatus.IN_PROGRESS : TaskStatus.WAITING);
        savedTask.setExecutor(task.getExecutorId() != null ? userUtils.findUserById(task.getExecutorId()) : null);
        savedTask.setAuthor(author);
        return TaskMapper.toTaskResponse(taskRepo.save(savedTask));
    }

    public TaskResponse updateTask(Long id, TaskRequest newTask, Principal principal) throws AccessDeniedException {
        Task task = getTask(id);

        boolean isAdmin = userUtils.isAdmin(principal.getName());
        boolean isExecutor = isTaskExecutor(id, principal.getName());

        if (isAdmin) {
            if (newTask.getTitle() != null) task.setTitle(newTask.getTitle());
            if (newTask.getDescription() != null) task.setDescription(newTask.getDescription());
            if (newTask.getPriority() != null) task.setPriority(newTask.getPriority());
            if (newTask.getStatus() != null) task.setStatus(newTask.getStatus());
            if (newTask.getExecutorId() != null) task.setExecutor(userUtils.findUserById(newTask.getExecutorId()));
        } else if (isExecutor) {
            if (newTask.getStatus() != null) task.setStatus(newTask.getStatus());
        } else {
            throw new AccessDeniedException("You do not have access to update the task");
        }

        return TaskMapper.toTaskResponse(taskRepo.save(task));
    }

    public void deleteTask(Long id) {
        taskRepo.delete(getTask(id));
    }

    public boolean isTaskExecutor(Long taskId, String username) {
        return taskRepo.findById(taskId)
                .map(task -> task.getExecutor() != null && task.getExecutor().getEmail().equals(username))
                .orElse(false);
    }

    public Task getTask(Long id) {
        return taskRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Task is not found"));
    }

    public List<TaskResponse> getCreatedTasks(Long userId) {
        return TaskMapper.toTaskResponseList(taskRepo.findAllByAuthor(userUtils.findUserById(userId)));
    }

    public List<TaskResponse> getAssignedTasks(Long userId) {
        return TaskMapper.toTaskResponseList(taskRepo.findAllByExecutor(userUtils.findUserById(userId)));
    }
}
