package page;

import lib.Driver;
import model.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class FavoritePage {
    WebDriver driver;

    public FavoritePage(WebDriver webDriver) {
        this.driver = webDriver;
    }

    WebElement textProductBrandName(WebElement el) {
        return el.findElement(By.xpath(".//span[@class='prdct-desc-cntnr-ttl']"));
    }

    WebElement textProductDescription(WebElement el) {
        return el.findElement(By.xpath(".//span[contains(@class, 'prdct-desc-cntnr-name')]"));
    }

    WebElement textProductPrice(WebElement el) {
        return el.findElement(By.xpath(".//div[@class='prc-box-dscntd']"));
    }

    WebElement buttonUnFavoriteProduct(WebElement el) {
        return el.findElement(By.xpath(".//i[@class='i-close']"));
    }

    WebElement buttonAddBasket(WebElement el) {
        return el.findElement(By.xpath(".//div[contains(@class,'basket-button')]"));
    }

    List<WebElement> divProductList() {
        return driver.findElements(By.xpath("//div[@class='favored-product-container']//div[@class='p-card-wrppr']"));
    }


    public void deleteAllFavorites() {
        while (!divProductList().isEmpty()) {
            buttonUnFavoriteProduct(divProductList().get(0)).click();
            Driver.sleep(2000);
        }


    }


    public Product addProductToBasket() {
        WebElement el = divProductList().get(0);
        Product product = mapProduct(el);
        buttonAddBasket(el).click();
        Driver.sleep(2000);
        return product;
    }

    private Product mapProduct(WebElement el) {
        Product product = new Product();
        product.setBrandName(textProductBrandName(el).getText());
        product.setDescription(textProductDescription(el).getText());
        product.setPrice(textProductPrice(el).getText());
        product.setDataID(el.getAttribute("data-id"));
        return product;
    }
}
