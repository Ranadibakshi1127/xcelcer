package xceler;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class test {

	public static void main(String[] args) {
		System.setProperty("webdriver.chrome.driver", "D:\\selenium\\selenium neww\\chromedriver.exe");
		WebDriver driver=new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		driver.get("https://xcelerator.ninja/");		
		WebDriverWait wait=new WebDriverWait(driver, 120);
		//driver.findElement(By.xpath("//*[text()='About']")).click();
		driver.findElement(By.xpath("//img[@src='/static/25ee574cfc3d1ee07c8f16bcc65bb7b1.png']/../../../center/button")).click();
		
	}

}
