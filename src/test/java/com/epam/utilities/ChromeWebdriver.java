package com.epam.utilities;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeWebdriver {

    private static WebDriver driver;

    private ChromeWebdriver(){

    }

    public static WebDriver getInstance()  {
        if (driver==null) {
            System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver_linux64/chromedriver");
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--no-sandbox");
            options.addArguments("--headless");
            options.addArguments("start-maximized"); // open Browser in maximized mode
            options.addArguments("disable-infobars"); // disabling infobars
            options.addArguments("--disable-extensions"); // disabling extensions
            options.addArguments("--disable-gpu"); // applicable to windows os only
            options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
            options.addArguments("--window-size=1920,1080");
            options.addArguments("--ignore-certificate-errors");
            options.addArguments("--allow-running-insecure-content");

            driver = new ChromeDriver(options);
        }
        return driver;
    }
}
