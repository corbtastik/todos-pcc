package io.todos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@RestController
public class Api {
    private final TodosRepo todosRepo;

    @Autowired
    public Api(TodosRepo todosRepo) {
        this.todosRepo = todosRepo;
    }

    @GetMapping("/")
    public List<Todo> retrieve() {
        List<Todo> todos = new ArrayList<>();
        todosRepo.findAll().forEach(todos::add);
        return todos;
    }

    @PostMapping("/")
    public Todo create(@RequestBody Todo todo) {
        todo.setId(System.currentTimeMillis());
        return todosRepo.save(todo);
    }

    @DeleteMapping("/")
    public void delete() {
        todosRepo.deleteAll();
    }

    @GetMapping("/{id}")
    public Todo retrieve(@PathVariable Long id) {
        Todo todos = this.todosRepo.findById(id).orElse(null);
        if(todos != null) {
            return todos;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, format("todo.id=%d", id));
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        todosRepo.deleteById(id);
    }

    @PatchMapping("/{id}")
    public Todo update(@PathVariable Long id, @RequestBody Todo todo) {
        if(todo == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "todo can't be null");
        }
        Optional<Todo> result = this.todosRepo.findById(id);
        if(!result.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, format("todo.id=%d", id));
        }
        Todo current = result.get();
        if(!ObjectUtils.isEmpty(todo.getCompleted())) {
            current.setCompleted(todo.getCompleted());
        }
        if(!StringUtils.isEmpty(todo.getTitle())){
            current.setTitle(todo.getTitle());
        }
        return todosRepo.save(current);
    }
}
