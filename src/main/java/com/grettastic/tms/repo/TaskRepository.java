package com.grettastic.tms.repo;

import com.grettastic.tms.model.Task;
import com.grettastic.tms.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAllByAuthor(User author);

    List<Task> findAllByExecutor(User executor);
}
