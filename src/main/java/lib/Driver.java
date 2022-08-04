package lib;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;
import java.util.ArrayList;

public class Driver {

    public static WebDriver getWebDriver(String webDriver) {
        WebDriver driver;
        switch (webDriver) {
            case "firefoxdriver":
                driver = getFirefoxDriver();
                break;
            case "edgedriver":
                driver = getEdgeDriver();
                break;
            case "chromedriver":
            default:
                driver = getChromeDriver();
                break;
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        return driver;
    }

    private static WebDriver getEdgeDriver() {
        EdgeOptions edgeOptions = new EdgeOptions();
        edgeOptions.addArguments("--disable-notifications");
        edgeOptions.addArguments("--start-maximized");
        edgeOptions.addArguments("--disable-infobars");
        return WebDriverManager.edgedriver().capabilities(edgeOptions).create();
    }

    private static WebDriver getFirefoxDriver() {
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.addArguments("--disable-notifications");
        firefoxOptions.addArguments("--start-maximized");
        firefoxOptions.addArguments("--disable-infobars");
        return WebDriverManager.firefoxdriver().capabilities(firefoxOptions).create();
    }

    private static WebDriver getChromeDriver() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("disable-infobars");
        chromeOptions.addArguments("--start-maximized");
        chromeOptions.addArguments("--disable-notifications");
        return WebDriverManager.chromedriver().capabilities(chromeOptions).create();
    }

    public static void scrollIntoViewTop(WebDriver driver, WebElement el) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({block: \"start\"});", el);
        sleep(3000);
    }
    public static void sleep(int duration) {
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public static void closeOldTabAndSwitchNewTab(WebDriver driver){
        sleep(3000);
        driver.close();
        ArrayList<String> newTab = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(newTab.get(0));
    }

}
