package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AutoDromPage {

    public WebDriver driver;
    /**
     * конструктор класса, занимающийся инициализацией полей класса
     */
    public AutoDromPage(WebDriver driver)
    {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(css = "[data-ftid='sales__filter_fid']")
    private WebElement brandFilter;

    @FindBy(css = "[data-ftid='sales__filter_fid'] input")
    private WebElement brandFilterInput;

    @FindBy(css = "[data-ftid='sales__filter_mid']")
    private WebElement modelFilter;

    @FindBy(css = "[data-ftid='sales__filter_mid'] input")
    private WebElement modelFilterInput;

    @FindBy(css = "[data-ftid='sales__filter_fuel-type']")
    private WebElement fuelTypeFilter;

    @FindBy(css = "[for='sales__filter_unsold']")
    private WebElement unsoldFilter;

    @FindBy(css = "[data-ftid='sales__filter_mileage-from']")
    private WebElement mileageFromFilter;

    @FindBy(css = "[data-ftid='sales__filter_year-from']")
    private WebElement yearFromFilter;

    @FindBy(css = "[data-ftid='sales__filter_advanced-button']")
    private WebElement advancedFilterBtn;

    @FindBy(css = "[data-ftid='sales__filter_submit-button']")
    private WebElement applyFiltersBtn;

    @FindBy(css = "[data-ftid='component_pagination-item-next']")
    private WebElement nextPageBtn;

    @FindBy(css = "[data-ftid*='geoOverCity']")
    private WebElement otherCityBtn;

    @FindBy(css = "[data-ftid='bulls-list_bull'] svg[fill='none']")
    private WebElement addBullToFavoritesBtn;

    public void clickBrandFilter()
    {
        brandFilter.click();
    }

    public void setBrand(String brand) throws InterruptedException
    {
        brandFilter.click();
        brandFilterInput.sendKeys(brand);
        Thread.sleep(500);
        selectValue(brand);
    }

    public void setModel(String model) throws InterruptedException
    {
        modelFilter.click();
        modelFilterInput.sendKeys(model);
        Thread.sleep(500);
        selectValue(model);
    }

    public void setFuelType(String fuelType)
    {
        fuelTypeFilter.click();
        selectValue(fuelType);
    }

    public void selectValue(String value)
    {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        String filterValueLocator = "//div[@data-ftid='component_select_dropdown']//div[contains(text(),'"+ value +"')]";
        WebElement filterValue = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(filterValueLocator)));
        filterValue.click();
    }

    public void openAdvancedFilter()
    {
        advancedFilterBtn.click();
    }

    public void setUnsold()
    {
        unsoldFilter.click();
    }

    public void setMileageFrom(String mileageFrom)
    {
        mileageFromFilter.sendKeys(mileageFrom);
    }

    public void setYearFrom(String yearFrom)
    {
        yearFromFilter.click();
        selectValue(yearFrom);
    }

    public void applyFilters()
    {
        applyFiltersBtn.click();
    }

    public void clickNextPage()
    {
        nextPageBtn.click();
    }

    public void setOtherCity(String city)
    {
        otherCityBtn.click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-ftid='component_modal_content']")));
        WebElement searchInput = driver.findElement(By.cssSelector("[placeholder*='поиск города']"));
        searchInput.sendKeys(city);
        String filteredValueLocator = "//li//b[contains(text(),'"+ city +"')]";
        WebElement filteredValue = driver.findElement(By.xpath(filteredValueLocator));
        filteredValue.click();
        driver.manage().timeouts().getPageLoadTimeout();
    }

    public void addBullToFavorites()
    {
        addBullToFavoritesBtn.click();
    }
}
