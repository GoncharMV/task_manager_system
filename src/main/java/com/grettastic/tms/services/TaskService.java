package com.grettastic.tms.services;

import com.grettastic.tms.enums.TaskStatus;
import com.grettastic.tms.model.Task;
import com.grettastic.tms.model.User;
import com.grettastic.tms.repo.TaskRepository;
import com.grettastic.tms.requests.TaskRequest;
import com.grettastic.tms.responses.TaskResponse;
import com.grettastic.tms.utils.TaskMapper;
import com.grettastic.tms.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class TaskService {
    private final TaskRepository taskRepo;
    private final UserUtils userUtils;

    public List<TaskResponse> getAllTasks() {
        return TaskMapper.toTaskResponseList(taskRepo.findAll());
    }

    public TaskResponse createNewTask(TaskRequest task, User author) {
        Task savedTask = new Task();
        savedTask.setDescription(task.getDescription());
        savedTask.setPriority(task.getPriority());
        savedTask.setStatus(task.getExecutorId() != null ? TaskStatus.IN_PROGRESS : TaskStatus.WAITING);
        savedTask.setExecutor(task.getExecutorId() != null ? userUtils.findUserById(task.getExecutorId()) : null);
        savedTask.setAuthor(author);
        return TaskMapper.toTaskResponse(taskRepo.save(savedTask));
    }
}
