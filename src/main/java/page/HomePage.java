package page;

import lib.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class HomePage {
    public static final String LOG_IN_URL = "https://www.trendyol.com/";
    WebDriver driver;

    public HomePage(WebDriver webDriver) {
        this.driver = webDriver;
    }

    WebElement buttonNavigateToLogIn() {
        return driver.findElement(By.xpath("//div[@data-fragment-name='AccountNavigation']//div[contains(@class,'account-user')]"));
    }

    WebElement buttonNavigateToBasket() {
        return driver.findElement(By.xpath("//div[@data-fragment-name='AccountNavigation']//a[contains(@class,'account-basket')]"));
    }
    WebElement buttonNavigateToFavorite() {
        return driver.findElement(By.xpath("//div[@data-fragment-name='AccountNavigation']//a[contains(@class,'account-favorites')]"));
    }

    WebElement buttonAcceptCookies() {
        return driver.findElement(By.xpath("//button[@id='onetrust-accept-btn-handler']"));
    }

    WebElement inputSearch() {
        return driver.findElement(By.xpath("//input[@class='search-box']"));
    }
    public List<WebElement> navigationTab() {
        return driver.findElements(By.xpath("//div[@id='navigation']//li[@class='tab-link']"));
    }
    public List<WebElement> navigationComponent() {
        return driver.findElements(By.xpath("//a[@class='item']"));
    }

    public void gotoLoginPage() {
        buttonNavigateToLogIn().click();
    }

    public void AcceptCookies() {
        buttonAcceptCookies().click();
        Driver.sleep(2000);
    }

    public void search(String text) {
        inputSearch().sendKeys(text);
        inputSearch().sendKeys(Keys.ENTER);
        Driver.sleep(3000);
    }


    public void gotoBasket() {
        buttonNavigateToBasket().click();
        Driver.sleep(4000);
    }


    public void gotoFavoritePage() {
        buttonNavigateToFavorite().click();
        Driver.sleep(4000);
    }


}
