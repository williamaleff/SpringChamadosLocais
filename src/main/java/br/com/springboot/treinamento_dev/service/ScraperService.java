
  package br.com.springboot.treinamento_dev.service;
  
  import lombok.AllArgsConstructor;
  
  //import java.util.List;
  
  import javax.annotation.PostConstruct;
  
  import org.openqa.selenium.By; 
  import org.openqa.selenium.Keys; 
  import org.openqa.selenium.WebElement; 
  import org.openqa.selenium.chrome.ChromeDriver; 
  import org.openqa.selenium.chrome.ChromeOptions; 
  import org.springframework.stereotype.Service; 
  //import java.time.Duration; 
  import org.openqa.selenium.support.ui.WebDriverWait; 
  import org.openqa.selenium.support.ui.ExpectedConditions; 
  import java.util.concurrent.TimeUnit;
  
  @Service
  @AllArgsConstructor 
  public class ScraperService {
  
  // Configuração do proxy // 
  //String proxy = "http://william.teixeira:sap0900@192.168.14.1:3128"; // Substitua pelos  detalhes do seu proxy
  
  private static final String URL = "http://glpi.sap.ce.gov.br/front/central.php"; 
  final ChromeOptions chromeOptions = new ChromeOptions().addArguments(); 
  private final ChromeDriver driverSelenium = new ChromeDriver();
  
  @PostConstruct void postConstruct() { 
	  //scrape("Fishstick"); 
	  fecharChrome();
  }
  
  public void scrape( String tipo, String mensagem, String requerente) { 
	  //String URL = "http://glpi.sap.ce.gov.br/front/central.php"; 
	  driverSelenium.get(URL);
	  driverSelenium.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
  
  
  driverSelenium.findElementByXPath("/html/body/div[1]/div[3]/form/p[1]/input").sendKeys("william.teixeira");
  driverSelenium.findElementByXPath("/html/body/div[1]/div[3]/form/p[2]/input").sendKeys("Sap@0754");
  driverSelenium.findElementByXPath("/html/body/div[1]/div[3]/form/p[5]/input").sendKeys(Keys.ENTER);
  
  //hardware/software 
  driverSelenium.get("http://glpi.sap.ce.gov.br/plugins/formcreator/front/formdisplay.php?id=29");
  
  driverSelenium.findElementByXPath("/html/body/div[2]/form/div[1]/div[2]/div[2]/span/span/span[1]/span/span[1]").click();
  
  driverSelenium.findElementByXPath("/html/body/span[2]/span/span[1]/input").sendKeys("UP-SOBRAL");
  driverSelenium.findElementByXPath("/html/body/span[2]/span/span[1]/input").sendKeys(Keys.ENTER); 
  driverSelenium.findElementByXPath("/html/body/span[2]/span/span[2]/ul/li/ul/li").click();
  
  WebDriverWait wait = new WebDriverWait(driverSelenium, 10); 
  WebElement iframe = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div[2]/form/div[1]/div[3]/div[2]/div[1]/div/div[2]/iframe")));
  
  // Trocar para o iframe do TinyMCE 
  driverSelenium.switchTo().frame(iframe);
  // Encontrar o corpo do editor TinyMCE e alterar o texto 
  WebElement body = driverSelenium.findElement(By.cssSelector("body")); 
  body.clear(); // Limpa o  texto atual 
  body.sendKeys(mensagem);
  
  
  driverSelenium.switchTo().defaultContent();
  
  //clicar no botao enviar
  driverSelenium.findElementByXPath("/html/body/div[2]/form/div[2]/input").click(); //clicar no link do suporte
  driverSelenium.findElementByXPath("/html/body/div[6]/div[2]/a").click();
  //clicar na aba chamado 
  driverSelenium.findElementByXPath("/html/body/div[2]/div[2]/div[2]/ul/li[1]/a").click(); //atribuir a mim mesmo
  driverSelenium.findElementByXPath("/html/body/div[2]/div[2]/div[2]/div[1]/form/div/div[1]/span[3]/div[1]/a/span").click();
  
  //trocar para aba processando chamado 
  driverSelenium.findElementByXPath("/html/body/div[2]/div[2]/div[2]/ul/li[2]/a").click();
  
  //botao solucao 
  driverSelenium.findElementByXPath("/html/body/div[2]/div[2]/div[2]/div[2]/div/div[1]/ul/li[4]").click();
  
  //driverSelenium.quit();
  
  }
  
  public void fecharChrome() { 
	  driverSelenium.quit(); 
	  }
  
  }
 