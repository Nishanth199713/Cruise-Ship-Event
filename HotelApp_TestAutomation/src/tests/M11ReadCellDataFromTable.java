package tests;

import java.util.List;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import functions.HotelApp_BusinessFunctions1;
import org.junit.runner.RunWith;
import junitparams.FileParameters;
import junitparams.JUnitParamsRunner;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

@RunWith(JUnitParamsRunner.class)
public class M11ReadCellDataFromTable extends HotelApp_BusinessFunctions1{
//  private WebDriver driver;
  private StringBuffer verificationErrors = new StringBuffer();
  public static String sAppURL, sSharedUIMapPath,username,password;

  public void PrintHotelTableContents() {
	  //Get all rows
	  List<WebElement> rows= driver.findElement(By.xpath(".//*[@id='select_form']/table/tbody/tr[2]/td/table")).findElements(By.tagName("tr"));

	  //print out header row
	  for (int i=2;i<11;i++)
		  System.out.print(driver.findElement(By.xpath(".//*[@id='select_form']/table/tbody/tr[2]/td/table/tbody/tr[1]/td["+i+"]")).getText()+"\t");
	  System.out.println();
	  
	  //Output data from each row
	  for (int i=0;i<rows.size()-1;i++) {

		  System.out.print(driver.findElement(By.xpath(".//*[@id='hotel_name_"+i+"']")).getAttribute("value")+"\t");
		  System.out.print(driver.findElement(By.xpath(".//*[@id='location_"+i+"']")).getAttribute("value")+"\t");
		  System.out.print(driver.findElement(By.xpath(".//*[@id='rooms_"+i+"']")).getAttribute("value")+"\t");
		  System.out.print(driver.findElement(By.xpath(".//*[@id='arr_date_"+i+"']")).getAttribute("value")+"\t");
		  System.out.print(driver.findElement(By.xpath(".//*[@id='dep_date_"+i+"']")).getAttribute("value")+"\t");
		  System.out.print(driver.findElement(By.xpath(".//*[@id='no_days_"+i+"']")).getAttribute("value")+"\t"+"\t");
		  System.out.print(driver.findElement(By.xpath(".//*[@id='room_type_"+i+"']")).getAttribute("value")+"\t");
		  System.out.print(driver.findElement(By.xpath(".//*[@id='price_night_"+i+"']")).getAttribute("value")+"\t");
		  System.out.print(driver.findElement(By.xpath(".//*[@id='total_price_"+i+"']")).getAttribute("value")+"\t");		  
		  System. out.println();
	  }
  }
  
//Add this for Jenkins to get rid of the org.openqa.selenium.remote.ProtocolHandshake createSession
//which causes it to report the test as failed due to "error"
 @BeforeClass
	public static void setUpBeforeClass() throws Exception {
	    Logger.getLogger("org.openqa.selenium.remote").setLevel(Level.OFF);	  
 }
 	
  @Before
  public void setUp() throws Exception {
//	MAGIC CODE GOES HERE 
	System.setProperty("webdriver.chrome.driver","C:\\ChromeDriver\\chromedriver.exe");
    driver = new ChromeDriver();
    prop = new Properties();	  
    prop.load(new FileInputStream("./Login/Login.properties"));
	username = prop.getProperty("username");
	password = prop.getProperty("password");
//	Load Configuration file
    prop.load(new FileInputStream("./Configuration/HA_Configuration.properties"));
	sAppURL = prop.getProperty("sAppURL");
	sSharedUIMapPath = prop.getProperty("SharedUIMap");	  
	
//	Load Shared UI Map
	prop.load(new FileInputStream(sSharedUIMapPath));
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
  }

  @Test
  @FileParameters("src/Excel/Search Hotel1.csv")
  public void myParameterizationTest(int testCaseNo, String location, String hotelName, String roomType, String numberRooms, String checkIn, 
		  String checkOut, String noAdults, String noChild, double ExpPriceNight, double totalPrice) throws Exception {
	    driver.get(sAppURL);

    HA_BF_Login(driver, username,password);
//  Get method name for screenshot
    String methodName= new Throwable().getStackTrace()[0].getMethodName();
	takeScreenshot(driver,methodName+" SearchHotelPage");
	searchHotel(driver, location, hotelName, roomType, numberRooms, checkIn, checkOut, noAdults, noChild);
	takeScreenshot(driver,methodName+" SelectHotelPage");
	PrintHotelTableContents();
	
	assertEquals(ExpPriceNight,Double.parseDouble(driver.findElement(By.id("price_night_0")).getAttribute("value").replace("AUD $ ","")),0.01);
	assertEquals(totalPrice,Double.parseDouble(driver.findElement(By.id("total_price_0")).getAttribute("value").replace("AUD $ ","")),0.01);

	driver.findElement(By.id(prop.getProperty("Rad_SelectHotel_RadioButton_0"))).click();
    driver.findElement(By.id(prop.getProperty("Btn_SelectHotel_Continue"))).click();

    bookHotel(driver,"Dummy", "James", "1234 Anywhere Ln\nCity, St 12345","0001000200030004","Master Card","January","2019","123");

	driver.findElement(By.linkText(prop.getProperty("Lnk_BookingHotel_Logout"))).click();
    driver.findElement(By.linkText(prop.getProperty("Lnk_Logout_ClickHeretoLoginAgain"))).click();
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