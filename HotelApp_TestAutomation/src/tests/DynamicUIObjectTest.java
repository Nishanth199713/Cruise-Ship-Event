package tests;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import functions.HotelApp_BusinessFunctions;
import org.junit.runner.RunWith;
import junitparams.FileParameters;
import junitparams.JUnitParamsRunner;

@RunWith(JUnitParamsRunner.class)
public class DynamicUIObjectTest extends HotelApp_BusinessFunctions {
  private WebDriver driver;
  private String username,password;
  private StringBuffer verificationErrors = new StringBuffer();
  public static String sAppURL, sSharedUIMapPath;


 @BeforeClass
	public static void setUpBeforeClass() throws Exception {
	    Logger.getLogger("org.openqa.selenium.remote").setLevel(Level.OFF);	  
 }

  @Before
  public void setUp() throws Exception {
//		MAGIC CODE GOES HERE - Driver

	    System.setProperty("webdriver.chrome.driver","C:\\ChromeDriver\\chromedriver.exe");
	    driver = new ChromeDriver();
	    Properties prop = new Properties();	  
	    prop.load(new FileInputStream("./Login/Login.properties"));
		username = prop.getProperty("username");
		password = prop.getProperty("password");
//		Load Configuration file
	    prop.load(new FileInputStream("./Configuration/HA_Configuration.properties"));
		sAppURL = prop.getProperty("sAppURL");
		sSharedUIMapPath = prop.getProperty("SharedUIMap");	  
//		Load Shared UI Map
		prop.load(new FileInputStream(sSharedUIMapPath));
		
	 
  }

  @Test
  @FileParameters("src/Excel/Search Hotel1.csv")
  public void testMyDynamicObject(int testCaseNo, String location, String hotelName, String roomType, String numberRooms, String checkIn, 
		  String checkOut, String noAdults, String noChild, double ExpPriceNight, double totalPrice) throws Exception {
    driver.get(sAppURL);
    HA_BF_Login(driver, username,password); //Login
    searchHotel(driver, "Sydney", "Hotel Creek", "", "2 - Two", checkIn, checkOut, "2 - Two", ""); //Search Hotel
    bookHotel(driver,"Dummy", "James", "1234 Anywhere Ln\nCity, St 12345","0001000200030004","Master Card","January","2019","123");//Book hotel 
    Thread.sleep(10_000);   
    String strOrderNo = driver.findElement(By.id("order_no")).getAttribute("value");
    driver.findElement(By.id("my_itinerary")).click();
    driver.findElement(By.id("order_id_text")).click();
    driver.findElement(By.id("order_id_text")).sendKeys(strOrderNo);
    driver.findElement(By.id("search_hotel_id")).click();
    Thread.sleep(10_000);
    driver.findElements(By.xpath(".//* [contains(@id,'btn_id_')]")).get(0).click();
    Alert javascriptAlert = driver.switchTo().alert();  
    assertEquals("Are you sure, you want to cancel the booking with Order no. "+strOrderNo+"?", javascriptAlert.getText());
    driver.switchTo().alert().accept();
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
}