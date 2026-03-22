package br.com.infnet.todo_api.service;

import br.com.infnet.todo_api.entity.Todo;
import br.com.infnet.todo_api.exception.TodoNotFoundException;
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

    public Todo buscarPorId(Long id) {
        return todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException(id));
    }

    public Todo criar(Todo todo) {
        return todoRepository.save(todo);
    }

    public Todo atualizar(Long id, Todo novoTodo) {
        Todo existente = buscarPorId(id);

        atualizarDados(existente, novoTodo);
        return todoRepository.save(existente);
    }

    public void deletar(Long id) {
        Todo existente = buscarPorId(id);
        todoRepository.delete(existente);
    }

    private void atualizarDados(Todo existente, Todo novoTodo) {
        existente.setTitle(novoTodo.getTitle());
        existente.setCompleted(novoTodo.getCompleted());
    }
}
