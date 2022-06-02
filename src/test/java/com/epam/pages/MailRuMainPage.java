package com.epam.pages;

import com.epam.base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class MailRuMainPage extends BasePage {

    public MailRuMainPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//span[text()=\"Написать письмо\"]")
    WebElement writeLetterButton;

    @FindBy(xpath = "//label[@class='container--zU301']")
    WebElement letterRecipient;

    @FindBy(xpath = "//div[@role='textbox']")
    WebElement textBox;

    @FindBy(xpath = "//span[text()='Отправить']")
    WebElement sendButton;

    @FindBy(xpath = "//*[@id=\"sideBarContent\"]/div/nav/a[6]/div/div[2]/div")
    WebElement sentMessagesButton;

    @FindBy(xpath = "//a[starts-with(@class, 'llc llc_normal')]/div[4]/div/div/span[starts-with(@class, 'll-crpt')]")
    List<WebElement> listOfRecipients;

    @FindBy(xpath = "//a[starts-with(@class, 'llc llc_normal')]/div[4]/div/div[3]/span[2]/div/span")
    List<WebElement> listOfContentsThatSent;

    @FindBy(xpath = "//span[@class='ph-dropdown-icon svelte-14x1gy5']")
    WebElement dropdown;

    @FindBy(xpath = "//div[text()='Выйти']")
    WebElement exitButton;

    public WebElement getDropdown() {
        return dropdown;
    }

    public WebElement getExitButton() {
        return exitButton;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public WebElement getWriteLetterButton() {
        return writeLetterButton;
    }

    public WebElement getLetterRecipient() {
        return letterRecipient;
    }

    public WebElement getTextBox() {
        return textBox;
    }

    public WebElement getSendButton() {
        return sendButton;
    }

    public WebElement getSentMessagesButton() {
        return sentMessagesButton;
    }

    public List<WebElement> getListOfRecipients() {
        return listOfRecipients;
    }

    public List<WebElement> getListOfContentsThatSent() {
        return listOfContentsThatSent;
    }

    @Step("sending letter in mailru")
    public void sendLetter(String recipient, String content) {
        waitForElementToBeClickable(this.getWriteLetterButton());
        this.getWriteLetterButton().click();
        this.getLetterRecipient().sendKeys(recipient, Keys.ENTER);
        this.getTextBox().sendKeys(content);
        waitForElementToBeClickable(this.getSendButton());
        this.getSendButton().click();
    }

    @Step("finding particular recipient by its name in mailru")
    public int findRecipientByName(String name) {
        for (int i = 0; i < this.getListOfRecipients().size(); i++) {
            if (this.getListOfRecipients().get(i).getText().equalsIgnoreCase(name))
                return i;
        }
        return -1;
    }

    @Step("finding particular content by its text in mailru")
    public int findContentByText(String text) {
        for (int i = 0; i < this.getListOfContentsThatSent().size(); i++) {
            if (this.getListOfContentsThatSent().get(i).getText().contains(text))
                return i;
        }
        return -1;
    }

    public void logOut() {
        waitForElementToBeClickable(this.getDropdown());
        this.getDropdown().click();
        waitForElementToBeClickable(this.getExitButton());
        this.getExitButton().click();
    }

    public void clickSentLetterButton() {
        waitForElementToBeClickable(this.getSentMessagesButton());
        this.getSentMessagesButton().click();
    }
}
