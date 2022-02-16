package org.example;

import net.jodah.failsafe.internal.util.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.AfterClass;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Test3 {
    public static AutoDromPage autodrompage;
    public static WebDriver driver;

    @BeforeClass
    public static void setup()
    {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        autodrompage = new AutoDromPage(driver);

        driver.get("https://auto.drom.ru/");
        driver.manage().timeouts().getPageLoadTimeout();
        //задержка на выполнение теста = 10 сек. надо ожидание, что баннер с рекламой прогрузился, если он есть на странице
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void Test3()
    {
        autodrompage.setOtherCity("Приморский край");
        Assert.isTrue(driver.getCurrentUrl().contains("https://auto.drom.ru/region25/"), "Приморский край не выбран");

        scriptToSetSelectDropdownMaxHeight();
        ArrayList<String> brandAndCount = getBrandAndCountInfo();
        sortInfoAndPrintToConsole(brandAndCount);
    }

    private void scriptToSetSelectDropdownMaxHeight()
    {
        //В выпадающем списке фильтра отображается не весь список марок, он подгружается при прокрутке
        //Чтоб не скроллить, сделала max-height большим, чтоб получить все значения сразу
        String brandFilterDropdownLocator = "[data-ftid='sales__filter_fid'] [data-ftid='component_select_dropdown']";
        WebElement brandFilterDropdown = driver.findElement(By.cssSelector(brandFilterDropdownLocator));
        JavascriptExecutor js = ((JavascriptExecutor) driver);
        js.executeScript("arguments[0].style.maxHeight = '4034px'", brandFilterDropdown);
    }

    private ArrayList<String> getBrandAndCountInfo()
    {
        //получаем всю информацию о марках и количестве
        autodrompage.clickBrandFilter();
        String brandAndCountText;
        String brandDropdownElementsLocator = "[data-ftid='sales__filter_fid'] div[class*='css-vgqgem']";
        java.util.List<WebElement> brandDropdownElements = driver.findElements(By.cssSelector(brandDropdownElementsLocator));
        //получаем инфу только о марках с количеством
        ArrayList<String> brandAndCount = new ArrayList();
        for (WebElement elem:brandDropdownElements)
        {
            brandAndCountText = elem.getText();
            if (brandAndCountText.contains("("))
            {
                brandAndCount.add(brandAndCountText);
            }
        }
        return brandAndCount;
    }

    private void sortInfoAndPrintToConsole(ArrayList<String> brandAndCount)
    {
        int elemLength = brandAndCount.size();
        String[] brandType = new String[elemLength];
        int[] offerCount = new int[elemLength];
        for (int i = 0; i < elemLength; i++)
        {
            //массив с названиями марок
            brandType[i] = brandAndCount.get(i).split("\\(")[0];
            //массив с количеством
            offerCount[i] = Integer.parseInt(brandAndCount.get(i).split("[\\(\\)]")[1]);
        }
        //сортировка пузырьком
        for (int i = elemLength-1; i > 0; i--)
        {
            for (int j = 0; j < i; j++)
            {
                if (offerCount[j] > offerCount[j+1])
                {
                    int tmp = offerCount[j];
                    String smp = brandType[j];
                    offerCount[j] = offerCount[j+1];
                    brandType[j] = brandType[j+1];
                    offerCount[j+1] = tmp;
                    brandType[j+1] = smp;
                }
            }
        }

        //Выводим список из 20 фирм с наибольшим количеством поданных объявлений
        System.out.println("| Фирма | Количество объявлений |");
        for (int k = elemLength-1; k > elemLength-21; k--)
        {
            System.out.println("| " + brandType[k] + " | " + offerCount[k] + " |");
        }
    }

    @AfterClass
    public static void tearDown()
    {
        driver.quit();
    }
}

