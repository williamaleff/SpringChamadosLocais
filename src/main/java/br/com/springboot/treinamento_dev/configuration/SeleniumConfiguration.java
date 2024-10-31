
  package br.com.springboot.treinamento_dev.configuration;
  
  import javax.annotation.PostConstruct;
  import org.openqa.selenium.chrome.ChromeDriver; 
  import org.openqa.selenium.chrome.ChromeOptions; 
  import org.springframework.context.annotation.Bean; 
  import org.springframework.context.annotation.Configuration;
  
  @Configuration 
  public class SeleniumConfiguration {
  
  @PostConstruct 
  void postConstruct() {
  System.setProperty("webdriver.chrome.driver", "C:/eclipse/wokspace/bot/chromedriver-win64/chromedriver.exe"); 
  }
  
  @Bean 
  public ChromeDriver driver() { 
	  final ChromeOptions chromeOptions = new  ChromeOptions(); 
	  //chromeOptions.addEncodedExtensions("C:/eclipse/wokspace/treinamento-dev/src/main/resources/static/proxy/proxy.zip"); 
	  chromeOptions.addArguments("--headless"); 
	  return new  ChromeDriver(chromeOptions); 
	  }
  
  }
 