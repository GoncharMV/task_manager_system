package com.grettastic.tms.services;

import com.grettastic.tms.enums.TaskStatus;
import com.grettastic.tms.model.Task;
import com.grettastic.tms.repo.TaskRepository;
import com.grettastic.tms.requests.TaskRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class TaskService {
    private final TaskRepository taskRepo;

    public List<Task> getAllTasks() {
        return taskRepo.findAll();
    }

    public Task createNewTask(TaskRequest task) {
        Task savedTask = new Task();
        savedTask.setDescription(task.getDescription());
        savedTask.setPriority(task.getPriority());
        savedTask.setStatus(task.getExecutor_id() != null ? TaskStatus.IN_PROGRESS : TaskStatus.WAITING);

        return taskRepo.save(savedTask);
    }
}
