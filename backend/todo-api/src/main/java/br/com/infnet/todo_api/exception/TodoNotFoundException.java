package br.com.infnet.todo_api.exception;

public class TodoNotFoundException extends RuntimeException {
    public TodoNotFoundException(Long id) {
        super("Tarefa não encontrada com id: " + id);
    }
}