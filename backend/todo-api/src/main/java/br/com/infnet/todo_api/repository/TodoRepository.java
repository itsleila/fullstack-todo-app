package br.com.infnet.todo_api.repository;

import br.com.infnet.todo_api.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}
