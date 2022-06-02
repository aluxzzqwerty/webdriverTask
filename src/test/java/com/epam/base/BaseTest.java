package com.epam.base;

import com.epam.utilities.ChromeWebdriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class BaseTest {

    protected static WebDriver driver;
    protected static Properties config = new Properties();
    protected SoftAssert softAssert = new SoftAssert();

    @BeforeSuite
    public void setUp() {
        if (driver == null) {
            try {
                config.load(new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/properties/Config.properties"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (config.getProperty("browser").equalsIgnoreCase("firefox")) {
            System.setProperty("webdriver.gecko.driver", "/usr/bin/geckodriver");
            driver = new FirefoxDriver();
        } else if (config.getProperty("browser").equalsIgnoreCase("chrome")) {
            driver = ChromeWebdriver.getInstance();
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicitlyWait")), TimeUnit.SECONDS);
    }

    @AfterSuite
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    public void takeScreenshot() {
        Date currentDate = new Date();
        String screenName = currentDate.toString().replace(" ", "-").replace(":", "-");
        File src = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(src, new File(".//screenshots//" + screenName + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterMethod
    public void trackTest(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            takeScreenshot();
        }
    }
}
