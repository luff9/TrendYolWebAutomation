package page;

import lib.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class LoginPage {
    WebDriver driver;

    public LoginPage(WebDriver webDriver) {
        this.driver = webDriver;
    }

    WebElement textBoxEMail() {
        return driver.findElement(By.xpath("//input[@id='login-email']"));
    }

    WebElement textBoxPassword() {
        return driver.findElement(By.xpath("//input[@id='login-password-input']"));
    }

    WebElement buttonLogin() {
        return driver.findElement(By.xpath("//div[@id='login-register']//button[@type='submit']"));
    }

    public List<WebElement> loggedInUser() {
        return driver.findElements(By.xpath("//div[@id='account-navigation-container']//div[@class='user-loggedin-container']//p[@class='user-name']"));
    }

    public WebElement getLoggInErrorMessage() {
        return driver.findElement(By.xpath("//div[@id='error-box-wrapper']"));
    }

    public void login(String eMail, String password) {
        textBoxEMail().sendKeys(eMail);
        textBoxPassword().sendKeys(password);
        buttonLogin().click();
        Driver.sleep(3000);
    }

    public String getLoggedInUserEMail() {
        return loggedInUser()
                .get(0)
                .getAttribute("innerText");
    }
}
