package br.com.infnet.todo_api.Selenium.core;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class BaseSeleniumPage {

  protected WebDriver driver;
  protected WebDriverWait wait;

  public BaseSeleniumPage(WebDriver driver) {
    this.driver = driver;
    this.wait = new WebDriverWait(driver, Duration.ofSeconds(25));
  }

  protected WebElement esperarElementoVisivel(By locator) {
    return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
  }

  protected WebElement esperarElementoClicavel(By locator) {
    return wait.until(ExpectedConditions.elementToBeClickable(locator));
  }

  protected void clicar(By locator) {
    WebElement elemento = esperarElementoClicavel(locator);
    elemento.click();
  }

  protected void preencherCampo(By locator, String valor) {
    WebElement campo = esperarElementoVisivel(locator);
    campo.clear();
    campo.sendKeys(valor);
  }
}