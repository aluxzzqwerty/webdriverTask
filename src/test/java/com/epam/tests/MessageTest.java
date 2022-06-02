package com.epam.tests;

import com.epam.base.BaseTest;
import com.epam.models.User;
import com.epam.pages.*;
import com.epam.utilities.DataProviderClass;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Iterator;
import java.util.Set;

public class MessageTest extends BaseTest {

    @Test(dataProvider = "data to send letter", dataProviderClass = DataProviderClass.class)
    @Severity(SeverityLevel.NORMAL)
    @Description("Logging into mail.ru and sending letter to protonmail user")
    public void sentLetterValidation(String recipient, String content) {
        driver.get(config.getProperty("mailUrl"));
        MailRuLoginPage loginPage = PageFactory.initElements(driver, MailRuLoginPage.class);
        MailRuMainPage mailRuMainPage = loginPage.loggingUpIntoAccount(new User("fakeusername00@mail.ru", "qaz85201234"));

        mailRuMainPage.sendLetter(recipient, content);
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("arguments[0].click()", mailRuMainPage.getSentMessagesButton());
        int indexOfRecipient = mailRuMainPage.findRecipientByName(recipient);
        int indexOfContent = mailRuMainPage.findContentByText(content);
        Assert.assertEquals(mailRuMainPage.getListOfRecipients().get(indexOfRecipient).getText(), recipient);
        Assert.assertEquals(mailRuMainPage.getListOfContentsThatSent().get(indexOfContent).getText().replaceAll("--.*", "").trim(), content);
    }
    @Test(dependsOnMethods = { "sentLetterValidation" }, dataProvider = "data from the sent letter", dataProviderClass = DataProviderClass.class)
    @Severity(SeverityLevel.NORMAL)
    @Description("Logging into protonmail.com and checking whether letter is arrived, unread and has correct sender and content")
    public void letterValidation(String sender, String sentContent) {
        driver.get(config.getProperty("protonmail"));
        ProtonMailHomePage protonMailHomePage = PageFactory.initElements(driver, ProtonMailHomePage.class);
        String parent=driver.getWindowHandle();
        ProtonMailLoginPage protonMailLoginPage  = protonMailHomePage.clickLogin();
        Set<String> s=driver.getWindowHandles();

        Iterator<String> I1= s.iterator();
        ProtonMailMainPage protonMailMainPage = null;

        while(I1.hasNext())
        {
            String child_window=I1.next();

            if(!parent.equals(child_window))
            {
                driver.switchTo().window(child_window);
                protonMailMainPage = protonMailLoginPage.loggingProtonmail(new User("firstTAAlua@protonmail.com", "Njimko555"));
            }

        }
        // letter is marked as unread validation
        while (!protonMailMainPage.isLetterUnread(sender)) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            driver.navigate().refresh();
        }
        softAssert.assertTrue(protonMailMainPage.isLetterUnread(sender));

        // letter is arrived and has correct sender validation
        int indexOfSender = protonMailMainPage.findSenderName(sender);
        softAssert.assertEquals(protonMailMainPage.getSendersName().get(indexOfSender).getAttribute("title"), sender);

        // content of letter matches the sent one validation
        protonMailMainPage.getSendersName().get(indexOfSender).click();
        driver.switchTo().frame(protonMailMainPage.getFrame());
        int indexOfContentSender = protonMailMainPage.findContentOfSender(sentContent);
        softAssert.assertEquals(protonMailMainPage.getContentsOfSenders().get(indexOfContentSender).getText(), sentContent);
    }
}
