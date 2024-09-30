package exercise.controller;

import java.util.List;

import exercise.dto.TaskCreateDTO;
import exercise.dto.TaskDTO;
import exercise.dto.TaskUpdateDTO;
import exercise.mapper.TaskMapper;
import exercise.model.Task;
import exercise.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import exercise.exception.ResourceNotFoundException;
import exercise.repository.TaskRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/tasks")
public class TasksController {

    TaskRepository taskRepository;

    UserRepository userRepository;

    TaskMapper taskMapper;

    TasksController(TaskRepository taskRepository, UserRepository userRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.taskMapper = taskMapper;
    }

    // BEGIN
    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAllTasks() {
        return ResponseEntity.ok(taskRepository.findAll().stream().map(t -> taskMapper.map(t)).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable long id) {
        var task = taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task not found"));
        return ResponseEntity.ok(taskMapper.map(task));
    }

    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@RequestBody @Valid TaskCreateDTO taskCreateDTO) {
        var task = taskMapper.map(taskCreateDTO);
        task = taskRepository.save(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(taskMapper.map(task));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable long id, @RequestBody @Valid TaskUpdateDTO taskUpdateDTO) {
        var task = taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task not found"));
        taskMapper.update(taskUpdateDTO, task);
        //var assignee = taskUpdateDTO.getAssigneeId();
        //if(assignee!= null) task.setAssignee(userRepository.findById(assignee).orElseThrow(() -> new ResourceNotFoundException("Assignee not found")));
        task = taskRepository.save(task);
        return ResponseEntity.ok(taskMapper.map(task));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TaskDTO> deleteTask(@PathVariable long id) {
        var task = taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task not found"));
        taskRepository.delete(task);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    // END
}
