package page;

import model.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class ProductPage {
    WebDriver driver;

    public ProductPage(WebDriver webDriver) {
        this.driver = webDriver;
    }

    WebElement buttonAddToBasket() {
        return driver.findElement(By.xpath("//button[@class='add-to-basket']"));
    }

    WebElement textProductBrandName() {
        return driver.findElement(By.xpath("//h1[@class='pr-new-br']/a"));
    }

    WebElement textProductDescription() {
        return driver.findElement(By.xpath("//h1[@class='pr-new-br']/span"));
    }

    WebElement textProductPrice() {
        return driver.findElement(By.xpath("//div[@class='product-price-container']//span[@class='prc-dsc']"));
    }

    private Product mapProduct() {
        Product product = new Product();
        product.setBrandName(textProductBrandName().getText());
        product.setDescription(textProductDescription().getText());
        product.setPrice(textProductPrice().getText());
        return product;
    }


    public Product getProduct() {
        return mapProduct();
    }

    public void addToBasket() {
        buttonAddToBasket().click();
    }


}
