package com.epam.pages;

import com.epam.base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ProtonMailMainPage extends BasePage {

    public ProtonMailMainPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//div[@class='flex flex-nowrap flex-align-items-center cursor-pointer item-container unread'] //span[@data-testid=\"message-column:sender-address\"]")
    List<WebElement> unreadSendersLetters;

    @FindBy(xpath = "//*[@data-signature-widget='container']/preceding-sibling::div[2]")
    List<WebElement> contentsOfSenders;

    @FindBy(xpath = "//iframe[@title='Email content']")
    WebElement contentFrame;

    @Step("finding a particular sender by its name in protonmail")
    public int findSenderName(String name) {
        for (int i = 0; i < this.unreadSendersLetters.size(); i++) {
            if (this.unreadSendersLetters.get(i).getAttribute("title").equals(name)) {
                return i;
            }
        }
        return -1;
    }

    @Step("finding a particular sender`s content by its text in protonmail")
    public int findContentOfSender(String text) {
        for (int i = 0; i < this.contentsOfSenders.size(); i++) {
            if (this.contentsOfSenders.get(i).getText().equals(text)) {
                return i;
            }
        }
        return -1;
    }

    public boolean validateIsLetterUnreadAndHasCorrectSenderAndContent(String sender, String sentContent) {
        int indexOfSender = findSenderName(sender);

        this.unreadSendersLetters.get(indexOfSender).click();
        driver.switchTo().frame(this.contentFrame);
        int indexOfContentSender = findContentOfSender(sentContent);
        driver.switchTo().defaultContent();
        return indexOfSender != -1 && indexOfContentSender != -1;
    }

}
