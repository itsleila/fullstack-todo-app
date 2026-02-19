package br.com.infnet.todo_api.service;

import br.com.infnet.todo_api.entity.Todo;
import br.com.infnet.todo_api.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<Todo> listarTodos() {
        return todoRepository.findAll();
    }

    public Todo findById(Long id) {
        return todoRepository.findById(id).orElseThrow(() -> new RuntimeException("Tarefa não encontrado"));
    }

    public Todo addTodo(Todo todo) {
        return todoRepository.save(todo);
    }

    public Todo updateTodo(Long id, Todo novoTodo) {
        Todo existente = findById(id);

        existente.setTitle(novoTodo.getTitle());
        existente.setCompleted(novoTodo.getCompleted());

        return todoRepository.save(existente);
    }

    public void deleteTodo(Long id) {
        findById(id);
        todoRepository.deleteById(id);
    }
}
