package data;

import org.testng.annotations.DataProvider;

public class TestData {


    @DataProvider(name = "driverData")
    public static Object[][] getDriverList() {
        return new Object[][]{
//                {"firefoxdriver"},
//                {"edgedriver"},
                {"chromedriver"}};
    }

    public static Object[][] getValidUserInfoList() {
        return new Object[][]{
                {"t38t3r@gmail.com", "Tester2022!"}
        };
    }

    public static Object[][] getInvalidUserInfoList() {
        return new Object[][]{{"t38t3r@gmail.com", "asd"}
//                , {"t38t3r@gmail.", "Tester2022!"}
//                , {"t38t3rasdf@gmail.com", "Testeasdf2!"}
//                , {"", "Tester2022!"}
//                , {"t38t3r@gmail.com", ""}
        };
    }

    public static Object[][] getProductWithSearchTextBrandMinMaxPrice() {
        return new Object[][]{
                {"Oyuncu Bilgisayarı", "Monster", 3000, 20000}
                , {"Oyuncu Bilgisayarı", "Monster", 5000, 40000}
                , {"Oyuncu Bilgisayarı", "Monster", 3000, 10000}
                , {"Oyuncu Bilgisayarı", "Monster", 30000, 200000}
        };
    }

    public static Object[][] getProductWithSearchText() {
        return new Object[][]{
                {"Gömlek"}
                , {"Pantolon"}
                , {"T-Shirt"}
                , {"Etek"}
        };
    }


    @DataProvider(name = "addBasket")
    public static Object[][] driverAndValidUserDataAndProductWithSearchTextBrandMinMaxPrice() {
        return mergeData(mergeData(getDriverList(), getValidUserInfoList()), getProductWithSearchTextBrandMinMaxPrice());
    }

    @DataProvider(name = "addFavoriteAndAddBasket")
    public static Object[][] driverAndValidUserDataAndProductWithSearchText() {
        return mergeData(mergeData(getDriverList(), getValidUserInfoList()), getProductWithSearchText());
    }

    @DataProvider(name = "driverAndValidUserData")
    public static Object[][] driverAndValidUserData() {
        return mergeData(getDriverList(), getValidUserInfoList());
    }


    @DataProvider(name = "driverAndInvalidUserData")
    public static Object[][] driverAndInvalidUserData() {
        return mergeData(getDriverList(), getInvalidUserInfoList());

    }

    private static Object[][] mergeData(Object[][] list1, Object[][] list2) {
        int d1Size = list1.length * list2.length;
        int d2Size = list1[0].length + list2[0].length;

        Object[][] ret1 = new Object[d1Size][d2Size];
        int d1Index = 0;
        int d2Index = 0;
        for (Object[] item1 : list1) {
            for (Object[] item2 : list2) {
                for (Object o1 : item1) {
                    ret1[d1Index][d2Index] = o1;
                    d2Index++;
                }
                for (Object o2 : item2) {
                    ret1[d1Index][d2Index] = o2;
                    d2Index++;
                }
                d1Index++;
                d2Index = 0;
            }
        }
        return ret1;
    }


}
