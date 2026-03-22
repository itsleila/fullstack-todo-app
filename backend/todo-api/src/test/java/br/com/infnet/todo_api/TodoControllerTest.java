

package br.com.infnet.todo_api;

import br.com.infnet.todo_api.controller.TodoController;
import br.com.infnet.todo_api.entity.Todo;
import br.com.infnet.todo_api.service.TodoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TodoController.class)
class TodoControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private TodoService todoService;

  private Todo todo;

  @BeforeEach
  public void setUp() {todo = new Todo();}

  @Test
  @DisplayName("Deve listar todas as tarefas com sucesso")
  public void testListarTodos() throws Exception {
    List<Todo> todos = Arrays.asList(new Todo(), new Todo());
    when(todoService.listarTodos()).thenReturn(todos);

    mockMvc.perform(get("/api/todos"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.length()").value(2));
  }

  @Test
  @DisplayName("Deve criar uma nova tarefa com sucesso")
  public void testCriarTodo() throws Exception {
    todo.setId(1L);
    todo.setTitle("Ler A Metamorfose");

    when(todoService.criar(any(Todo.class))).thenReturn(todo);
    mockMvc.perform(post("/api/todos")
      .contentType(MediaType.APPLICATION_JSON)
      .content("""
        {
          "title": "Ler A Metamorfose",
          "completed": false
        }
      """))
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.title").value("Ler A Metamorfose"));
  }

  @Test
  @DisplayName("Deve atualizar uma tarefa existente com sucesso")
  public void testAtualizarTodo() throws Exception {
    todo.setId(1L);
    todo.setTitle("Ler A Metamorfose- Atualizado");
    todo.setCompleted(true);

    when(todoService.atualizar(anyLong(), any(Todo.class))).thenReturn(todo);
    mockMvc.perform(put("/api/todos/1")
      .contentType(MediaType.APPLICATION_JSON)
      .content("""
        {
          "title": "Ler A Metamorfose- Atualizado",
          "completed": true
        }
      """))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.completed").value(true));
  }

  @Test
  @DisplayName("Deve deletar uma tarefa existente com sucesso")
  public void testDeletarTodo() throws Exception {
      Long todoId = 1L;
      doNothing().when(todoService).deletar(todoId);
      mockMvc.perform(delete("/api/todos/{id}", todoId))
        .andExpect(status().isNoContent());
      verify(todoService, times(1)).deletar(todoId);
  }

  @Test
  @DisplayName("Deve retornar timeout ao acessar endpoint de teste")
  public void testTesteTimeout() throws Exception {
    mockMvc.perform(get("/api/todos/teste-timeout"))
    .andExpect(status().isRequestTimeout());
  }

  @Test
  @DisplayName("Deve retornar erro ao acessar endpoint de teste")
  public void testTesteError() throws Exception {
    mockMvc.perform(get("/api/todos/teste-error"))
      .andExpect(status().isInternalServerError());
  }
}