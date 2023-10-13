package br.com.marcusfeitosa.todo_java.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("tasks")
public class TaskController {

    @Autowired
    private ITaskRepository taskRepository;

    public ResponseEntity createTask(@RequestBody TaskDTO taskDTO){
        this.taskRepository.save(taskDTO);
        return ResponseEntity.ok().build();
    }
}
