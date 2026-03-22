package br.com.infnet.todo_api;

import br.com.infnet.todo_api.entity.Todo;
import br.com.infnet.todo_api.repository.TodoRepository;
import br.com.infnet.todo_api.service.TodoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TodoServiceTest {

  @Mock
  private TodoRepository todoRepository;
  @InjectMocks
  private TodoService todoService;

  private Todo todo;
  @BeforeEach
  public void setUp() {
     todo = new Todo();
  }

  @Test
  @DisplayName("Deve adicionar uma nova tarefa com sucesso")
  public void testAddTodo() {
    todo.setTitle("Escultar TLOASG");
    when(todoRepository.save(any(Todo.class))).thenReturn(todo);

    Todo addTodo = todoService.criar(todo);

    assertNotNull(addTodo);
    assertEquals("Escultar TLOASG", addTodo.getTitle());
      verify(todoRepository, times(1)).save(any(Todo.class));
  }

  @Test
  @DisplayName("Deve deletar uma tarefa existente com sucesso")
  public void testDeleteTodo() {
    Long todoId = 1L;
    when(todoRepository.findById(todoId)).thenReturn(java.util.Optional.of(todo));
    todoService.deletar(todoId);
    verify(todoRepository, times(1)).delete(todo);
  }

  @Test
  @DisplayName("Deve lançar exeção ao tentar deletar uma tarefa inexistente")
  public void testDeleteTodoNaoEncontrado() {
    Long todoId = 1L;
    when(todoRepository.findById(todoId)).thenReturn(java.util.Optional.empty());

    assertThrows(RuntimeException.class, () -> {todoService.deletar(todoId);});
    verify(todoRepository, times(0)).deleteById(anyLong());
  }

  @Test
  @DisplayName("Deve atualizar uma tarefa existente com sucesso")
  public void testUpdateTodo() {
    Long todoId = 1L;
    todo.setId(todoId);
    todo.setTitle("Escultar TLOASG");

      Todo novoTodo = new Todo();
      novoTodo.setTitle("Escultar Slippery When Wet");

    when(todoRepository.findById(todoId)).thenReturn(java.util.Optional.of(todo));
    when(todoRepository.save(any(Todo.class))).thenReturn(novoTodo);

    Todo atualizado = todoService.atualizar(todoId, novoTodo);

    assertNotNull(atualizado);
    assertEquals("Escultar Slippery When Wet", atualizado.getTitle());
  }

  @Test
  @DisplayName("Deve lançar exeção ao tentar atualizar uma tarefa inexistente")
  public void testUpdateTodoNaoEncontrado() {
    Long todoId = 1L;
    Todo novoTodo = new Todo();
    novoTodo.setTitle("Escultar Slippery When Wet");

    when(todoRepository.findById(todoId)).thenReturn(java.util.Optional.empty());
    assertThrows(RuntimeException.class, () -> {todoService.atualizar(todoId, novoTodo);});

      verify(todoRepository, never()).save(any());
  }

  @Test
  @DisplayName("Deve listar todas as tarefas com sucesso")
  public void testListarTodos() {
    List<Todo> todos = Arrays.asList(
      new Todo(1L, "Fazer Café", false),
      new Todo(2L, "Academia", true),
      new Todo(3L, "Ler livro", false)
    );
    when(todoRepository.findAll()).thenReturn(todos);

    List<Todo> resultado = todoService.listarTodos();

    assertNotNull(resultado);
    assertEquals(3, resultado.size());
    verify(todoRepository, times(1)).findAll();
  }
  

  @Test
  @DisplayName("Deve encontrar uma tarefa por ID com sucesso")
  public void testBuscarPorId() {
    Long todoId = 1L;
    todo.setId(todoId);
    todo.setTitle("Fazer Café");
    when(todoRepository.findById(todoId)).thenReturn(java.util.Optional.of(todo));
    Todo resultado = todoService.buscarPorId(todoId);

    assertNotNull(resultado);
    assertEquals("Fazer Café", resultado.getTitle());
    verify(todoRepository, times(1)).findById(todoId);
  }


  @Test
  @DisplayName("Deve lançar exeção ao tentar encontrar uma tarefa por ID inexistente")
  public void testBuscarPorIdNaoEncontrado() {
    when(todoRepository.findById(1L)).thenReturn(java.util.Optional.empty());
    assertThrows(RuntimeException.class, () -> {todoService.buscarPorId(1L);});

    verify(todoRepository, times(1)).findById(1L);
  }

}