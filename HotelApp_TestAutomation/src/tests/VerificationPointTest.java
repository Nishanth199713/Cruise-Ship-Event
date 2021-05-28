package tests;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class VerificationPointTest<WebDriver> {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    System.setProperty("webdriver.chrome.driver","C:/ChromeDriver/chromedriver.exe");
    driver = new ChromeDriver();
    baseUrl = "http://adactin.com/";
  }

  @Test
  public void VerificationPointTest() throws Exception {
    driver.get(baseUrl + "/HotelApp/");
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("Samiksha5");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("Sakshi12$");
    driver.findElement(By.id("login")).click();
 
    new Select(driver.findElement(By.id("location"))).selectByVisibleText("London");
    new Select(driver.findElement(By.id("hotels"))).selectByVisibleText("Hotel Hervey");
    driver.findElement(By.id("datepick_in")).clear();
    driver.findElement(By.id("datepick_in")).sendKeys("21/10/2020");
    driver.findElement(By.id("datepick_out")).clear();
    driver.findElement(By.id("datepick_out")).sendKeys("28/10/2020");    
    driver.findElement(By.id("Submit")).click();
    
    Thread.sleep(2_000);
    
   for(int i = 1 ; i < 5 ; i++) 
   {
	   System.out.print("Verify the Location is London for all hotels shown");
   	   String Loc = driver.findElement(By.xpath (".//*[@id='location_"+i+"']")).getAttribute("value");
   	   System.out.println(Loc+" "+".//*[@id='location_"+i+"']");
       assertEquals("London", Loc);
       System.out.println(Loc);
       System.out.println("Verify the total rate is calculated correctly ");
       String pricePNight = driver.findElement(By.xpath(".//*[@id='price_night_"+i+"']")).getAttribute("value");
       pricePNight = pricePNight.replace("AUD $ ","");
       System.out.println(pricePNight);

       int pricePNight1 = 0;
       pricePNight1 = Integer.parseInt(pricePNight);
       System.out.println(pricePNight1);
       String totalprice = driver.findElement(By.xpath(".//*[@id='total_price_"+i+"']")).getAttribute("value");
       totalprice=totalprice.replace("AUD $ ","");
       System.out.println(totalprice);

       int expectedtotal=0;
       expectedtotal= (pricePNight1*7) + 10;
       System.out.println(expectedtotal);

       assertEquals(Integer.toString(expectedtotal), totalprice);
       
   }


    driver.findElement(By.linkText("Logout")).click();
    driver.findElement(By.linkText("Click here to login again")).click();
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
