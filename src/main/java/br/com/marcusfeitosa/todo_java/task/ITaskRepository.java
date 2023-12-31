package br.com.marcusfeitosa.todo_java.task;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ITaskRepository extends JpaRepository <TaskDTO, UUID> {

    List<TaskDTO> findByUserId(UUID userId);
}
