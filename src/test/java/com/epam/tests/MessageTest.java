package com.epam.tests;

import com.epam.base.BaseTest;
import com.epam.models.User;
import com.epam.pages.*;
import com.epam.service.UserCreator;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class MessageTest extends BaseTest {
    private final String CONTENT = "some content";

    @Test()
    @Severity(SeverityLevel.NORMAL)
    @Description("Logging into mail.ru and sending letter to protonmail user")
    public void sentLetterValidation() {
        User user = UserCreator.withCredentialsFromProperty("mail");
        String recipient = "firstTAAlua@protonmail.com";
        MailRuMainPage mailRuMainPage = PageFactory.initElements(driver, MailRuLoginPage.class)
                .openLoginPage()
                .loggingUpIntoAccount(user)
                .sendLetter(recipient, CONTENT);
        boolean actualResult = mailRuMainPage.isLetterSent(recipient, CONTENT);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(actualResult);

        mailRuMainPage.acceptAlert();
        softAssert.assertAll();
    }

    @Test(dependsOnMethods = { "sentLetterValidation" })
    @Severity(SeverityLevel.NORMAL)
    @Description("Logging into protonmail.com and checking whether letter is arrived, unread and has correct sender and content")
    public void letterValidation() {
        User user = UserCreator.withCredentialsFromProperty("proton");
        String sender = "fakeusername00@mail.ru";
        boolean actualResult = PageFactory.initElements(driver, ProtonMailLoginPage.class)
                .openLoginPage()
                .loggingProtonmail(user)
                .isLetterUnreadAndHasCorrectSenderAndContent(sender, CONTENT);
        Assert.assertTrue(actualResult);
    }
}
