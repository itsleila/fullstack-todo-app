package br.com.infnet.todo_api.Selenium.pages;

import br.com.infnet.todo_api.Selenium.core.BaseSeleniumPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TodoListPage extends BaseSeleniumPage {
  private final By tituloPagina = By.tagName("h2");
  private By inputNovaTarefa = By.id("newTodo-input");
  private By botaoAdicionar = By.id("newTodo-button");
  private By alertaSucesso = By.className("success-alert");
  private By alertaErro = By.className("error-alert");
  private By obterTarefa = By.xpath("(//div[contains(@class,'tarefa-item')])[last()]");
  private By buttonStatus = By.xpath("(//button[contains(@id,'status-button')])[last()]");
  private By statusText = By.xpath("(//span[contains(@id,'status-text')])[last()]");
  private By buttonDelete = By.xpath("//button[contains(@id, 'delete-button-')]");
  private By buttonEdit = By.xpath("(//button[contains(@id, 'edit-button-')])[last()]");
  private By inputEdit = By.id("edit-input");
  private By buttonSave = By.id("save-button");

  public TodoListPage(WebDriver driver) {
    super(driver);
  }

  public String obterTituloPagina() {
    esperarElementoVisivel(tituloPagina);
    return driver.findElement(tituloPagina).getText();
  }

  public void adicionarTarefa(String tarefa) {
    preencherCampo(inputNovaTarefa, tarefa);
    clicar(botaoAdicionar);
  }

  public String obterMensagemSucesso() {
    esperarElementoVisivel(alertaSucesso);
    return driver.findElement(alertaSucesso).getText();
  }

  public String obterMensagemErro() {
    esperarElementoVisivel(alertaErro);
    return driver.findElement(alertaErro).getText();
  }

  public String obterTarefaAdicionada() {
    esperarElementoVisivel(obterTarefa);
    return driver.findElement(obterTarefa).getText();
  }


  public void alterarStatusTarefa() {
    esperarElementoClicavel(buttonStatus);
    clicar(buttonStatus);
  }


  public String obterStatusTarefa() {
    esperarElementoVisivel(statusText);
    return driver.findElement(statusText).getText();
  }


  public void deletarTarefa() {
    esperarElementoClicavel(buttonDelete);
    clicar(buttonDelete);
  }

  public void editarTarefa(String novaTarefa) {
    esperarElementoClicavel(buttonEdit);
    clicar(buttonEdit);

    esperarElementoVisivel(inputEdit);
    preencherCampo(inputEdit, novaTarefa);

    clicar(buttonSave);
    obterMensagemSucesso();
  }



}