package br.com.infnet.todo_api.Selenium.core;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;


public class BaseSeleniumTest {
  protected WebDriver driver;

  @BeforeAll
  static void inicializarDriver(){
    WebDriverManager.chromedriver().setup();
  }

  @BeforeEach
  void abrirNavegador(){
    driver = new ChromeDriver();
    driver.get("http://localhost:5173/");
    driver.manage().window().maximize();
  }

  @AfterEach
  void fecharNavegador(){
    if (driver != null) {
      driver.quit();
    }
  }
}