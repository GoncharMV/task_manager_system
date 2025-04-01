package com.grettastic.tms.services;

import com.grettastic.tms.model.Comment;
import com.grettastic.tms.model.Task;
import com.grettastic.tms.model.User;
import com.grettastic.tms.repo.CommentRepository;
import com.grettastic.tms.requests.CommentRequest;
import com.grettastic.tms.responses.CommentResponse;
import com.grettastic.tms.utils.CommentsMapper;
import com.grettastic.tms.utils.TaskUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CommentService {
    private final CommentRepository commentRepo;
    private final TaskUtils taskUtils;

    public CommentResponse postComment(Long id, CommentRequest comment, User author) {
        Comment newComment = new Comment();

        newComment.setText(comment.getText());
        newComment.setAuthor(author);
        newComment.setTask(taskUtils.findTaskById(id));

        return CommentsMapper.toCommentResponse(commentRepo.save(newComment));
    }

    public List<CommentResponse> getCommentsByTaskId(Long id) {
        Task task = taskUtils.findTaskById(id);
        return CommentsMapper.toCommentResponseList(commentRepo.findAllByTask(task));
    }
}
