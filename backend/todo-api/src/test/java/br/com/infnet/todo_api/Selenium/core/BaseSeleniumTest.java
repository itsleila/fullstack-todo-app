package br.com.infnet.todo_api.Selenium.core;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseSeleniumTest {

  protected WebDriver driver;

  @BeforeAll
  static void inicializarDriver(){
    WebDriverManager.chromedriver().setup();
  }

  @BeforeEach
  void abrirNavegador(){

    ChromeOptions options = new ChromeOptions();
    options.addArguments("--headless=new");
    options.addArguments("--no-sandbox");
    options.addArguments("--disable-dev-shm-usage");
    options.addArguments("--disable-gpu");
    options.addArguments("--window-size=1920,1080");

    driver = new ChromeDriver(options);

    //driver.get("http://localhost:5173/");
    driver.get("http://localhost:3000/");
  }

  @AfterEach
  void fecharNavegador(){
    if (driver != null) {
      driver.quit();
    }
  }
}