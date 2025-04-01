package com.grettastic.tms.repo;

import com.grettastic.tms.model.Comment;
import com.grettastic.tms.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByTask(Task task);
}
