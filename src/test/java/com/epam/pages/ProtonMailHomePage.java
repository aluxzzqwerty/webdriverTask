package com.epam.pages;

import com.epam.base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProtonMailHomePage extends BasePage {

    public ProtonMailHomePage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//a[@href='https://account.proton.me/login']")
    WebElement login;

    public WebElement getLogin() {
        return login;
    }

    public ProtonMailLoginPage clickLogin() {
        waitForElementToBeClickable(this.getLogin());
        this.getLogin().click();
        return new ProtonMailLoginPage(getDriver());
    }
}
