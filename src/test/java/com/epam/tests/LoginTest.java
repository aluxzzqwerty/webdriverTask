package com.epam.tests;

import com.epam.base.BaseTest;
import com.epam.models.User;
import com.epam.pages.MailRuLoginPage;
import com.epam.pages.MailRuMainPage;
import com.epam.service.UserCreator;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class LoginTest extends BaseTest {

    @Test(priority = 2)
    @Severity(SeverityLevel.NORMAL)
    @Description("Logging into mail.ru with the correct username / password")
    public void loggingToMailRuPositive() {
        User user = UserCreator.withCredentialsFromProperty("mail");
        MailRuMainPage mailRuMainPage = PageFactory.initElements(driver, MailRuLoginPage.class)
                .openLoginPage()
                .loggingUpIntoAccount(user);
        String actualResult = mailRuMainPage.getLoggedInUser();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(actualResult, user.getName());
        mailRuMainPage.logOut();
        softAssert.assertAll();
    }

    @Test(priority = 1)
    @Severity(SeverityLevel.NORMAL)
    @Description("Logging into mail.ru with the incorrect username / password pair")
    public void loggingToMailRuNegative() {
        String actualResult = PageFactory.initElements(driver, MailRuLoginPage.class)
                .openLoginPage()
                .loggingWithIncorrectData(new User("fakeusername00@mail.ru","12345"))
                .getIncorrectPasswordError();
        Assert.assertEquals(actualResult, "Incorrect password. Try again");
    }

    @Test(priority = 0)
    @Severity(SeverityLevel.NORMAL)
    @Description("Logging into mail.ru with an empty username / password")
    public void loggingToMailWithNoData() {
        String actualResult = PageFactory.initElements(driver, MailRuLoginPage.class)
                .openLoginPage()
                .loggingWithIncorrectData(new User("", ""))
                .getEmptyUsernameFieldError();
        Assert.assertEquals(actualResult, "The \"Account name\" field is required");
    }
}