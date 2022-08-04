package page;

import lib.Driver;
import model.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class ProductListPage {
    public static final String FILTER_NAME_FIYAT = "Fiyat";
    public static final String FILTER_NAME_MARKA = "Marka";
    WebDriver driver;

    public ProductListPage(WebDriver webDriver) {
        this.driver = webDriver;
    }

    WebElement inputBrandSearch() {
        return driver.findElement(By.xpath("//div[@data-title='Marka']//input"));
    }
    List<WebElement>  overlayOption() {
        return driver.findElements(By.xpath("//div[@class='overlay']"));
    }

    WebElement textBrandName(String brandName) {
        return driver.findElement(By.xpath("//div[normalize-space()='" + brandName + "']"));
    }

    WebElement textFilterName(String filterName) {
        return driver.findElement(By.xpath("//div[normalize-space()='" + filterName + "']"));
    }

    WebElement inputMaxPrice() {
        return driver.findElement(By.xpath("//input[@class='fltr-srch-prc-rng-input max']"));
    }

    WebElement inputMinPrice() {
        return driver.findElement(By.xpath("//input[@class='fltr-srch-prc-rng-input min']"));
    }

    WebElement buttonPriceSearch() {
        return driver.findElement(By.xpath("//button[@class='fltr-srch-prc-rng-srch']"));
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
    public WebElement imageProductImage(WebElement el) {
        return el.findElement(By.xpath(".//img[@class='p-card-img']"));
    }
    private WebElement buttonAddFavorite(WebElement el) {
        return el.findElement(By.xpath(".//i[@class='fvrt-btn']"));
    }

    List<WebElement> webElementFilter(String filterName) {
        return driver.findElements(By.xpath("//div[@data-title='" + filterName + "']"));
    }
    List<WebElement> textSelectedFilters() {
        return driver.findElements(By.xpath("//div[@class='slctd-fltrs-cntnr']//div[@class='slctd-fltr-item']//span"));
    }
    public List<WebElement> divListedProductList() {
        refreshIfOverlayExist();
        return driver.findElements(By.xpath("//div[contains(@class,'p-card-wrppr')]"));
    }


    public void selectBrand(String brand) {
        openFilter(FILTER_NAME_MARKA);
        inputBrandSearch().sendKeys(brand);
        textBrandName(brand).click();
        Driver.sleep(3000);
    }

    public void openFilter(String filterName) {
        if (webElementFilter(filterName).isEmpty()) {
            textFilterName(filterName).click();
        }

    }


    public void setPriceRange(int minPrice, int maxPrice) {
        openFilter(FILTER_NAME_FIYAT);
        inputMinPrice().sendKeys(String.valueOf(minPrice));
        inputMaxPrice().sendKeys(String.valueOf(maxPrice));
        buttonPriceSearch().click();
        Driver.sleep(3000);
    }


    public ArrayList<String> getSelectedFilters() {
        ArrayList<String> ret1 = new ArrayList<>();
        for (WebElement el : textSelectedFilters()) {
            if (!el.getText().isEmpty()) {
                ret1.add(el.getText());
            }
        }
        return ret1;
    }
    public ArrayList<Product> getListedProducts(){
        ArrayList<Product> ret1= new ArrayList<>();
        boolean isLazyLoadingFinished=false;
        while(!isLazyLoadingFinished) {
            List<WebElement> productDivArr = divListedProductList();
            for (WebElement el : productDivArr) {
                Product product = mapProduct(el);
                if(!ret1.contains(product)){
                    ret1.add(product);
                }
            }
            isLazyLoadingFinished=isLazyLoadingFinished(productDivArr);
        }
        return ret1;
    }

    private Product mapProduct(WebElement el) {
        Product product = new Product();
        product.setBrandName(textProductBrandName(el).getText());
        product.setDescription(textProductDescription(el).getText());
        product.setPrice(textProductPrice(el).getText());
        product.setDataID(el.getAttribute("data-id"));
        return product;
    }

    public Product selectProduct(){
        List<WebElement> productDivArr = divListedProductList();
        Driver.scrollIntoViewTop(driver,productDivArr.get(0));
        Product product = mapProduct(productDivArr.get(0));
        textProductDescription(productDivArr.get(0)).click();
        Driver.closeOldTabAndSwitchNewTab(driver);
        return product;
    }
    private boolean isLazyLoadingFinished(List<WebElement> webElementList){
        Driver.scrollIntoViewTop(driver,webElementList.get(webElementList.size()-1));
        List<WebElement> productDivArr = divListedProductList();
        for (WebElement el:productDivArr){
            if(!webElementList.contains(el))
                return false;
        }
        return true;
    }


    public Product addProductToFavorite() {
        refreshIfOverlayExist();
        List<WebElement> productDivArr = divListedProductList();
        Product product = mapProduct(productDivArr.get(0));
        buttonAddFavorite(productDivArr.get(0)).click();
        Driver.sleep(3000);
        return product;
    }
    public void refreshIfOverlayExist(){
        if(!overlayOption().isEmpty()){
            driver.navigate().refresh();
            Driver.sleep(3000);
        }
    }


}
