package com.grettastic.tms.utils;

import com.grettastic.tms.model.Comment;
import com.grettastic.tms.responses.CommentResponse;

import java.util.ArrayList;
import java.util.List;

public class CommentsMapper {
    public static CommentResponse toCommentResponse(Comment comment) {
        if (comment == null) {
            return null;
        }

        return CommentResponse.builder()
                .id(comment.getId())
                .authorId(comment.getAuthor().getId())
                .text(comment.getText())
                .taskId(comment.getTask().getId())
                .createdAt(comment.getCreatedAt())
                .build();
    }

    public static List<CommentResponse> toCommentResponseList(List<Comment> comments) {
        if (comments == null) {
            return null;
        }
        List<CommentResponse> commentResponseList = new ArrayList<>();
        for (Comment c : comments) {
            commentResponseList.add(toCommentResponse(c));
        }
        return commentResponseList;
    }

    public static List<Long> toCommentsIds(List<Comment> comments) {
        List<Long> commentIds = new ArrayList<>();
        for (Comment c : comments) {
            commentIds.add(c.getId());
        }
        return commentIds;
    }
}
