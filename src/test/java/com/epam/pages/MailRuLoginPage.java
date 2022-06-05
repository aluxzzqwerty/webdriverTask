package com.epam.pages;

import com.epam.base.BasePage;
import com.epam.models.User;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MailRuLoginPage extends BasePage {
    private final String BASE_URL = "https://mail.ru/";

    public MailRuLoginPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//button[@data-testid='enter-mail-primary']")
    WebElement signInButton;

    @FindBy(xpath = "//input[@name='username']")
    WebElement inputUsername;

    @FindBy(xpath = "//input[@name='password']")
    WebElement inputPassword;

    @FindBy(xpath = "//iframe[@class='ag-popup__frame__layout__iframe']")
    WebElement signInFrame;

    @Step("opening mail.ru page")
    public MailRuLoginPage openLoginPage() {
        driver.navigate().to(BASE_URL);
        return this;
    }

    @Step("logging in mailru")
    public MailRuMainPage loggingUpIntoAccount(User user) {
        waitForElementToBeClickable(this.signInButton);
        this.signInButton.click();
        getDriver().switchTo().frame(this.signInFrame);
        this.inputUsername.sendKeys(user.getName(), Keys.ENTER);
        this.inputPassword.sendKeys(user.getPassword(), Keys.ENTER);
        getDriver().switchTo().defaultContent();
        return new MailRuMainPage(getDriver());
    }

    @Step("logging in with an incorrect data in mailru")
    public ErrorPage loggingWithIncorrectData(User user) {
        waitForElementToBeClickable(this.signInButton);
        this.signInButton.click();
        getDriver().switchTo().frame(this.signInFrame);
        if (user.getName().equals("")) {
            this.inputUsername.sendKeys(user.getName(), Keys.ENTER);
            return new ErrorPage(getDriver());
        }
        this.inputUsername.sendKeys(user.getName(), Keys.ENTER);
        this.inputPassword.sendKeys(user.getPassword(), Keys.ENTER);
        getDriver().switchTo().defaultContent();
        return new ErrorPage(getDriver());
    }
}
