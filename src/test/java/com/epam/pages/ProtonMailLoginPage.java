package com.epam.pages;

import com.epam.base.BasePage;
import com.epam.models.User;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProtonMailLoginPage extends BasePage {

    public ProtonMailLoginPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//*[@id=\"username\"]")
    WebElement username;

    @FindBy(xpath = "//input[@id='password']")
    WebElement password;

    public WebElement getUsername() {
        return username;
    }

    public WebElement getPassword() {
        return password;
    }

    @Step("logging in protonmail service")
    public ProtonMailMainPage loggingProtonmail(User user) {
        this.getUsername().sendKeys(user.getName());
        this.getPassword().sendKeys(user.getPassword(), Keys.ENTER);
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new ProtonMailMainPage(getDriver());
    }
}
