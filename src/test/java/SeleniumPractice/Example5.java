package SeleniumPractice;

import io.cucumber.java.eo.Se;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


import java.time.Duration;

public class Example5 {


        WebDriver driver;


        @BeforeTest
         public void setUp(){
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        }

        @Test()
        public void openAsos(){
                driver.get("https://www.asos.com/us/");
                driver.manage().window().maximize();

                String actual = driver.getCurrentUrl();
                String expected = "https://www.asos.com/us/";

                Assert.assertEquals(actual,expected);

        }

        @Test(dependsOnMethods = {"openAsos"})
        public void clickDropDown(){
                WebElement dropdown = driver.findElement(By.xpath("//button[@aria-label='My Account']"));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",dropdown);

                dropdown.click();

                WebElement option = driver.findElement(By.xpath("//ul[@class='F7CGtue']/child::li[1]/child::*"));
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", option);

                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

                String title = driver.getTitle();
                Assert.assertEquals(title, "ASOS | Sign in","Title not displayed");
        }

        @Test(dependsOnMethods = {"clickDropDown","openAsos"})
        public void signUp(){
                driver.findElement(By.xpath("//div[@class='title qa-title with-link']")).click();
                driver.findElement(By.xpath("//input[@id='Email']")).sendKeys("abc123@gmail.com");
                driver.findElement(By.xpath("//input[@id='FirstName']")).sendKeys("Saskue");
                driver.findElement(By.xpath("//input[@id='Email']/following::input[2]")).sendKeys("Uchiha");
                driver.findElement(By.xpath("//legend[@for='BirthDay']/parent::*/parent::*/parent::*/descendant::input[4]")).sendKeys("ItachiIsABitch");

                WebElement dropdown2 = driver.findElement(By.xpath("//select[@id='BirthDay']"));
                WebElement dropdown3 = driver.findElement(By.xpath("//select[@id='BirthMonth']"));
                WebElement dropdown4 = driver.findElement(By.xpath("//select[@id='BirthYear']"));

                Select dropdownDate = new Select(dropdown2);
                Select dropdownMonth = new Select(dropdown3);
                Select dropdownYear = new Select(dropdown4);

                dropdownDate.selectByVisibleText("18");
                dropdownMonth.selectByVisibleText("June");
                dropdownYear.selectByVisibleText("1993");
        }

        @Test(dependsOnMethods = {"signUp","clickDropDown"})
        public void clickRadioButton(){
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//input[@id='male']")));

                // Wait for the presence of the radio button with a timeout of 10 seconds
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                WebElement radioButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='male']")));

                // Click the radio button using JavaScriptExecutor
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", radioButton);

//                Assert.fail();

        }


//        @AfterTest
//        void tearDown(){
//                driver.close();
//        }

}
