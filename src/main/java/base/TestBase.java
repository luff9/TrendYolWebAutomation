package base;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import page.HomePage;
import page.LoginPage;

public class TestBase {
    public WebDriver driver;
    public HomePage homePage;
    public LoginPage loginPage;

    @BeforeTest
    public void setUp(){

    }
    @AfterTest
    public void tearDown(){
    driver.quit();
    }

}
