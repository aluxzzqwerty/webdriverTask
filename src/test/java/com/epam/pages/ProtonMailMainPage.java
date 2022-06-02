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

    @FindBy(xpath = "//div[@class='flex flex-nowrap flex-align-items-center cursor-pointer item-container unread']/div/div/div[contains(@class, 'item-senders')]/span")
    List<WebElement> sendersName;

    @FindBy(xpath = "//*[@id=\"proton-root\"]/div[2]/div/div[1]")
    List<WebElement> contentsOfSenders;

    @FindBy(xpath = "//iframe[@title='Email content']")
    WebElement frame;

    public WebElement getFrame() {
        return frame;
    }

    public List<WebElement> getSendersName() {
        return sendersName;
    }

    public List<WebElement> getContentsOfSenders() {
        return contentsOfSenders;
    }

    @Step("finding a particular sender by its name in protonmail")
    public int findSenderName(String name) {
        for (int i = 0; i < this.getSendersName().size(); i++) {
            if (this.getSendersName().get(i).getAttribute("title").equals(name)) {
                return i;
            }
        }
        return -1;
    }

    @Step("finding a particular sender`s content by its text in protonmail")
    public int findContentOfSender(String text) {
        for (int i = 0; i < this.getContentsOfSenders().size(); i++) {
            if (this.getContentsOfSenders().get(i).getText().equals(text)) {
                return i;
            }
        }
        return -1;
    }

    @Step("validating whether the letter is unread in protonmail")
    public boolean isLetterUnread(String name) {
        for (int i = 0; i < this.getSendersName().size(); i++) {
            if (this.getSendersName().get(i).getAttribute("title").equals(name)) {
                return true;
            }
        }
        return false;
    }


    public ProtonMailMainPage clickToSendersName(int indexOfSender){
        waitForElementToBeClickable(this.getSendersName().get(indexOfSender));
        this.getSendersName().get(indexOfSender).click();
        return this;
    }

}
