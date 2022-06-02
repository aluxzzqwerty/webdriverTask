package com.epam.utilities;

import com.epam.base.BaseTest;
import com.epam.models.User;
import org.testng.annotations.DataProvider;

public class DataProviderClass extends BaseTest {
    @DataProvider
    public Object[][] dataForPositiveLoginTest(){
        return new Object[][] {{new User("fakeusername00@mail.ru", "qaz85201234"), "Написать письмо"}};
//        return new Object[][] {{new User("fakeusername00@mail.ru", "qaz85201234"), "Написать письмо"}, {new User("hhtdfbfbhr", "rgfvxc"), "smth"}};
    }

    @DataProvider
    public Object[][] dataForNegativeDataLoginTest(){
        return new Object[][] {{new User("fakeusername00@mail.ru","12345"), "Incorrect password. Try again"}};
    }

    @DataProvider
    public Object[][] dataForEmptyDataLoginTest(){
        return new Object[][] {{new User("", ""), "The \"Account name\" field is required"}};
    }

    @DataProvider(name = "data to send letter")
    public Object[][] dataToSendLetter(){
        return new Object[][] {{"firstTAAlua@protonmail.com", "some content"}};
    }

    @DataProvider(name = "data from the sent letter")
    public Object[][] dataFromSentLetter(){
        return new Object[][] {{"fakeusername00@mail.ru", "some content"}};
    }
}