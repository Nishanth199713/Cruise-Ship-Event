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
public class M17_ParameterizationTest2 extends HotelApp_BusinessFunctions {
  private WebDriver driver;
  private String username,password;
  private StringBuffer verificationErrors = new StringBuffer();
  public static String sAppURL, sSharedUIMapPath;

//Add this for Jenkins to get rid of the org.openqa.selenium.remote.ProtocolHandshake createSession
//which causes it to report the test as failed due to "error"
 @BeforeClass
	public static void setUpBeforeClass() throws Exception {
	    Logger.getLogger("org.openqa.selenium.remote").setLevel(Level.OFF);	  
 }

  @Before
  public void setUp() throws Exception {
//		MAGIC CODE GOES HERE 
//		System.setProperty("webdriver.firefox.marionette", "C:\\GeckoSelenium\\geckodriver.exe");
//	    driver = new FirefoxDriver();
	    System.setProperty("webdriver.chrome.driver", "c:/ChromeDriver/chromedriver.exe");
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
		
	    //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
  }

  @Test
  @FileParameters("src/Excel/Search Hotel1.csv")
  public void testMyFirstWebDriver(int testCaseNo, String location, String hotelName, String roomType, String numberRooms, String checkIn, 
		  String checkOut, String noAdults, String noChild, double ExpPriceNight, double totalPrice) throws Exception {
    driver.get(sAppURL);
    HA_BF_Login(driver, username,password);
    searchHotel(driver, location, hotelName, roomType, numberRooms, checkIn, checkOut, noAdults, noChild);

    bookHotel(driver,"Dummy", "James", "1234 Anywhere Ln\nCity, St 12345","0001000200030004","Master Card","January","2019","123");
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