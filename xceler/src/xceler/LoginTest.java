package xceler;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginTest {
	WebDriver driver;
	WebDriverWait wait;
	@BeforeClass
	public void configbeforeclass(){
		System.setProperty("webdriver.chrome.driver", "D:\\selenium\\selenium neww\\chromedriver.exe");
		driver=new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		driver.get("https://xcelerator.ninja/");		
		wait=new WebDriverWait(driver, 120);		
	}
	@BeforeMethod
	public void configbeforemethod(){
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[text()='LOGIN']")));
		driver.findElement(By.xpath("//button[text()='LOGIN']")).click();
		Reporter.log("login button clicked");
	}
	@Test(priority=1)
	public void verify_valid_input(){
		String email="xceletorninja@gmail.com";
		String password="xc123456789";
		driver.findElement(By.xpath("//input[@name='email']")).sendKeys(email);
		Reporter.log("valid email id");
		driver.findElement(By.xpath("//input[@name='password']")).sendKeys(password);
		Reporter.log("valid password");
		driver.findElement(By.xpath("//button[@class='loginModalButton']")).click();
		Reporter.log("button clicked");	
		
		String expusername="Welcome Ranadip";
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='heading-content']/h1")));
		String actUsername=driver.findElement(By.xpath("//div[@class='heading-content']/h1")).getText();
		Reporter.log("get the username from dashboard");	
		Assert.assertEquals(actUsername, expusername,"expected result doesnt match");		
		String expURL="https://xcelerator.ninja/dashboard";
		String actURL=driver.getCurrentUrl();
		Assert.assertEquals(actURL, expURL,"url doesnt match");		
		driver.findElement(By.xpath("//span[@class='caret']")).click();		
		driver.findElement(By.xpath("//a[text()='Logout']")).click();
		Reporter.log("logged out");	
		
	}
	@Test(dataProvider="DataInput",priority=2)
	public void verify_invalid_input(String userName, String password) 
	{	
		driver.findElement(By.xpath("//input[@name='email']")).sendKeys(userName);
		Reporter.log("valid email id");
		driver.findElement(By.xpath("//input[@name='password']")).sendKeys(password);
		Reporter.log("valid password");
		driver.findElement(By.xpath("//button[@class='loginModalButton']")).click();
		Reporter.log("button clicked");		
		String actErrormsg=driver.findElement(By.xpath("//*[text()='Please enter correct username and password']")).getText();
		String expErrormsg="Please enter correct username and password";
		Assert.assertEquals(actErrormsg, expErrormsg,"error message doesnt matched");
		driver.navigate().refresh();	
	
	}
    @DataProvider(name="DataInput")
    public static Iterator fetchData() throws InvalidFormatException, IOException{
        ArrayList myData = new ArrayList();
        FileInputStream fis = new FileInputStream("C:\\Users\\admin123\\Desktop\\xceler\\xceler\\loginInputs.xlsx");
        Workbook wb = WorkbookFactory.create(fis);
        Sheet sh = wb.getSheet("Sheet1");
        int numOfRows = sh.getLastRowNum();
        String userName, password;
        for(int i=1; i<numOfRows; i++){
            userName = sh.getRow(i).getCell(0).getStringCellValue();
            password = sh.getRow(i).getCell(1).getStringCellValue();
            
            myData.add(new Object[]{userName,password});
        }
        return myData.iterator();
    }
 
	@AfterClass
	public void configafterclass(){
		driver.close();
	}
}












