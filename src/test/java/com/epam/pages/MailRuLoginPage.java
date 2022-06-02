package com.epam.pages;

import com.epam.base.BasePage;
import com.epam.models.User;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MailRuLoginPage extends BasePage {

    public MailRuLoginPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = ".//button[@data-testid='enter-mail-primary']")
    WebElement login;

    @FindBy(xpath = ".//input[@name='username']")
    WebElement username;

    @FindBy(xpath = "//input[@name='password']")
    WebElement password;

    @FindBy(xpath = "//iframe[@class='ag-popup__frame__layout__iframe']")
    WebElement frame;

    public WebElement getLogin() { return login; }

    public WebElement getEmail() {
        return username;
    }

    public WebElement getPassword() {
        return password;
    }

    public WebElement getFrame() { return frame; }

    @Step("logging in mailru")
    public MailRuMainPage loggingUpIntoAccount(User user) {
        waitForElementToBeClickable(this.getLogin());
        this.getLogin().click();
        getDriver().switchTo().frame(this.getFrame());
        this.getEmail().sendKeys(user.getName(), Keys.ENTER);
        this.getPassword().sendKeys(user.getPassword(), Keys.ENTER);
        getDriver().switchTo().defaultContent();
        return new MailRuMainPage(getDriver());
    }

    @Step("logging in with an incorrect data in mailru")
    public ErrorPage loggingWithIncorrectData(User user) {
        waitForElementToBeClickable(this.getLogin());
        this.getLogin().click();
        getDriver().switchTo().frame(this.getFrame());
        if (user.getName().equals("")) {
            this.getEmail().sendKeys(user.getName(), Keys.ENTER);
            return new ErrorPage(getDriver());
        }
        this.getEmail().sendKeys(user.getName(), Keys.ENTER);
        this.getPassword().sendKeys(user.getPassword(), Keys.ENTER);
        getDriver().switchTo().defaultContent();
        return new ErrorPage(getDriver());
    }
}
