import base.TestBase;
import data.TestData;
import lib.Driver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import page.HomePage;
import page.ProductListPage;

import java.util.List;

public class ImageTest extends TestBase {

    @Test(dataProvider = "driverData", dataProviderClass = TestData.class)
    public void loginSuccessWithValidUserInfo(String driverName) {
        driver = Driver.getWebDriver(driverName);
        driver.get(HomePage.LOG_IN_URL);
        homePage = new HomePage(driver);
        homePage.AcceptCookies();
        ProductListPage productListPage= new ProductListPage(driver);

        int tabListSize=homePage.navigationTab().size();
        for(int tabIndex=0; tabIndex<tabListSize;tabIndex++){
            int navigationComponentIndex=0;
            homePage.navigationTab().get(tabIndex).click();
            Driver.sleep(5000);
            if(tabIndex==8){
                navigationComponentIndex=2;
            }
            homePage.navigationComponent().get(navigationComponentIndex).click();
            Driver.sleep(5000);
            List<WebElement> productList = productListPage.divListedProductList();
            for(int i=0;i<4;i++){
                WebElement productImage=productListPage.imageProductImage(productList.get(i));
                Assert.assertTrue(productImage.isDisplayed(),"Product image is not displayed");
            }
        }


    }




}
