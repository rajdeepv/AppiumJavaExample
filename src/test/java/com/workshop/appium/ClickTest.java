package com.workshop.appium;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.net.URL;

public class ClickTest {

    private AppiumDriver<MobileElement> driver;
    private WebDriverWait wait;

    @Before
    public void setUp() throws IOException {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 700000);
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "espresso");
        capabilities.setCapability(MobileCapabilityType.APP, System.getProperty("user.dir") + "/app-debug.apk");
        capabilities.setCapability("forceEspressoRebuild", true);

        driver = new AndroidDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        wait = new WebDriverWait(driver, 10);
    }

    @After
    public void tearDown() {
        try {
            driver.quit();
        } catch (Exception ignored) {
        }
    }


    @Test
    public void clickDemo() throws InterruptedException {
       MobileElement e = driver.findElement(By.id("italic"));
       e.click();
       Thread.sleep(1000);
    }

}
