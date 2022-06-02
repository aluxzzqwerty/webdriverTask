package com.epam.tests;

import com.epam.base.BaseTest;
import com.epam.models.User;
import com.epam.pages.ErrorPage;
import com.epam.pages.MailRuLoginPage;
import com.epam.pages.MailRuMainPage;
import com.epam.utilities.DataProviderClass;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test(dataProvider = "dataForPositiveLoginTest", dataProviderClass = DataProviderClass.class)
    @Severity(SeverityLevel.NORMAL)
    @Description("Logging into mail.ru with the correct username / password")
    public void loggingToMailRuPositive(User user, String expectedResult) {
        driver.get(config.getProperty("mailUrl"));
        MailRuLoginPage loginPage = PageFactory.initElements(driver, MailRuLoginPage.class);
        MailRuMainPage mailRuMainPage = loginPage.loggingUpIntoAccount(user);
        Assert.assertEquals(mailRuMainPage.getWriteLetterButton().getText(), expectedResult);
        mailRuMainPage.logOut();
    }

    @Test(dataProvider = "dataForNegativeDataLoginTest", dataProviderClass = DataProviderClass.class)
    @Severity(SeverityLevel.NORMAL)
    @Description("Logging into mail.ru with the incorrect username / password pair")
    public void loggingToMailRuNegative(User user, String expected) {
        driver.get(config.getProperty("mailUrl"));
        MailRuLoginPage loginPage = PageFactory.initElements(driver, MailRuLoginPage.class);
        ErrorPage errorPage = loginPage.loggingWithIncorrectData(user);
        Assert.assertEquals(errorPage.getIncorrectPasswordError().getText(), expected);
    }

    @Test(dataProvider = "dataForEmptyDataLoginTest", dataProviderClass = DataProviderClass.class)
    @Severity(SeverityLevel.NORMAL)
    @Description("Logging into mail.ru with an empty username / password")
    public void loggingToMailWithNoData(User user, String expected) {
        driver.get(config.getProperty("mailUrl"));
        MailRuLoginPage loginPage = PageFactory.initElements(driver, MailRuLoginPage.class);
        ErrorPage errorPage = loginPage.loggingWithIncorrectData(user);
        Assert.assertEquals(errorPage.getEmptyUsernameFieldError().getText(), expected);
    }

}