package br.com.infnet.todo_api.controller;

import br.com.infnet.todo_api.entity.Todo;
import br.com.infnet.todo_api.service.TodoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
@CrossOrigin(origins = "*")
public class TodoController {

    private final TodoService service;

    public TodoController(TodoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Todo>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @PostMapping
    public ResponseEntity<Todo> criar(@Valid @RequestBody Todo todo) {
        Todo criado = service.criar(todo);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Todo> atualizar(@PathVariable Long id, @RequestBody Todo todo) {
        Todo atualizado = service.atualizar(id, todo);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/teste-timeout")
    public ResponseEntity<Void> testeTimeout() {
        throw new ResponseStatusException(HttpStatus.REQUEST_TIMEOUT,"Tempo limite excedido");
    }

    @GetMapping("/teste-error")
    public ResponseEntity<Void> testeError() {
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro simulado para teste");
    }
}
