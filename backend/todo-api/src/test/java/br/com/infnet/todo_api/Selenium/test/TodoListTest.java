package br.com.infnet.todo_api.Selenium.test;

import br.com.infnet.todo_api.Selenium.core.BaseSeleniumTest;
import br.com.infnet.todo_api.Selenium.pages.TodoListPage;
import org.junit.jupiter.api.*;

@Tag("e2e")
public class TodoListTest extends BaseSeleniumTest {
  private TodoListPage todoListPage;

  @BeforeEach
  void configurarPagina() {
    todoListPage = new TodoListPage(driver);
  }

  @Test
  @DisplayName("Deve exibir o título correto da página")
  void paginaCorretaTest() {
    String titulo = todoListPage.obterTituloPagina();
    Assertions.assertEquals("TO-DO List:", titulo);
  }

  @Test
  @DisplayName("Deve adicionar uma tarefa com sucesso")
  void tarefaAdcionadaSucessoTest() {
    String tarefa = "Fazer vitamina de abacate";
    todoListPage.adicionarTarefa(tarefa);
    String mensagemSucesso = todoListPage.obterMensagemSucesso();
    Assertions.assertEquals("Sua nova tarefa foi adicionada!", mensagemSucesso);
  }

  @Test
  @DisplayName("Deve exibir erro com entrada de tarefa vazia")
  void entradaInvalidaTest() {
    todoListPage.adicionarTarefa("");
    String mensagemErro = todoListPage.obterMensagemErro();
    Assertions.assertEquals("A tarefa não pode estar vazia", mensagemErro);
  }

  @Test
  @DisplayName("Deve alterar o status de uma tarefa")
  void alterarStatusTest() {
    String tarefa = "Ler A Metamorfose";
    todoListPage.adicionarTarefa(tarefa);
    todoListPage.alterarStatusTarefa();
    String status = todoListPage.obterStatusTarefa();
    Assertions.assertEquals("Feito", status);
  }

  @Test
  @DisplayName("Deve conseguir deletar uma tarefa")
  void deletarTarefaTest() {
    String tarefa = "Ir no supermercado";
    todoListPage.adicionarTarefa(tarefa);
    todoListPage.deletarTarefa();
    String mensagemSucesso = todoListPage.obterMensagemSucesso();
    Assertions.assertEquals("Tarefa removida com sucesso!", mensagemSucesso);
  }

  @Test
  @DisplayName("Deve editar a tarefa com sucesso.")
  void editarTarefaTest() {
    String tarefa = "Ir para academia";
    String tarefaAtualizada = "Ir para praia";
    todoListPage.adicionarTarefa(tarefa);
    todoListPage.editarTarefa(tarefaAtualizada);
    String mensagemSucesso = todoListPage.obterMensagemSucesso();
    Assertions.assertEquals("Tarefa editada com sucesso!", mensagemSucesso);
  }

}