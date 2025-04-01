package com.grettastic.tms.utils;

import com.grettastic.tms.model.Task;
import com.grettastic.tms.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class TaskUtils {
    private final TaskService taskService;

    public Task findTaskById(Long id) {
        return taskService.getTask(id);
    }

}
