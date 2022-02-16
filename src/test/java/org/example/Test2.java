package org.example;

import net.jodah.failsafe.internal.util.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.AfterClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;

public class Test2 {

    public static AutoDromPage autodrompage;
    public static BookmarkPage bookmarkpage;
    public static LoginPage loginpage;
    public static WebDriver driver;

    @BeforeClass
    public static void setup()
    {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        autodrompage = new AutoDromPage(driver);
        bookmarkpage = new BookmarkPage(driver);
        loginpage = new LoginPage(driver);

        driver.get("https://auto.drom.ru/");
        driver.manage().timeouts().getPageLoadTimeout();
    }

    @Test
    public void Test2()
    {
        loginpage.authorize("lemof49884@balaket.com", "kef42nck"); //Создала тестовый ящик на https://temp-mail.org/en/
        autodrompage.addBullToFavorites();
        checkFavoriteBullHref();
    }

    private void checkFavoriteBullHref()
    {
        //не было указано, проверять ли потом наличие объявления, на всякий сделала так:
        //получаем ссылку на первое объявление, к-е добавим в Избранное
        String firstBullHref = driver.findElement(By.cssSelector("[data-ftid='bulls-list_bull']")).getAttribute("href");
        driver.get("https://my.drom.ru/personal/bookmark");
        //Проверяем, что объявление добавилось
        int favoriteBullsCount = driver.findElements(By.cssSelector("[class*='bull-item_inline']")).size();
        Assert.isTrue(favoriteBullsCount == 1, "Объявление не добавлено в избранное");
        //И что добавилось именно то объявление
        String firstFavoriteBullHref = driver.findElement(By.cssSelector("[class*='bulletinLink']")).getAttribute("href");
        boolean isBullsHrefAreEqual = firstFavoriteBullHref.equals(firstBullHref);
        Assert.isTrue(isBullsHrefAreEqual, "В избранное добавлено другое объявление");
    }

    @AfterClass
    public static void tearDown()
    {
        //удаляем объявление из избранного для возможности повторного прогона
        bookmarkpage.removeFavoriteBull();
        driver.quit();
    }
}
