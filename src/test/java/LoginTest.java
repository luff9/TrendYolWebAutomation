import base.TestBase;
import data.TestData;
import lib.Driver;
import org.junit.jupiter.api.DisplayName;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import page.HomePage;
import page.LoginPage;

public class LoginTest extends TestBase {

    @Test(dataProvider = "driverAndValidUserData", dataProviderClass = TestData.class)
    @DisplayName("Login should be successful with valid credentials")
    public void loginSuccessWithValidUserInfo(String driverName, String userEMail, String password) {
        driver = Driver.getWebDriver(driverName);
        driver.get(HomePage.LOG_IN_URL);

        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver);

        homePage.acceptCookies();
        homePage.gotoLoginPage();
        loginPage.login(userEMail, password);
        Assert.assertEquals(loginPage.getLoggedInUserEMail(), userEMail, "Expected user is not logged in");

    }

    @Test(dataProvider = "driverAndInvalidUserData", dataProviderClass = TestData.class)
    @DisplayName("Login should be fail with invalid credentials")
    public void loginFailWithWrongUserInfo(String driverName, String userEMail, String password) {
        WebDriver driver = Driver.getWebDriver(driverName);
        driver.get(HomePage.LOG_IN_URL);

        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver);
        homePage.acceptCookies();
        homePage.gotoLoginPage();
        loginPage.login(userEMail, password);

        Assert.assertTrue(loginPage
                .getLoggInErrorMessage()
                .isDisplayed(), "Expected warning is not displayed");
        Assert.assertEquals(loginPage
                .loggedInUser()
                .size(), 0, "Expected warning is not displayed");


    }


}
