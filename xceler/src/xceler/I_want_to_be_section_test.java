package xceler;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class I_want_to_be_section_test {
	
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
	
	@Test(dataProvider="DataInput")
	public void validate_I_want_to_be_section(String linkName, String expres) throws AWTException{	
		driver.findElement(By.xpath("//*[text()='Others']")).click();
		driver.findElement(By.xpath("//*[text()='"+linkName+"']")).click();
		String acttext=driver.findElement(By.xpath("//h3[@class='selectedJobFamily']")).getText();		
		Assert.assertEquals(acttext, expres);
		driver.findElement(By.xpath("//div[@class='Select-placeholder']")).click();
		Robot robot=new Robot();
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);		
		driver.findElement(By.xpath("//span[@class='Select-arrow']")).click();
		driver.findElement(By.xpath("//button[@class='pickJobRoleModalButton']")).click();
		String expdata="Kudos, Ninja!";
		String actdata=driver.findElement(By.xpath("//h3[text()='Kudos, Ninja!']")).getText();
		Assert.assertEquals(actdata, expdata);
		driver.navigate().refresh();
		
	}
	
	@DataProvider(name="DataInput")
    public static Iterator fetchData() throws InvalidFormatException, IOException{
        ArrayList myData = new ArrayList();
        FileInputStream fis = new FileInputStream("C:\\Users\\admin123\\Desktop\\xceler\\xceler\\xceler_home_link_Section.xlsx");
        Workbook wb = WorkbookFactory.create(fis);
        Sheet sh = wb.getSheet("Sheet2");
        int numOfRows = sh.getLastRowNum();
        String linkName, expres;
        for(int i=1; i<=numOfRows; i++){
            linkName = sh.getRow(i).getCell(0).getStringCellValue();
            expres = sh.getRow(i).getCell(1).getStringCellValue();
            
            myData.add(new Object[]{linkName,expres});
        }
        return myData.iterator();
    }
	
	 @AfterClass
	  public void configafterclass(){
		  driver.close();
	  }

}
