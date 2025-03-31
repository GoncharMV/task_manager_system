package com.grettastic.tms.utils;

import com.grettastic.tms.model.User;
import com.grettastic.tms.responses.UserResponse;

import java.util.ArrayList;
import java.util.List;

public class UserMapper {

    public static UserResponse toUserResponse(User user) {
        if (user == null) {
            return null;
        }
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .assignedTasksIds(TaskMapper.toTasksIds(user.getAssignedTasks()))
                .createdTasksIds(TaskMapper.toTasksIds(user.getCreatedTasks()))
                .commentsIds(CommentsMapper.toCommentsIds(user.getComments()))
                .build();
    }

    public static List<UserResponse> toUserResponseList(List<User> users) {
        List<UserResponse> userResponseList = new ArrayList<>();
        for (User u : users) {
            userResponseList.add(toUserResponse(u));
        }

        return userResponseList;
    }
}
