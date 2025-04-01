package com.grettastic.tms.utils;

import com.grettastic.tms.model.Task;
import com.grettastic.tms.responses.TaskResponse;

import java.util.ArrayList;
import java.util.List;

public class TaskMapper {

    public static TaskResponse toTaskResponse(Task task) {
        if (task == null) {
            return null;
        }
        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .priority(task.getPriority())
                .status(task.getStatus())
                .authorId(UserMapper.toUserId(task.getAuthor()))
                .executorId(UserMapper.toUserId(task.getExecutor()))
                .comments(CommentsMapper.toCommentResponseList(task.getComments()))
                .build();
    }

    public static List<Long> toTasksIds(List<Task> tasks) {
        List<Long> ids = new ArrayList<>();
        for (Task t : tasks) {
            ids.add(t.getId());
        }
        return ids;
    }

    public static List<TaskResponse> toTaskResponseList(List<Task> tasks) {
        List<TaskResponse> taskResponseList = new ArrayList<>();
        for (Task t : tasks) {
            taskResponseList.add(toTaskResponse(t));
        }
        return taskResponseList;
    }
}
