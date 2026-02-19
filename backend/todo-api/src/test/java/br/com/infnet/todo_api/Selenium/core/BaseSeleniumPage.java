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
    this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
  }

  public void esperarElementoVisivel(By locator) {
    wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
  }

  public void esperarElementoClicavel(By locator) {
    wait.until(ExpectedConditions.elementToBeClickable(locator));
  }

  protected void clicar(By locator) {
    esperarElementoClicavel(locator);
    driver.findElement(locator).click();
  }

  protected void preencherCampo(By locator, String valor) {
    esperarElementoVisivel(locator);
    WebElement campo = driver.findElement(locator);
    campo.clear();
    campo.sendKeys(valor);
  }

}