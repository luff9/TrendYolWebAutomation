package page;

import lib.Driver;
import model.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class BasketPage {
    WebDriver driver;

    public BasketPage(WebDriver webDriver) {
        this.driver = webDriver;
    }

    WebElement textProductBrandName() {
        return driver.findElement(By.xpath("//a[@class='pb-basket-item-details']/p/span"));
    }
    WebElement textProductDescription() {
        return driver.findElement(By.xpath("//a[@class='pb-basket-item-details']/p"));
    }
    WebElement textProductPrice() {
        return driver.findElement(By.xpath("//div[@class='pb-basket-item-price']"));
    }
    private WebElement textProductCount() {
        return driver.findElement(By.xpath("//input[@class='counter-content']"));
    }
    private List<WebElement> buttonDeleteProduct() {
        return driver.findElements(By.xpath("//i[@class='i-trash']/parent::button"));
    }
    private WebElement buttonDeleteProductConfirmation() {
        return driver.findElement(By.xpath("//div[contains(@class,'pb-item-remove-confirmation-modal')]//button[contains(text(),'Sil')]"));
    }

    private Product mapProduct() {
        Product product = new Product();
        String description=textProductDescription().getText();
        int index=description.indexOf("\n");
        description=description.substring(index).trim();

        product.setBrandName(textProductBrandName().getText());
        product.setDescription(description);
        product.setPrice(textProductPrice().getText());
        product.setCount(Integer.parseInt(textProductCount().getAttribute("Value")));
        return product;
    }
    public Product getProduct(){
        return mapProduct();
    }


    public void emptyBasket() {
        while (!buttonDeleteProduct().isEmpty()){
            buttonDeleteProduct().get(0).click();
            buttonDeleteProductConfirmation().click();
            Driver.sleep(4000);
        }


    }
}
