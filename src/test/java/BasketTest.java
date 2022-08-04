import base.TestBase;
import data.TestData;
import lib.Driver;
import model.Product;
import org.junit.jupiter.api.DisplayName;
import org.testng.Assert;
import org.testng.annotations.Test;
import page.*;

import java.util.ArrayList;

public class BasketTest extends TestBase {

    @Test(dataProvider = "addBasket", dataProviderClass = TestData.class)
    @DisplayName("Login=>Search=>Select brand=> set max & min price => add basket")
    public void addBasket(String driverName, String userName, String password, String searchText, String brand, int minPrice, int maxPrice) {
        driver = Driver.getWebDriver(driverName);
        driver.get(HomePage.LOG_IN_URL);
        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver);
        BasketPage basketPage = new BasketPage(driver);
        ProductListPage productListPage = new ProductListPage(driver);
        ProductPage productPage = new ProductPage(driver);

        homePage.acceptCookies();
        homePage.gotoLoginPage();
        loginPage.login(userName, password);
        homePage.gotoBasket();
        basketPage.emptyBasket();

        homePage.search(searchText);
        productListPage.selectBrand(brand);
        productListPage.setPriceRange(minPrice, maxPrice);
        ArrayList<String> selectedFilters = productListPage.getSelectedFilters();

        //Validate selected filter count and name
        Assert.assertEquals(selectedFilters.size(), 1, "Selected filter count mismatch");
        Assert.assertTrue(selectedFilters.contains(brand), "Selected filter name mismatch");

        //Validate listed products are between max and min price range
        //Validate brand name
        for (Product product : productListPage.getListedProducts()) {
            String productPrice = product.getPrice();
            productPrice = productPrice
                    .replace(".", "")
                    .replace("TL", "")
                    .strip();
            if (productPrice.contains(",")) {
                int commaIndex = productPrice.indexOf(",");
                productPrice = productPrice.substring(0, commaIndex);
            }
            int price = Integer.parseInt(productPrice);
            Assert.assertTrue(minPrice < price, "Price is less than min price");
            Assert.assertTrue(price < maxPrice, "Price is more than max price");
            Assert.assertEquals(product.getBrandName(), brand, "Brand name mismatch");

        }

        Product productFromProductListPage = productListPage.selectProduct();
        String title = driver.getTitle();
        String productBrandAndDescription = productFromProductListPage.getBrandName() + " " + productFromProductListPage
                .getDescription()
                .substring(0, 15);
        //Validate new tab(Product page) title contains product brand and description
        Assert.assertTrue(title.contains(productBrandAndDescription), "Page title mismatch");
        Product productFromProductPage = productPage.getProduct();

        //Validate new tab(Product page) contains correct product info
        validateProduct(productFromProductListPage, productFromProductPage);
        productPage.addToBasket();
        homePage.gotoBasket();

        Product productFromBasketPage = basketPage.getProduct();


        Assert.assertEquals(productFromBasketPage.getCount(), 1, "Product count mismatch");
        //Validate new tab(Basket page) contains correct product info
        validateProduct(productFromBasketPage, productFromProductPage);

    }

    void validateProduct(Product productFromProductListPage, Product productFromProductPage) {
        Assert.assertTrue(productFromProductPage
                .getDescription()
                .contains(productFromProductListPage.getDescription()), "Description mismatch");
    }

    @Test(dataProvider = "addFavoriteAndAddBasket", dataProviderClass = TestData.class)
    @DisplayName("Login=>Search=>Add to favorite =>Add basket")
    public void addFavoriteAndAddBasket(String driverName, String userName, String password, String searchText) {
        driver = Driver.getWebDriver(driverName);
        driver.get(HomePage.LOG_IN_URL);
        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver);
        BasketPage basketPage = new BasketPage(driver);
        ProductListPage productListPage = new ProductListPage(driver);
        FavoritePage favoritePage = new FavoritePage(driver);

        homePage.acceptCookies();
        homePage.gotoLoginPage();
        loginPage.login(userName, password);
        homePage.gotoBasket();
        basketPage.emptyBasket();
        homePage.gotoFavoritePage();
        favoritePage.deleteAllFavorites();

        homePage.search(searchText);

        Product productFromProductListPage = productListPage.addProductToFavorite();
        homePage.gotoFavoritePage();
        Product productFromFavoritePage = favoritePage.addProductToBasket();
        validateProduct(productFromProductListPage, productFromFavoritePage);
        homePage.gotoBasket();
        Product productFromBasketPage = basketPage.getProduct();


        Assert.assertEquals(productFromBasketPage.getCount(), 1, "Product count mismatch");
        //Validate new tab(Basket page) contains correct product info
        validateProduct(productFromBasketPage, productFromFavoritePage);


    }


}
