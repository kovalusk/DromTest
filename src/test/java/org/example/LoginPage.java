package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    public WebDriver driver;
    public LoginPage(WebDriver driver)
    {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(css = "[data-ftid='component_header_login']")
    private WebElement headerLoginBtn;

    @FindBy(id = "sign")
    private WebElement loginInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(id = "signbutton")
    private WebElement signBtn;

    public void authorize(String login, String password)
    {
        headerLoginBtn.click();
        loginInput.sendKeys(login);
        passwordInput.sendKeys(password);
        signBtn.click();
    }
}
