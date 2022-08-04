import base.TestBase;
import data.TestData;
import lib.Driver;
import model.Product;
import org.testng.Assert;
import org.testng.annotations.Test;
import page.*;

public class BasketTest extends TestBase {

    @Test(dataProvider = "addBasket_data", dataProviderClass = TestData.class)
    public void addBasket(String driverName, String userName, String password, String searchText, String brand, int minPrice, int maxPrice) {
        driver = Driver.getWebDriver(driverName);
        driver.get(HomePage.LOG_IN_URL);
        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver);
        BasketPage basketPage = new BasketPage(driver);
        ProductListPage productListPage = new ProductListPage(driver);
        ProductPage productPage = new ProductPage(driver);

        homePage.AcceptCookies();
        homePage.gotoLoginPage();
        loginPage.login(userName, password);
        homePage.gotoBasket();
        basketPage.emptyBasket();

        homePage.search(searchText);
        productListPage.selectBrand(brand);
        productListPage.setPriceRange(minPrice, maxPrice);
        productListPage.getSelectedFilters();

        //Validate selected filter count and name
        Assert.assertEquals(productListPage
                .getSelectedFilters()
                .size(), 1, "Selected filter count mismatch");
        Assert.assertTrue(productListPage
                .getSelectedFilters()
                .contains(brand), "Selected filter name mismatch");

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
                .substring(0, 30);
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
        Assert.assertEquals(productFromProductListPage.getPrice(), productFromProductPage.getPrice(), "Price mismatch");
        Assert.assertEquals(productFromProductListPage
                .getBrandName()
                .toLowerCase(), productFromProductPage
                .getBrandName()
                .toLowerCase(), "Brand mismatch");
        Assert.assertTrue(productFromProductPage
                .getDescription()
                .contains(productFromProductListPage.getDescription()), "Description mismatch");
    }

    @Test(dataProvider = "addFavoriteAndAddBasket_data", dataProviderClass = TestData.class)
    public void addFavoriteAndAddBasket(String driverName, String userName, String password, String searchText) {
        driver = Driver.getWebDriver(driverName);
        driver.get(HomePage.LOG_IN_URL);
        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver);
        BasketPage basketPage = new BasketPage(driver);
        ProductListPage productListPage = new ProductListPage(driver);
        FavoritePage favoritePage = new FavoritePage(driver);

        homePage.AcceptCookies();
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
