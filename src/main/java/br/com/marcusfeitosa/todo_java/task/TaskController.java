package br.com.marcusfeitosa.todo_java.task;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private ITaskRepository taskRepository;

    @PostMapping
    public ResponseEntity createTask(@RequestBody TaskDTO taskDTO, HttpServletRequest request){
        var userId = request.getAttribute("idUser");
        taskDTO.setUserId((UUID) userId);
        var currentDate = LocalDateTime.now();

        if(currentDate.isAfter(taskDTO.getEndAt())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A data de encerramento da tarefa não pode ser menor do que a data atual");
        }
        if(taskDTO.getStartAt().isAfter(taskDTO.getEndAt())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A data de início da task não pode ser posterior a de encerramento");
        }

        this.taskRepository.save(taskDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public List<TaskDTO> getAllUserTasks(HttpServletRequest request){
        var userId = request.getAttribute("idUser");
        return this.taskRepository.findByUserId((UUID)userId);
    }

    @PutMapping("/{id}")
    public TaskDTO updateTask(@RequestBody TaskDTO taskDTO, HttpServletRequest request, @PathVariable UUID id){
        var idUser = request.getAttribute("idUser");
        taskDTO.setUserId((UUID)idUser);
        taskDTO.setId(id);
        return this.taskRepository.save(taskDTO);
    }
}

