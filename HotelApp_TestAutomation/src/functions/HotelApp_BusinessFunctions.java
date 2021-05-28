package functions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class HotelApp_BusinessFunctions {

	public static WebDriver driver;
	public static Properties prop;
	
	public void HA_BF_Login(WebDriver driver, String sUserName, String sPassword) throws FileNotFoundException, IOException {
	    Properties prop = new Properties();	  

		prop.load(new FileInputStream("./SharedUIMap/SharedUIMap.properties"));

		driver.findElement(By.id(prop.getProperty("Login_username_text"))).clear();
	    driver.findElement(By.id(prop.getProperty("Login_username_text"))).sendKeys(sUserName);
	    driver.findElement(By.id(prop.getProperty("Login_password_text"))).clear();
	    driver.findElement(By.id(prop.getProperty("Login_password_text"))).sendKeys(sPassword);
	    driver.findElement(By.id(prop.getProperty("Login_login_btn"))).click();
		
	}
	
	public void searchHotel(WebDriver driver, String sLocation, String Hotelname,String roomtype, String sNoOfRooms,String checkin,String checkout, String sNoOfAdults,String sNoOfChild) throws FileNotFoundException, IOException {
		Properties prop = new Properties();	  

		prop.load(new FileInputStream("./SharedUIMap/SharedUIMap.properties"));

		new Select(driver.findElement(By.id(prop.getProperty("BookHotel_location_dropdown")))).selectByVisibleText(sLocation);
	    new Select(driver.findElement(By.id(prop.getProperty("BookHotel_noOfRooms_dropdown")))).selectByVisibleText(sNoOfRooms);
	    new Select(driver.findElement(By.id(prop.getProperty("BookHotel_noOfAdults_dropdown")))).selectByVisibleText(sNoOfAdults);
	    driver.findElement(By.id(prop.getProperty("BookHotel_Submit_btn"))).click();
	    driver.findElement(By.id(prop.getProperty("Rad_SelectHotel_RadioButton_0"))).click();
	    driver.findElement(By.id(prop.getProperty("Btn_SelectHotel_Continue"))).click();
	}
	
	public void bookHotel(WebDriver driver, String sFirstName, String sLastName, String sAddress, String sCCNum, String sCCType, String sCCExpMonth, String sCCExpYear, String sCCCvv) throws FileNotFoundException, IOException {
		Properties prop = new Properties();	  

		prop.load(new FileInputStream("./SharedUIMap/SharedUIMap.properties"));

		driver.findElement(By.id(prop.getProperty("FillForm_firstName_text"))).clear();
	    driver.findElement(By.id(prop.getProperty("FillForm_firstName_text"))).sendKeys(sFirstName);
	    driver.findElement(By.id(prop.getProperty("FillForm_lastName_text"))).clear();
	    driver.findElement(By.id(prop.getProperty("FillForm_lastName_text"))).sendKeys(sLastName);
	    driver.findElement(By.id(prop.getProperty("FillForm_address_text"))).clear();
	    driver.findElement(By.id(prop.getProperty("FillForm_address_text"))).sendKeys(sAddress);
	    driver.findElement(By.id(prop.getProperty("FillForm_ccNum_text"))).clear();
	    driver.findElement(By.id(prop.getProperty("FillForm_ccNum_text"))).sendKeys(sCCNum);
	    new Select(driver.findElement(By.id(prop.getProperty("FillForm_ccType_text")))).selectByVisibleText(sCCType);
	    new Select(driver.findElement(By.id(prop.getProperty("FillForm_ccExpMonth_text")))).selectByVisibleText(sCCExpMonth);
	    new Select(driver.findElement(By.id(prop.getProperty("FillForm_ccExpYear_text")))).selectByVisibleText(sCCExpYear);
	    driver.findElement(By.id(prop.getProperty("FillForm_ccCVV_text"))).clear();
	    driver.findElement(By.id(prop.getProperty("FillForm_ccCVV_text"))).sendKeys(sCCCvv);
	    driver.findElement(By.id(prop.getProperty("FillForm_bookNow_btn"))).click();
		
	}
	
}
