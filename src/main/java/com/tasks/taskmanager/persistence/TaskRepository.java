package com.tasks.taskmanager.persistence;

import com.tasks.taskmanager.rest.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    boolean existsById(String id);
   Task findById(String id);
   List<Task> findByStatus(Status status);
}
