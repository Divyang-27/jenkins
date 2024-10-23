package extent;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import io.github.bonigarcia.wdm.WebDriverManager;

public class rep_class {
    public static String van = "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login";
    ExtentReports extent;
    ExtentHtmlReporter htmlReporter;
    ExtentTest test;
    WebDriver driver;

    @BeforeTest
    public void bt() {
        // Setup Chrome Driver
        WebDriverManager.chromedriver().setup();
        
        // Set Chrome options for headless mode
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // Enable headless mode
        options.addArguments("--window-size=1920,1080"); // Set window size for the headless browser
        
        driver = new ChromeDriver(options);
        driver.get(van);
        driver.manage().window().maximize();
        
        extent = new ExtentReports();
        htmlReporter = new ExtentHtmlReporter("extent.html");
        extent.attachReporter(htmlReporter);
    }

    @Test(enabled = true)
    public void login() throws InterruptedException {
        System.out.println("orangehrm is opened");
        test = extent.createTest("this is my first test");
        Thread.sleep(4000);
        
        WebElement user = driver.findElement(By.name("username"));
        if (user.isEnabled()) {
            user.sendKeys("Admin");
            test.pass("entered username");
        }
        
        Thread.sleep(4000);
        
        WebElement pass = driver.findElement(By.name("password"));
        Thread.sleep(4000);
        if (pass.isEnabled()) {
            pass.sendKeys("admin123");
            test.pass("entered password");
        }
        
        Thread.sleep(4000);
        driver.findElement(By.xpath("//*[@type='submit']")).click();
        test.pass("submitted");
        
        Thread.sleep(4000);
        driver.findElement(By.linkText("Admin")).click();
        test.pass("clicked on admin");
        
        Thread.sleep(4000);
    }

    @AfterTest
    public void at() {
        driver.close();
        extent.flush();
    }
}
